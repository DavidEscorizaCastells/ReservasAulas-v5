package org.iesalandalus.programacion.reservasaulas.modelo.mongobd.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.modelo.mongobd.utilidades.MongoDB;

import com.mongobd.client.MongoCollection;
import static com.mongobd.client.model.Filters.and;
import static com.mongobd.client.model.Filters.eq;

public class Reservas {
	
	private static final float MAX_PUNTOS_PROFESOR_MES = 200.0f;
	private static final String COLECCION = "reservas";
	
	private MongoCollection<Document> coleccionReservas;
	
	public Reservas() {
		coleccionReservas = MongoDB.getBD().getCollection(COLECCION);
	}
		
	public List<Reserva> getReservas() {
		List<Reserva> reservas = new ArrayList<>();
		for (Document documentoReserva : coleccionReservas.find()) {
			reservas.add(MongoDB.obtenerReservaDesdeDocumento(documentoReserva));
		}
		return reservas;
	}
	
	public int getNumReservas() {
		return (int)coleccionReservas.countDocuments();
	}
	
	public void insertar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new IllegalArgumentException("No se puede realizar una reserva nula.");
		}
		Reserva reservaExistente = getReservaAulaDia(reserva.getAula(), reserva.getPermanencia().getDia());
		if (reservaExistente != null) { 
			if (reservaExistente.getPermanencia() instanceof PermanenciaPorTramo &&
					reserva.getPermanencia() instanceof PermanenciaPorHora) {
				throw new OperationNotSupportedException("Ya se ha realizado una reserva por tramo para este día y aula.");
			}
			if (reservaExistente.getPermanencia() instanceof PermanenciaPorHora &&
					reserva.getPermanencia() instanceof PermanenciaPorTramo) {
				throw new OperationNotSupportedException("Ya se ha realizado una reserva por hora para este día y aula.");
			}
		}
		if (!esMesSiguienteOPosterior(reserva)) {
			throw new OperationNotSupportedException("Sólo se pueden hacer reservas para el mes que viene o posteriores.");
		}
		if (getPuntosGastadosReserva(reserva) > MAX_PUNTOS_PROFESOR_MES) {
			throw new OperationNotSupportedException("Esta reserva excede los puntos máximos por mes para dicho profesor.");
		}
		if (buscar(reserva) != null) {
			throw new OperationNotSupportedException("La reserva ya existe.");
		} else {
			coleccionReservas.insertOne(MongoDB.obtenerDocumentoDesdeReserva(reserva));
		}
	}
	
	private boolean esMesSiguienteOPosterior(Reserva reserva) {
		LocalDate diaReserva = reserva.getPermanencia().getDia();
		LocalDate dentroDeUnMes = LocalDate.now().plusMonths(1);
		LocalDate primerDiaMesSiguiente = LocalDate.of(dentroDeUnMes.getYear(), dentroDeUnMes.getMonth(), 1);
		return diaReserva.isAfter(primerDiaMesSiguiente) || diaReserva.equals(primerDiaMesSiguiente);
	}
	
	private float getPuntosGastadosReserva(Reserva reserva) {
		float puntosGastados = 0;
		for (Reserva reservaProfesor : getReservasProfesorMes(reserva.getProfesor(), reserva.getPermanencia().getDia())) {
			puntosGastados += reservaProfesor.getPuntos();
		}
		return puntosGastados + reserva.getPuntos();
	}
	
	private List<Reserva> getReservasProfesorMes(Profesor profesor, LocalDate mes) {
		if (profesor == null) {
			throw new IllegalArgumentException("No se pueden buscar reservas de un profesor nulo.");
		}
		List<Reserva> reservasProfesor = new ArrayList<>();
		for (Reserva reserva : getReservasProfesor(profesor)) {
			LocalDate diaReserva = reserva.getPermanencia().getDia();
			if (reserva.getProfesor().equals(profesor) && 
					diaReserva.getMonthValue() == mes.getMonthValue() &&
					diaReserva.getYear() == mes.getYear()) {
				reservasProfesor.add(new Reserva(reserva));
			}
		}
		return reservasProfesor;
	}
	
	private Reserva getReservaAulaDia(Aula aula, LocalDate dia) {
		if (dia == null) {
			throw new IllegalArgumentException("No se puede buscar reserva para un día nulo.");
		}
		for (Reserva reserva : getReservasAula(aula)) {
			LocalDate diaReserva = reserva.getPermanencia().getDia();
			Aula aulaReserva = reserva.getAula();
			if (diaReserva.equals(dia) && aulaReserva.equals(aula)) {
				return reserva;
			}
		}
		return null;
	}
	
	public Reserva buscar(Reserva reserva) {
		Bson filtroPermanencia = null;
		if (reserva.getPermanencia() instanceof PermanenciaPorHora) {
			filtroPermanencia = eq(MongoDB.PERMANENCIA_HORA, ((PermanenciaPorHora) reserva.getPermanencia()).getHora().format(MongoDB.FORMATO_HORA)); 
		} else {
			filtroPermanencia = eq(MongoDB.PERMANENCIA_TRAMO, ((PermanenciaPorTramo) reserva.getPermanencia()).getTramo().name());
		}
		Document documentoReserva = coleccionReservas.find().filter(
			and(
				eq(MongoDB.PROFESOR_NOMBRE, reserva.getProfesor().getNombre()),
				eq(MongoDB.AULA_NOMBRE, reserva.getAula().getNombre()),
				eq(MongoDB.PERMANENCIA_DIA, reserva.getPermanencia().getDia().format(MongoDB.FORMATO_DIA)),
				filtroPermanencia
			)).first();
		
		return MongoDB.obtenerReservaDesdeDocumento(documentoReserva);
	}
	
	public void borrar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva == null) {
			throw new IllegalArgumentException("No se puede anular una reserva nula.");
		}
		if (!esMesSiguienteOPosterior(reserva)) {
			throw new OperationNotSupportedException("Sólo se pueden anular reservas para el mes que viene o posteriores.");
		}
		if (buscar(reserva) != null) {
			Bson filtroPermanencia = null;
			if (reserva.getPermanencia() instanceof PermanenciaPorHora) {
				filtroPermanencia = eq(MongoDB.PERMANENCIA_HORA, ((PermanenciaPorHora) reserva.getPermanencia()).getHora().format(MongoDB.FORMATO_HORA)); 
			} else {
				filtroPermanencia = eq(MongoDB.PERMANENCIA_TRAMO, ((PermanenciaPorTramo) reserva.getPermanencia()).getTramo().name());
			}
			coleccionReservas.deleteOne(
				and(
						eq(MongoDB.PROFESOR_NOMBRE, reserva.getProfesor().getNombre()),
						eq(MongoDB.AULA_NOMBRE, reserva.getAula().getNombre()),
						eq(MongoDB.PERMANENCIA_DIA, reserva.getPermanencia().getDia().format(MongoDB.FORMATO_DIA)),
						filtroPermanencia
					)
				);
		} else {
			throw new OperationNotSupportedException("La reserva a anular no existe.");

		}
	}
	
	public List<String> representar() {
		List<String> representacion = new ArrayList<>();
		for (Reserva reserva: getReservas()) {
			representacion.add(reserva.toString());
		}
		return representacion;
	}
	
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		if (profesor == null) {
			throw new IllegalArgumentException("No se pueden buscar reservas de un profesor nulo.");
		}
		List<Reserva> reservasProfesor = new ArrayList<>();
		for (Document documentoReserva : coleccionReservas.find(eq(MongoDB.PROFESOR_NOMBRE, profesor.getNombre()))) {
			reservasProfesor.add(new Reserva(MongoDB.obtenerReservaDesdeDocumento(documentoReserva)));
		}
		return reservasProfesor;
	}
	
	public List<Reserva> getReservasAula(Aula aula) {
		if (aula == null) {
			throw new IllegalArgumentException("No se pueden buscar reservas de un aula nula.");
		}
		List<Reserva> reservasAula = new ArrayList<>();
		for (Document documentoReserva : coleccionReservas.find(eq(MongoDB.AULA_NOMBRE, aula.getNombre()))) {
			reservasAula.add(new Reserva(MongoDB.obtenerReservaDesdeDocumento(documentoReserva)));
		}
		return reservasAula;
	}
	
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		if (permanencia == null) {
			throw new IllegalArgumentException("No se pueden buscar reservas de una permanencia nula.");
		}
		List<Reserva> reservasPermanencia = new ArrayList<>();
		Bson filtroPermanencia = null;
		if (permanencia instanceof PermanenciaPorHora) {
			filtroPermanencia = eq(MongoDB.PERMANENCIA_HORA, ((PermanenciaPorHora) permanencia).getHora().format(MongoDB.FORMATO_HORA)); 
		} else {
			filtroPermanencia = eq(MongoDB.PERMANENCIA_TRAMO, ((PermanenciaPorTramo) permanencia).getTramo().name());
		}
		for (Document documentoReserva : coleccionReservas.find().filter(
				and(
						eq(MongoDB.PERMANENCIA_DIA, permanencia.getDia().format(MongoDB.FORMATO_DIA)),
						filtroPermanencia
					))
				) 
		{
			reservasPermanencia.add(new Reserva(MongoDB.obtenerReservaDesdeDocumento(documentoReserva)));
		}
		return reservasPermanencia;
	}
	
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		if (aula == null) {
			throw new IllegalArgumentException("No se puede consultar la disponibilidad de un aula nula.");
		}
		if (permanencia == null) {
			throw new IllegalArgumentException("No se puede consultar la disponibilidad de una permanencia nula.");
		}
		boolean disponible = true;
		for (Reserva reserva: getReservasAula(aula)) {
			if (reserva.getPermanencia().equals(permanencia)) {
				disponible = false;
			}
		}
		return disponible;
	}

}
