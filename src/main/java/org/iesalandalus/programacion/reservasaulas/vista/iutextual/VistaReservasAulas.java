package org.iesalandalus.programacion.reservasaulas.vista.iutextual;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.vista.IVistaReservasAulas;

public class VistaReservasAulas implements IVistaReservasAulas {
	private static final String ERROR="Error: ";
	private static final String NOMBRE_VALIDO="David Escoriza Castells";
	private static final String CORREO_VALIDO="lDavi14@hotmail.com";
	private static final int PUESTOS_VALIDOS=40;
	private IControladorReservasAulas controlador;
	
	
	public VistaReservasAulas() {
		Opcion.setVista(this);
	}
	
	public void setControlador(IControladorReservasAulas controlador) {
		this.controlador=controlador;
	}
	
	public void comenzar(){
		Consola.mostrarCabecera("Programa para gestión de reservas.");
		int ordinalOpcion;
		do {
			Consola.mostrarMenu();
			ordinalOpcion = Consola.elegirOpcion();
			Opcion opcion = Opcion.getOpcionSegunOrdinal(ordinalOpcion);
			opcion.ejecutar();
		} while (ordinalOpcion != Opcion.SALIR.ordinal());
	}
	
	public void salir() {
		controlador.salir();
	}
	
	public void insertarAula() {
		Consola.mostrarCabecera("Insertar aula");
		try {			
			Aula aula = Consola.leerAula();
			controlador.insertarAula(aula);
			System.out.println("Aula insertada correctamente.");
		} catch (OperationNotSupportedException|IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}
	
	public void borrarAula() {
		Consola.mostrarCabecera("Borrar aula");
		try {
			Aula aula = new Aula(Consola.leerNombreAula(), PUESTOS_VALIDOS);
			controlador.borrarAula(aula);
			System.out.println("Aula borrada correctamente");
			} catch (OperationNotSupportedException|IllegalArgumentException e) {
				System.out.println(ERROR + e.getMessage());	
			}
	}
	
	public void buscarAula() {
		Consola.mostrarCabecera("Buscar aula");
		Aula aula = null;
		try {
			aula = new Aula(Consola.leerNombreAula(), PUESTOS_VALIDOS);
			aula = controlador.buscarAula(aula);
			if (aula != null) {
				System.out.println("El aula buscada es: " + aula.toString());
			} else {
				System.out.println("No existe ningún aula con ese nombre.");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}

	public void listarAulas() {
		Consola.mostrarCabecera("Listar aulas");
		List<String> listaAulas = controlador.representarAulas();
		if (!listaAulas.isEmpty()) {
			for (String aula : listaAulas) {
				System.out.println(aula);
			}
		} else {
			System.out.println("No hay aulas que listar.");
		}
	}
	
	public void insertarProfesor() {
		Consola.mostrarCabecera("Insertar profesor");
		try {			
			Profesor profesor = Consola.leerProfesor();
			controlador.insertarProfesor(profesor);
			System.out.println("Profesor insertado correctamente.");
		} catch (OperationNotSupportedException|IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}
	
	public void borrarProfesor() {
		Consola.mostrarCabecera("Borrar profesor");
		try {
			Profesor profesor = new Profesor (Consola.leerNombreProfesor(), CORREO_VALIDO);
			controlador.borrarProfesor(profesor);
			System.out.println("Profesor borrado correctamente");
			} catch (OperationNotSupportedException|IllegalArgumentException e) {
				System.out.println(ERROR + e.getMessage());	
			}
	}
	
	public void buscarProfesor() {
		Consola.mostrarCabecera("Buscar profesor");
		Profesor profesor = null;
		try {
			profesor = new Profesor (Consola.leerNombreProfesor(), CORREO_VALIDO);
			profesor = controlador.buscarProfesor(profesor);
			if (profesor != null) {
				System.out.println("El profesor buscado es: " + profesor.toString());
			} else {
				System.out.println("No existe ningún profesor con ese nombre.");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}
	
	public void listarProfesores() {
		Consola.mostrarCabecera("Listar profesores");
		List<String> listaProfesores = controlador.representarProfesores();
		if (!listaProfesores.isEmpty()) {
			for (String profesor : listaProfesores) {
				System.out.println(profesor);
			}
		} else {
			System.out.println("No hay profesores que listar.");
		}
	}
	
	public void realizarReserva() {
		Consola.mostrarCabecera("Realizar reserva");
		
		try {		
			Permanencia permanencia=Consola.leerPermanencia();
			Aula aula=Consola.leerAula();
			Profesor profesor=new Profesor(Consola.leerNombreProfesor(), CORREO_VALIDO);
			Reserva reserva=new Reserva(controlador.buscarProfesor(profesor),controlador.buscarAula(aula), permanencia);
			controlador.realizarReserva(reserva);
		} catch (OperationNotSupportedException|IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());
		}
	}
	
	/*private Reserva leerReserva(Profesor profesor) {
		
	}*/
	
	public void aunlaReserva() {
		Consola.mostrarCabecera("Anular reserva");
		
		try {
			Permanencia permanencia=Consola.leerPermanencia();
			Aula aula=Consola.leerAula();
			Profesor profesor=new Profesor(NOMBRE_VALIDO, CORREO_VALIDO);
			Reserva reserva=new Reserva(profesor,controlador.buscarAula(aula), permanencia);
			controlador.anularReserva(reserva);		
			} catch (OperationNotSupportedException|IllegalArgumentException e) {
				System.out.println(ERROR + e.getMessage());	
			}
	}
	
	public void listarReservas() {
		Consola.mostrarCabecera("Listar reservas");
		List<String> listaReservas = controlador.representarReservas();
		if (!listaReservas.isEmpty()) {
			for (String reserva : listaReservas) {
				System.out.println(reserva);
			}
		} else {
			System.out.println("No hay reservas que listar.");
		}
	}
	
	public void listarReservasAula() {
		Consola.mostrarCabecera("Listar reservas por aula");		
		List<Reserva> listaReservas = controlador.getReservasAula(Consola.leerAula());
		if (!listaReservas.isEmpty()) {
			for (Reserva reserva : listaReservas) {
				System.out.println(reserva.toString());
			}
		} else {
			System.out.println("No hay reservas que listar para ese aula.");
		}
	}
	
	public void listarReservasProfesor() {
		Consola.mostrarCabecera("Listar reservas por profesor");		
		List<Reserva> listaReservas = controlador.getReservasProfesor(Consola.leerProfesor());
		if (!listaReservas.isEmpty()) {
			for (Reserva reserva : listaReservas) {
				System.out.println(reserva.toString());
			}
		} else {
			System.out.println("No hay reservas que listar para ese profesor.");
		}
	}
	
	public void listarReservasPermanencia () {
		Consola.mostrarCabecera("Listar reservas por permanencia");
		try {
			Permanencia permanencia=Consola.leerPermanencia();
			List<Reserva> listaReservas = controlador.getReservasPermanencia(permanencia);
			if (!listaReservas.isEmpty()) {
				for (Reserva reserva : listaReservas) {
					System.out.println(reserva.toString());
				}
			} else {
				System.out.println("No hay reservas que listar para esa permanencia.");
			}	
		}  catch (IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());	
		}
		
		
		
	}
	
	public void consultarDisponibilidad() {
		Aula aula=Consola.leerAula();
		try {
			Permanencia permanencia=Consola.leerPermanencia();
			controlador.consultarDisponibilidad(controlador.buscarAula(aula), permanencia);
		}  catch (IllegalArgumentException e) {
			System.out.println(ERROR + e.getMessage());	
		}
		
	}
	
}
