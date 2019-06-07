package org.iesalandalus.programacion.reservasaulas.modelo.dominio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Tramo;
import org.junit.Test;

public class ReservaTest {
	
	private static final String ERROR_EXCEPCION = "Debería haber saltado la excepción.";
	private static final String ERROR_NO_EXCEPCION = "No debería haber saltado la excepción.";
	
	private static final String NOMBRE_PROFESOR = "José Ramón";
	private static final String CORREO = "joseramon.jimenez@iesalandalus.org";
	private static final String NOMBRE_AULA = "Salón de actos";
	private static final int PUESTOS = 30;
	private static final LocalDate DIA = LocalDate.now().plusMonths(1);
	private static final LocalTime HORA = LocalTime.now().minusMinutes(LocalTime.now().getMinute());
	private static final Profesor PROFESOR = new Profesor(NOMBRE_PROFESOR, CORREO);
	private static final Aula AULA = new Aula(NOMBRE_AULA, PUESTOS);
	private static final PermanenciaPorTramo PERMANENCIA_POR_TRAMO = new PermanenciaPorTramo(DIA, Tramo.MANANA);
	private static final PermanenciaPorHora PERMANENCIA_POR_HORA = new PermanenciaPorHora(DIA, HORA);

	@Test
	public void constructorValidoTest() {
		Reserva reserva = null;
		try {
			reserva = new Reserva(PROFESOR, AULA, PERMANENCIA_POR_TRAMO);
			assertEquals(PROFESOR, reserva.getProfesor());
			assertEquals(AULA, reserva.getAula());
			assertEquals(PERMANENCIA_POR_TRAMO, reserva.getPermanencia());
			assertTrue(25.0 == reserva.getPuntos());
		} catch (IllegalArgumentException e) {
			fail(ERROR_NO_EXCEPCION);
		}
		try {
			reserva = new Reserva(PROFESOR, AULA, PERMANENCIA_POR_HORA);
			assertEquals(PROFESOR, reserva.getProfesor());
			assertEquals(AULA, reserva.getAula());
			assertEquals(PERMANENCIA_POR_HORA, reserva.getPermanencia());
			assertTrue(18.0 == reserva.getPuntos());
		} catch (IllegalArgumentException e) {
			fail(ERROR_NO_EXCEPCION);
		}
		try {
			LocalDate hoy = LocalDate.now();
			LocalDate mesSiguiente = hoy.plusMonths(1);
			LocalDate primerDiaMesSiguiente = LocalDate.of(mesSiguiente.getYear(), mesSiguiente.getMonth(), 1);
			PermanenciaPorHora permanencia = new PermanenciaPorHora(primerDiaMesSiguiente, HORA);
			reserva = new Reserva(PROFESOR, AULA, permanencia);
			assertEquals(PROFESOR, reserva.getProfesor());
			assertEquals(AULA, reserva.getAula());
			assertEquals(permanencia, reserva.getPermanencia());
		} catch (IllegalArgumentException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void constructorNoValidoTest() {
		Reserva reserva = null;
		try {
			reserva = new Reserva(null, AULA, PERMANENCIA_POR_TRAMO);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("La reserva debe estar a nombre de un profesor.", e.getMessage());
			assertNull(reserva);
		}
		try {
			reserva = new Reserva(PROFESOR, null, PERMANENCIA_POR_TRAMO);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("La reserva debe ser para un aula concreta.", e.getMessage());
			assertNull(reserva);
		}
		try {
			reserva = new Reserva(PROFESOR, AULA, null);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("La reserva se debe hacer para una permanencia concreta.", e.getMessage());
			assertNull(reserva);
		}
	}
	
	@Test
	public void constructorCopiaValidoTest() {
		Reserva reserva = new Reserva(PROFESOR, AULA, PERMANENCIA_POR_TRAMO);
		Reserva reserva1;
		try {
			reserva1 = new Reserva(reserva);
			assertEquals(PROFESOR, reserva1.getProfesor());
			assertEquals(AULA, reserva1.getAula());
			assertEquals(PERMANENCIA_POR_TRAMO, reserva1.getPermanencia());
		} catch (IllegalArgumentException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void constructorCopiaNoValidoTest() {
		Reserva reserva = null;
		try {
			reserva = new Reserva(null);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("No se puede copiar una reserva nula.", e.getMessage());
			assertNull(reserva);
		}
	}
	
	@Test
	public void getTest() {
		Reserva reserva = new Reserva(PROFESOR, AULA, PERMANENCIA_POR_TRAMO);
		assertEquals(PROFESOR, reserva.getProfesor());
		assertEquals(AULA, reserva.getAula());
		assertEquals(PERMANENCIA_POR_TRAMO, reserva.getPermanencia());
	}
	
	@Test
	public void equalTest() {
		Reserva reserva = new Reserva(PROFESOR, AULA, PERMANENCIA_POR_TRAMO);
		Reserva reserva1 = new Reserva(reserva);
		Reserva reserva2 = new Reserva(PROFESOR, new Aula("Aula" ,15), PERMANENCIA_POR_TRAMO);
		assertNotEquals(reserva, null);
		assertNotEquals(reserva, "");
		assertEquals(reserva, reserva);
		assertEquals(reserva1, reserva);
		assertNotEquals(reserva, reserva2);
	}
	
	@Test
	public void hashCodeTest() {
		Reserva reserva = new Reserva(PROFESOR, AULA, PERMANENCIA_POR_TRAMO);
		Reserva reserva1 = new Reserva(reserva);
		Reserva reserva2 = new Reserva(PROFESOR, new Aula("Aula", 20), PERMANENCIA_POR_TRAMO);
		assertEquals(reserva.hashCode(), reserva.hashCode());
		assertEquals(reserva1.hashCode(), reserva.hashCode());
		assertNotEquals(reserva.hashCode(), reserva2.hashCode());
	}
	
	@Test
	public void toStringTest() {
		Reserva reserva = new Reserva(PROFESOR, AULA, PERMANENCIA_POR_TRAMO);
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String cadenaEsperada = "[profesor=[nombre=José Ramón, correo=joseramon.jimenez@iesalandalus.org]"
				+ ", aula=[nombre=Salón de actos, puestos=30], permanencia=[dia="+ DIA.format(formato) + ", tramo=Mañana], puntos=25.0]";
		assertEquals(cadenaEsperada, reserva.toString());
	}

}
