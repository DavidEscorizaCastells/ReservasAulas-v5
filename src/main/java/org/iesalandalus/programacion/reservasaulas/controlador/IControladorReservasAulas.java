package org.iesalandalus.programacion.reservasaulas.controlador;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;

public interface IControladorReservasAulas {

	void comenzar();

	void salir();

	void insertarAula(Aula aula) throws OperationNotSupportedException;

	void borrarAula(Aula aula) throws OperationNotSupportedException;

	Aula buscarAula(Aula aula);

	List<String> representarAulas();
	
	List<Aula> getAulas();

	void insertarProfesor(Profesor profesor) throws OperationNotSupportedException;

	void borrarProfesor(Profesor profesor) throws OperationNotSupportedException;

	Profesor buscarProfesor(Profesor profesor);

	List<String> representarProfesores();
	
	List<Profesor> getProfesores();
	
	void realizarReserva(Reserva reserva) throws OperationNotSupportedException;

	void anularReserva(Reserva reserva) throws OperationNotSupportedException;

	List<String> representarReservas();

	List<Reserva> getReservasAula(Aula aula);

	List<Reserva> getReservasProfesor(Profesor profesor);

	List<Reserva> getReservasPermanencia(Permanencia permanencia);

	boolean consultarDisponibilidad(Aula aula, Permanencia permanencia);

	List<Reserva> getReservas();

}