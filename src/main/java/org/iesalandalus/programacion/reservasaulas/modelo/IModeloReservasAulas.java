package org.iesalandalus.programacion.reservasaulas.modelo;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;

public interface IModeloReservasAulas {

	List<Aula> getAulas();

	int getNumAulas();

	List<String> representarAulas();

	Aula buscarAula(Aula aula);

	void insertarAula(Aula aula) throws OperationNotSupportedException;

	void borrarAula(Aula aula) throws OperationNotSupportedException;

	void leerAulas();

	void escribirAulas();
	
	List<Profesor> getProfesores();

	int getNumProfesores();

	List<String> representarProfesores();

	Profesor buscarProfesor(Profesor profesor);

	void insertarProfesor(Profesor profesor) throws OperationNotSupportedException;

	void borrarProfesor(Profesor profesor) throws OperationNotSupportedException;

	void leerProfesores();

	void escribirProfesores();
	
	List<Reserva> getReservas();

	int getNumReservas();

	List<String> representarReservas();

	Reserva buscarReserva(Reserva reserva);

	void realizarReserva(Reserva reserva) throws OperationNotSupportedException;

	void anularReserva(Reserva reserva) throws OperationNotSupportedException;

	List<Reserva> getReservasAula(Aula aula);

	List<Reserva> getReservaProfesor(Profesor profesor);

	List<Reserva> getReservaPermanencia(Permanencia permanencia);

	boolean consultarDisponibilidad(Aula aula, Permanencia permanencia);
	
	void leerReservas();

	void escribirReservas();

}