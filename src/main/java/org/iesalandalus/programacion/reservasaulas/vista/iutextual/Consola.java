package org.iesalandalus.programacion.reservasaulas.vista.iutextual;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Tramo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {
	
	
	private Consola() {}
	
	public static void mostrarMenu() {
		mostrarCabecera("Gestión de reservas");
		for (Opcion opcion: Opcion.values()) {
			System.out.println(opcion);
		}
	}
	
	public static void mostrarCabecera(String mensaje) {
		System.out.printf("%n%s%n", mensaje);
		String cadena = "%0" + mensaje.length() + "d%n";
		System.out.println(String.format(cadena, 0).replace("0", "-"));
	}
	
	public static int elegirOpcion() {
		int ordinalOpcion;
		do {
			System.out.print("\nElige una opción: ");
			ordinalOpcion = Entrada.entero();
		} while (!Opcion.esOrdinalValido(ordinalOpcion));
		return ordinalOpcion;
	}
	
	public static Aula leerAula() {		
		String aula=leerNombreAula();
		System.out.print("Introduce cantidad de puestos: ");
		int puestos=Entrada.entero();
		return new Aula(aula, puestos);
	}
	
	public static String leerNombreAula() {
		System.out.print("Introduce el nombre del aula: ");
		return Entrada.cadena();
	}
	
	public static Profesor leerProfesor() {	
		String nombre=leerNombreProfesor();
		System.out.print("Introduce correo del profesor: ");
		String correo=Entrada.cadena();
		System.out.print("Introduce teléfono del profesor (si tiene): ");
		String telefono=Entrada.cadena();
		if (telefono.trim().equals(""))
			return new Profesor(nombre, correo);
		else
			return new Profesor(nombre, correo, telefono);
	}
	
	public static String leerNombreProfesor() {
		System.out.print("Introduce el nombre del profesor: ");
		return Entrada.cadena();
	}
	
	public static Tramo leerTramo() {
		String tramo;
		do {
		System.out.print("Selecciona mañana o tarde: ");
		tramo = Entrada.cadena();
		} while (!tramo.toLowerCase().equals("mañana") && !tramo.toLowerCase().equals("tarde"));
		
		if (tramo.equalsIgnoreCase("mañana"))
			return Tramo.MANANA;
		else
			return Tramo.TARDE;		
	}
	
	public static String leerDia() {
		final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd/MM/yyyy");;
		System.out.print("Escribe fecha para la reserva: ");
		String dia=Entrada.cadena();
		try {
			LocalDate.parse(dia, FORMATO_DIA);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("El formato del día de la permanencia no es correcto.");
		}
		return dia;
	}
	
	public static String leerHora() {
		final DateTimeFormatter FORMATO_HORA=DateTimeFormatter.ofPattern("HH:mm");
		System.out.print("Escribe hora para la reserva: ");
		String hora=Entrada.cadena();
		try {
			LocalTime.parse(hora, FORMATO_HORA);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException ("El formato de la hora de la permanencia no es correcto.");
		}
		return hora;
	}
	
	public static Permanencia leerPermanencia() {
		if (elegirPermanencia()==1)
			return new PermanenciaPorHora(leerDia(),leerHora());
		else
			return new PermanenciaPorTramo(leerDia(),leerTramo());
	}
	
	public static int elegirPermanencia() {
		System.out.println("Tipo de permanencia:");
		System.out.println("1. Por hora.");
		System.out.println("2. Por tramo");
		int tipo;	
		do {
			System.out.print("Elige tipo: ");
			tipo=Entrada.entero();			
		} while (tipo!=1 && tipo!=2);
			
			
		return tipo;
		
	}
}
