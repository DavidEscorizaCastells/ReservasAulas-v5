package org.iesalandalus.programacion.reservasaulas.modelo.dao;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorTramo;

public class Reservas {
	private List<Reserva> reservas;
	private static final float MAX_PUNTOS_PROFESOR_MES=200f;
	private static final String NOMBRE_FICHERO_RESERVAS = "ficheros"+File.separator+"reservas.dat";
	
	public Reservas() {
		reservas=new ArrayList<>();
	}
	
	public Reservas(Reservas otrasReservas) {
		setReservas(otrasReservas);
	}
	
	private void setReservas(Reservas reservas) {
		if (reservas==null)
			throw new IllegalArgumentException("No se pueden copiar reservas nulas.");
		
		this.reservas=copiaProfundaReservas(reservas.reservas);			
		
	}
	
	private List<Reserva> copiaProfundaReservas(List<Reserva> reservas) {
		List<Reserva> otrasReservas = new ArrayList<>();
		for (Reserva reserva: reservas) {
			otrasReservas.add(new Reserva(reserva));
		}
		return otrasReservas;
	}

	public List<Reserva> getReservas() {
		return copiaProfundaReservas(reservas);
	}

	public int getNumReservas() {
		return reservas.size();
	}
	
	public void insertar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva==null)
			throw new IllegalArgumentException ("No se puede realizar una reserva nula.");
		
		if (reservas.contains(reserva)) 
			throw new OperationNotSupportedException("La reserva ya existe.");
		
		if (!esMesSiguienteOPosterior(reserva))
			throw new OperationNotSupportedException("Sólo se pueden hacer reservas para el mes que viene o posteriores.");
		
		if (getPuntosGastadosReserva(reserva)+reserva.getPuntos()>=MAX_PUNTOS_PROFESOR_MES)
			throw new OperationNotSupportedException("Esta reserva excede los puntos máximos por mes para dicho profesor.");
		
		List<Reserva> reservasPorAula=getReservasAula(reserva.getAula());
		Reserva reservaBuscada=null;
		for (Reserva reservaAula:reservasPorAula) {
			if (reservaAula.getPermanencia().getDia().equals(reserva.getPermanencia().getDia()))
				reservaBuscada=new Reserva (reservaAula);
		}
		
		if (reservaBuscada!=null && reservaBuscada.getPermanencia() instanceof PermanenciaPorHora && reserva.getPermanencia() instanceof PermanenciaPorTramo)
			throw new OperationNotSupportedException("Ya se ha realizado una reserva por hora para este día y aula.");
		else if (reservaBuscada!=null && reservaBuscada.getPermanencia() instanceof PermanenciaPorTramo && reserva.getPermanencia() instanceof PermanenciaPorHora)
			throw new OperationNotSupportedException("Ya se ha realizado una reserva por tramo para este día y aula.");
		else 
			reservas.add(new Reserva(reserva));
	}
	
	private boolean esMesSiguienteOPosterior(Reserva reserva){
		int mesPermanencia=reserva.getPermanencia().getDia().getMonthValue();
		int añoPermanencia=reserva.getPermanencia().getDia().getYear();
		if (añoPermanencia<LocalDate.now().getYear() || añoPermanencia==LocalDate.now().getYear() && mesPermanencia<=LocalDate.now().getMonthValue())
			return false;
		else
			return true;
	}
	
	private float getPuntosGastadosReserva(Reserva reserva) {
		float puntosGastadosTotales=0f;
		List<Reserva> reservasProfesorMes=getReservasProfesorMes(reserva.getProfesor(), reserva.getPermanencia().getDia());
		for (Reserva reservaProfesorMes:reservasProfesorMes) {
			puntosGastadosTotales+=reservaProfesorMes.getPuntos();
		}
		return puntosGastadosTotales;
	}
	
	private List<Reserva> getReservasProfesorMes(Profesor profesor, LocalDate dia){
		List<Reserva> reservasProfesorMes=new ArrayList<>();
		List<Reserva> reservasProfesor=getReservasProfesor(profesor);
		for (Reserva reserva:reservasProfesor) {
			if (reserva.getPermanencia().getDia().getMonthValue()==dia.getMonthValue())
				reservasProfesorMes.add(reserva);
		}
		return reservasProfesorMes;
	}
	
	private Reserva getReservaDia(LocalDate dia) {
		for (Reserva reserva:reservas) {
			if (reserva.getPermanencia().getDia().equals(dia))
				return reserva;
		}
		return null;
	}
	
	public Reserva buscar(Reserva reserva) {
		int indice = reservas.indexOf(reserva);
		if (indice != -1) {
			return new Reserva(reservas.get(indice));
		} else {
			return null;
		}
	}
	
	public void borrar(Reserva reserva) throws OperationNotSupportedException {
		if (reserva==null)
			throw new IllegalArgumentException ("No se puede anular una reserva nula.");
		
		if (!reservas.remove(reserva)) {
			throw new OperationNotSupportedException("La reserva a anular no existe.");
		}
	}
	
	public List<String> representar() {
		List <String> representacion=new ArrayList<>();
		for (Reserva reserva : reservas) {
			representacion.add(reserva.toString());
		}
		return representacion;
	}
	
	public List<Reserva> getReservasProfesor(Profesor profesor) {
		List<Reserva> reservasProfesor=new ArrayList<>();
		for (Reserva reserva:reservas) {
			if (profesor.equals(reserva.getProfesor()))
				reservasProfesor.add(new Reserva(reserva));
		}
		return reservasProfesor;
	}
	
	public List<Reserva> getReservasAula(Aula aula) {
		List<Reserva> reservasAula=new ArrayList<>();
		for (Reserva reserva:reservas) {
			if (aula.equals(reserva.getAula()))
				reservasAula.add(new Reserva(reserva));
		}
		return reservasAula;
	}
	
	public List<Reserva> getReservasPermanencia(Permanencia permanencia) {
		List <Reserva> reservasPermanencia=new ArrayList<>();
		for (Reserva reserva:reservas) {
			if (permanencia.equals(reserva.getPermanencia())) {
				reservasPermanencia.add(new Reserva(reserva));
			}
		}
		return reservasPermanencia;
	}
	
	public boolean consultarDisponibilidad(Aula aula, Permanencia permanencia) {
		if (aula==null)
			throw new IllegalArgumentException ("No se puede consultar la disponibilidad de un aula nula.");
		if (permanencia==null)
			throw new IllegalArgumentException ("No se puede consultar la disponibilidad de una permanencia nula.");
		for (Reserva reserva:reservas)
			if (reserva.getAula().equals(aula) && reserva.getPermanencia().equals(permanencia))
				return false;
		return true;
	}	
	
	public void leer() {
		File ficheroReservas = new File(NOMBRE_FICHERO_RESERVAS);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroReservas))) {
			Reserva reserva = null;
			do {
				reserva = (Reserva) entrada.readObject();
				insertar(reserva);
			} while (reserva != null);
		} catch (ClassNotFoundException e) {
			System.out.println("No puedo encontrar la clase que tengo que leer.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo abrir el fihero de reservas.");
		} catch (EOFException e) {
			System.out.println("Fichero reservas leído satisfactoriamente.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void escribir() {
		File ficheroProfesores = new File(NOMBRE_FICHERO_RESERVAS);
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroProfesores))){
			for (Reserva reserva : reservas)
				salida.writeObject(reserva);
			System.out.println("Fichero reservas escrito satisfactoriamente.");
		} catch (FileNotFoundException e) {
			System.out.println("No puedo crear el fichero de reservas");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida");
		}
	}
	
}
