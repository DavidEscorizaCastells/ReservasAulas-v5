package org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

public class PermanenciaPorHoraTest {

	private static final String ERROR_EXCEPCION = "Debería haber saltado la excepción.";
	private static final String ERROR_NO_EXCEPCION = "No debería haber saltado la excepción.";
	
	private static final LocalDate DIA_LD = LocalDate.of(2018, 12, 1); 
	private static final String DIA_STR = "02/12/2018";
	private static final LocalTime HORA_LT = LocalTime.of(10, 0);
	private static final String HORA_STR = "11:00";
	private static final PermanenciaPorHora PERMANENCIA = new PermanenciaPorHora(DIA_LD, HORA_LT);

	@Test
	public void constructorValidoDiaLDHoraLTTest() {
		try {
			PermanenciaPorHora permanencia = new PermanenciaPorHora(DIA_LD, HORA_LT);
			assertEquals(2018, permanencia.getDia().getYear());
			assertEquals(12, permanencia.getDia().getMonthValue());
			assertEquals(1, permanencia.getDia().getDayOfMonth());
			assertEquals(10, permanencia.getHora().getHour());
			assertEquals(0, permanencia.getHora().getMinute());
			assertEquals(3, permanencia.getPuntos());
		} catch (IllegalArgumentException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void constructorValidoDiaLDHoraStrTest() {
		try {
			PermanenciaPorHora permanencia = new PermanenciaPorHora(DIA_LD, HORA_STR);
			assertEquals(2018, permanencia.getDia().getYear());
			assertEquals(12, permanencia.getDia().getMonthValue());
			assertEquals(1, permanencia.getDia().getDayOfMonth());
			assertEquals(11, permanencia.getHora().getHour());
			assertEquals(0, permanencia.getHora().getMinute());
			assertEquals(3, permanencia.getPuntos());
		} catch (IllegalArgumentException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void constructorValidoDiaStrHoraLTTest() {
		try {
			PermanenciaPorHora permanencia = new PermanenciaPorHora(DIA_STR, HORA_LT);
			assertEquals(2018, permanencia.getDia().getYear());
			assertEquals(12, permanencia.getDia().getMonthValue());
			assertEquals(2, permanencia.getDia().getDayOfMonth());
			assertEquals(10, permanencia.getHora().getHour());
			assertEquals(0, permanencia.getHora().getMinute());
			assertEquals(3, permanencia.getPuntos());
		} catch (IllegalArgumentException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void constructorValidoDiaStrHoraStrTest() {
		try {
			PermanenciaPorHora permanencia = new PermanenciaPorHora(DIA_STR, HORA_STR);
			assertEquals(2018, permanencia.getDia().getYear());
			assertEquals(12, permanencia.getDia().getMonthValue());
			assertEquals(2, permanencia.getDia().getDayOfMonth());
			assertEquals(11, permanencia.getHora().getHour());
			assertEquals(0, permanencia.getHora().getMinute());
			assertEquals(3, permanencia.getPuntos());
		} catch (IllegalArgumentException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void constructorNoValidoTest() {
		PermanenciaPorHora permanencia = null;
		try {
			LocalTime hora = null;
			permanencia = new PermanenciaPorHora(DIA_LD, hora);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("La hora de una permanencia no puede ser nula.", e.getMessage());
			assertNull(permanencia);
		}
		try {
			String hora = null;
			permanencia = new PermanenciaPorHora(DIA_LD, hora);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("La hora de una permanencia no puede ser nula.", e.getMessage());
			assertNull(permanencia);
		}
		try {
			LocalDate dia = null;
			permanencia = new PermanenciaPorHora(dia, HORA_LT);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("El día de una permanencia no puede ser nulo.", e.getMessage());
			assertNull(permanencia);
		}
		try {
			String dia = null;
			permanencia = new PermanenciaPorHora(dia, HORA_LT);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("El día de una permanencia no puede ser nulo.", e.getMessage());
			assertNull(permanencia);
		}
		try {
			String dia = "1/12/2018";
			permanencia = new PermanenciaPorHora(dia, HORA_LT);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("El formato del día de la permanencia no es correcto.", e.getMessage());
			assertNull(permanencia);
		}
		try {
			String hora = "1:00";
			permanencia = new PermanenciaPorHora(DIA_LD, hora);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("El formato de la hora de la permanencia no es correcto.", e.getMessage());
			assertNull(permanencia);
		}
		try {
			String hora = "07:00";
			permanencia = new PermanenciaPorHora(DIA_LD, hora);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("La hora de una permanencia debe estar comprendida entre las 8 y las 22.", e.getMessage());
			assertNull(permanencia);
		}
		try {
			LocalTime hora = LocalTime.of(7, 0);
			permanencia = new PermanenciaPorHora(DIA_LD, hora);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("La hora de una permanencia debe estar comprendida entre las 8 y las 22.", e.getMessage());
			assertNull(permanencia);
		}
		try {
			String hora = "23:00";
			permanencia = new PermanenciaPorHora(DIA_LD, hora);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("La hora de una permanencia debe estar comprendida entre las 8 y las 22.", e.getMessage());
			assertNull(permanencia);
		}
		try {
			LocalTime hora = LocalTime.of(23, 0);
			permanencia = new PermanenciaPorHora(DIA_LD, hora);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("La hora de una permanencia debe estar comprendida entre las 8 y las 22.", e.getMessage());
			assertNull(permanencia);
		}
		try {
			String hora = "11:15";
			permanencia = new PermanenciaPorHora(DIA_LD, hora);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("La hora de una permanencia debe ser una hora en punto.", e.getMessage());
			assertNull(permanencia);
		}
		try {
			LocalTime hora = LocalTime.of(11, 15);
			permanencia = new PermanenciaPorHora(DIA_LD, hora);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("La hora de una permanencia debe ser una hora en punto.", e.getMessage());
			assertNull(permanencia);
		}
	}
	
	@Test
	public void constructorCopiaValidoTest() {
		PermanenciaPorHora otraPermanencia;
		try {
			otraPermanencia = new PermanenciaPorHora(PERMANENCIA);
			assertEquals(DIA_LD, otraPermanencia.getDia());
			assertEquals(HORA_LT, otraPermanencia.getHora());
		} catch (IllegalArgumentException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void constructoCopiaNoValidoTest() {
		Permanencia otraPermanencia = null;
		try {
			otraPermanencia = new PermanenciaPorHora(null);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("No se puede copiar una permanencia nula.", e.getMessage());
			assertNull(otraPermanencia);
		}
	}
	
	@Test
	public void getTest() {
		assertEquals(DIA_LD, PERMANENCIA.getDia());
		assertEquals(HORA_LT, PERMANENCIA.getHora());
	}
	
	@Test
	public void getPuntosTest() {
		assertEquals(3, PERMANENCIA.getPuntos());
	}
	
	@Test
	public void equalsTest() {
		PermanenciaPorHora permanencia1 = new PermanenciaPorHora(DIA_LD, HORA_LT);
		PermanenciaPorHora permanencia2 = new PermanenciaPorHora(DIA_LD, HORA_STR);
		assertNotEquals(permanencia1, null);
		assertNotEquals(permanencia1, "");
		assertEquals(permanencia1, permanencia1);
		assertEquals(PERMANENCIA, permanencia1);
		assertNotEquals(permanencia1, permanencia2);
	}
	
	@Test
	public void hashCodeTest() {
		PermanenciaPorHora permanencia1 = new PermanenciaPorHora(DIA_LD, HORA_LT);
		PermanenciaPorHora permanencia2 = new PermanenciaPorHora(DIA_LD, HORA_STR);
		assertEquals(PERMANENCIA.hashCode(), PERMANENCIA.hashCode());
		assertEquals(PERMANENCIA.hashCode(), permanencia1.hashCode());
		assertNotEquals(permanencia1.hashCode(), permanencia2.hashCode());
	}
	
	@Test
	public void toStringTest() {
		assertEquals("[dia=01/12/2018, hora=10:00]", PERMANENCIA.toString());
	}

}
