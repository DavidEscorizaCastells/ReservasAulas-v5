package org.iesalandalus.programacion.reservasaulas.modelo.mongobd.utilidades;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.bson.Document;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Tramo;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDB {
	
	private static final String SERVIDOR = "35.180.192.242";
	private static final int PUERTO = 27017;
	private static final String BD = "reservasaulas-david-escoriza";
	private static final String USUARIO = "david-escoriza";
	private static final String CONTRASENA = "david-escoriza-8789";
	
	public static final String PROFESOR = "profesor";
	public static final String NOMBRE = "nombre";
	public static final String CORREO = "correo";
	public static final String TELEFONO = "telefono";
	public static final String AULA = "aula";
	public static final String PUESTOS = "puestos";
	public static final String PERMANENCIA = "permanencia";
	public static final String TIPO = "tipo";
	public static final String DIA = "dia";
	public static final String HORA = "hora";
	public static final String TRAMO = "tramo";
	public static final String PROFESOR_NOMBRE = PROFESOR + "." + NOMBRE;
	public static final String AULA_NOMBRE = AULA + "." + NOMBRE;
	public static final String PERMANENCIA_TIPO = PERMANENCIA + "." + TIPO;
	public static final String TIPO_HORA = "POR_HORA";
	public static final String TIPO_TRAMO = "POR_TRAMO";
	public static final String PERMANENCIA_DIA = PERMANENCIA + "." + DIA;
	public static final String PERMANENCIA_TRAMO = PERMANENCIA + "." + TRAMO;
	public static final String PERMANENCIA_HORA = PERMANENCIA + "." + HORA;
	public static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	public static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
	
	private static MongoClient cliente = null;
	
	private MongoDB() {
		// Evitamos que se cree el constructor por defecto
	}
	
	public static MongoClient establecerConexion() {
		if (cliente == null) {
			MongoCredential credenciales = MongoCredential.createScramSha1Credential(USUARIO, BD, CONTRASENA.toCharArray());
			cliente = MongoClients.create(
			        MongoClientSettings.builder()
	                .applyToClusterSettings(builder -> 
	                        builder.hosts(Arrays.asList(new ServerAddress(SERVIDOR, PUERTO))))
	                .credential(credenciales)
	                .build());
			System.out.println("Conexión a MongoDB realizada correctamente.");
		}
		return cliente;
	}
	
	public static MongoClient getCliente() {
		return cliente;
	}
	
	public static MongoDatabase getBD() {
		if (cliente == null) {
			establecerConexion();
		}
		return cliente.getDatabase(BD);
	}
	
	public static void cerrarCliente() {
		if (cliente != null) {
			cliente.close();
			System.out.println("Conexión a MongoDB cerrada.");
		}
	}
	
	public static Document obtenerDocumentoDesdeProfesor(Profesor profesor) {
		if (profesor == null) {
			return null;
		}
		String nombre = profesor.getNombre();
		String correo = profesor.getCorreo();
		String telefono = profesor.getTelefono();
		return new Document().append(NOMBRE, nombre).append(CORREO, correo).append(TELEFONO, telefono);
	}

	public static Profesor obtenerProfesorDesdeDocumento(Document documentoProfesor) {
		if (documentoProfesor == null) {
			return null;
		}
		return new Profesor(documentoProfesor.getString(NOMBRE), documentoProfesor.getString(CORREO), documentoProfesor.getString(TELEFONO));
	}
	
	public static Document obtenerDocumentoDesdeAula(Aula aula) {
		if (aula == null) {
			return null;
		}
		String nombre = aula.getNombre();
		int puestos = aula.getPuestos();
		return new Document().append(NOMBRE, nombre).append(PUESTOS, puestos);
	}

	public static Aula obtenerAulaDesdeDocumento(Document documentoAula) {
		if (documentoAula == null) {
			return null;
		}
		return new Aula(documentoAula.getString(NOMBRE), documentoAula.getInteger(PUESTOS));
	}
	
	public static Document obtenerDocumentoDesdeReserva(Reserva reserva) {
		if (reserva == null) {
			return null;
		}
		Profesor profesor = reserva.getProfesor();
		Aula aula = reserva.getAula();
		Permanencia permanencia = reserva.getPermanencia();
		String dia = permanencia.getDia().format(FORMATO_DIA);
		Document dProfesor = obtenerDocumentoDesdeProfesor(profesor);
		Document dAula = obtenerDocumentoDesdeAula(aula);
		Document dPermanencia = new Document().append(DIA, dia);
		if (permanencia instanceof PermanenciaPorTramo) {
			String tramo = ((PermanenciaPorTramo) permanencia).getTramo().name();
			dPermanencia.append(TIPO, TIPO_TRAMO).append(TRAMO, tramo);
		} else {
			String hora = ((PermanenciaPorHora) permanencia).getHora().format(FORMATO_HORA);
			dPermanencia.append(TIPO, TIPO_HORA).append(HORA, hora);
		}
		return new Document().append(PROFESOR, dProfesor).append(AULA, dAula).append(PERMANENCIA, dPermanencia);
	}
	
	public static Reserva obtenerReservaDesdeDocumento(Document documentoReserva) {
		if (documentoReserva == null) {
			return null;
		}
		Aula aula = new Aula(obtenerAulaDesdeDocumento((Document) documentoReserva.get(AULA)));
		Profesor profesor = new Profesor(obtenerProfesorDesdeDocumento((Document) documentoReserva.get(PROFESOR)));
		Document dPermanencia = (Document) documentoReserva.get(PERMANENCIA);
		LocalDate dia = LocalDate.parse(dPermanencia.getString(DIA), FORMATO_DIA);
		String tipoPermanencia = dPermanencia.getString(TIPO);
		Permanencia permanencia = null;
		if (tipoPermanencia.equals(TIPO_TRAMO)) {
			Tramo tramo = Tramo.valueOf(dPermanencia.getString(TRAMO));
			permanencia = new PermanenciaPorTramo(dia, tramo);
		} else {
			LocalTime hora = LocalTime.parse(dPermanencia.getString(HORA), FORMATO_HORA);
			permanencia = new PermanenciaPorHora(dia, hora);
		}
		return new Reserva(profesor, aula, permanencia);
	}
}
