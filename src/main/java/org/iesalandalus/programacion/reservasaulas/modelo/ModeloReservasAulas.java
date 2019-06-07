package org.iesalandalus.programacion.reservasaulas.modelo;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.modelo.dao.Aulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dao.Profesores;
import org.iesalandalus.programacion.reservasaulas.modelo.dao.Reservas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;

public class ModeloReservasAulas implements IModeloReservasAulas {
	private Aulas aulas;
	private Profesores profesores;
	private Reservas reservas;
	
	public ModeloReservasAulas() {
		aulas=new Aulas();
		profesores=new Profesores();
		reservas=new Reservas();
	}
	
	@Override
	public List<Aula> getAulas() {
		return aulas.getAulas();
	}
	
	@Override
	public int getNumAulas() {
		return aulas.getNumAulas();
	}
	
	@Override
	public List<String> representarAulas() {
		return aulas.representar();
	}
	
	@Override
	public Aula buscarAula(Aula aula) {
		return aulas.buscar(aula);
	}
	
	@Override
	public void insertarAula (Aula aula) throws OperationNotSupportedException {
		aulas.insertar(aula);
	}
	
	@Override
	public void borrarAula (Aula aula) throws OperationNotSupportedException {
		aulas.borrar(aula);
	}
	
	@Override
	public void leerAulas() {
		aulas.leer();
	}
	
	@Override
	public void escribirAulas() {
		aulas.escribir();
	}
	
	@Override
	public List<Profesor> getProfesores() {
		return profesores.getProfesores();
	}
	
	@Override
	public int getNumProfesores() {
		return profesores.getNumProfesores();
	}
	
	@Override
	public List<String> representarProfesores() {
		return profesores.representar();
	}
	
	@Override
	public Profesor buscarProfesor(Profesor profesor) {
		return profesores.buscar(profesor);
	}
	
	@Override
	public void insertarProfesor (Profesor profesor) throws OperationNotSupportedException {
		profesores.insertar(profesor);
	}
	
	@Override
	public void borrarProfesor (Profesor profesor) throws OperationNotSupportedException {
		profesores.borrar(profesor);
	}
	
	@Override
	public void leerProfesores() {
		profesores.leer();
	}
	
	@Override
	public void escribirProfesores() {
		profesores.escribir();
	}
	
	@Override
	public List<Reserva> getReservas() {
		return reservas.getReservas();
	}
	
	@Override
	public int getNumReservas() {
		return reservas.getNumReservas();
	}
	
	@Override
	public List<String> representarReservas() {
		return reservas.representar();
	}
	
	@Override
	public Reserva buscarReserva (Reserva reserva) {
		return reservas.buscar(reserva);
	}
	
	@Override
	public void realizarReserva (Reserva reserva) throws OperationNotSupportedException {
		reservas.insertar(reserva);
	}
	
	@Override
	public void anularReserva (Reserva reserva) throws OperationNotSupportedException {
		reservas.borrar(reserva);
	}
	
	@Override
	public List<Reserva> getReservasAula (Aula aula) {
		return reservas.getReservasAula(aula);
	}
	
	@Override
	public List<Reserva> getReservaProfesor (Profesor profesor) {
		return reservas.getReservasProfesor(profesor);
	}
	
	@Override
	public List<Reserva> getReservaPermanencia (Permanencia permanencia) {
		return reservas.getReservasPermanencia(permanencia);
	}
	
	@Override
	public boolean consultarDisponibilidad (Aula aula, Permanencia permanencia) {
		return reservas.consultarDisponibilidad(aula, permanencia);
	}
	
	@Override
	public void leerReservas() {
		reservas.leer();
	}
	
	@Override
	public void escribirReservas() {
		reservas.escribir();
	}
}
