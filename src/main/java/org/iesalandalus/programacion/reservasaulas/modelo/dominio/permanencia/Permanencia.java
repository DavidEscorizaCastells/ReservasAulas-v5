package org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class Permanencia implements Serializable {
	protected LocalDate dia;
	protected static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	protected Permanencia() {	}
	
	protected Permanencia (LocalDate dia) {
		setDia(dia);
	}
	
	protected Permanencia (String dia) {
		setDia(dia);
	}

	public LocalDate getDia() {
		return dia;
	}

	protected void setDia(LocalDate dia) {
		if (dia==null)
			throw new IllegalArgumentException ("El día de una permanencia no puede ser nulo.");

		this.dia = dia;
	}
	
	protected void setDia(String fecha) {
		if (fecha==null)
			throw new IllegalArgumentException ("El día de una permanencia no puede ser nulo.");
		
		try {
			dia = LocalDate.parse(fecha, FORMATO_DIA);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("El formato del día de la permanencia no es correcto.");
		}
	}
	
	public abstract int getPuntos();
	
	public abstract int hashCode();
	
	public abstract boolean equals(Object obj);
	
	public abstract String toString();
	
}
