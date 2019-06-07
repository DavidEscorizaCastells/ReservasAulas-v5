package org.iesalandalus.programacion.reservasaulas.modelo.dominio;

import java.io.Serializable;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorTramo;

public class Reserva implements Serializable {
	private Profesor profesor;
	private Aula aula;
	private Permanencia permanencia;
	
	public Reserva (Profesor profesor,Aula aula,Permanencia permanencia) {
		setProfesor(profesor);
		setAula(aula);
		setPermanencia(permanencia);
	}
	
	public Reserva (Reserva reserva) {
		if (reserva==null){
			throw new IllegalArgumentException ("No se puede copiar una reserva nula.");
		}
		setProfesor(reserva.profesor);
		setAula(reserva.aula);
		setPermanencia(reserva.permanencia);
	}

	public Profesor getProfesor() {
		return new Profesor(profesor);
	}

	private void setProfesor(Profesor profesor) {
		if (profesor==null)
			throw new IllegalArgumentException ("La reserva debe estar a nombre de un profesor.");
		
		this.profesor = new Profesor(profesor);
	}

	public Aula getAula() {
		return new Aula(aula);
	}

	private void setAula(Aula aula) {
		if (aula==null)
			throw new IllegalArgumentException ("La reserva debe ser para un aula concreta.");
		
		this.aula = new Aula(aula);
	}

	public Permanencia getPermanencia() {
		Permanencia nuevaPermanencia=null;
		
		if (permanencia instanceof PermanenciaPorHora )
			nuevaPermanencia= new PermanenciaPorHora((PermanenciaPorHora)permanencia);
		else if (permanencia instanceof PermanenciaPorTramo)
			nuevaPermanencia=new PermanenciaPorTramo((PermanenciaPorTramo)permanencia);
		
		return nuevaPermanencia;
	}

	private void setPermanencia(Permanencia permanencia) {
		Permanencia nuevaPermanencia=null;
		
		if (permanencia==null)
			throw new IllegalArgumentException ("La reserva se debe hacer para una permanencia concreta.");
		
		if (permanencia instanceof PermanenciaPorHora )
			nuevaPermanencia= new PermanenciaPorHora((PermanenciaPorHora)permanencia);
		else if (permanencia instanceof PermanenciaPorTramo)
			nuevaPermanencia=new PermanenciaPorTramo((PermanenciaPorTramo)permanencia);
		
		this.permanencia = nuevaPermanencia;
	}
	
	public float getPuntos() {
		return aula.getPuntos()+permanencia.getPuntos();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aula == null) ? 0 : aula.hashCode());
		result = prime * result + ((permanencia == null) ? 0 : permanencia.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reserva other = (Reserva) obj;
		if (aula == null) {
			if (other.aula != null)
				return false;
		} else if (!aula.equals(other.aula))
			return false;
		if (permanencia == null) {
			if (other.permanencia != null)
				return false;
		} else if (!permanencia.equals(other.permanencia))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[profesor=" + profesor.toString() + ", aula=" + aula.toString() + ", permanencia=" + permanencia.toString() + ", puntos="+getPuntos()+"]";
	}
	
	
	
	
	
}
