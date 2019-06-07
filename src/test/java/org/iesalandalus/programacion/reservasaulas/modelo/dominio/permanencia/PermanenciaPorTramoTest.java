package org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Tramo;
import org.junit.Test;

public class PermanenciaPorTramoTest {
	
	private static final String ERROR_EXCEPCION = "Debería haber saltado la excepción.";
	private static final String ERROR_NO_EXCEPCION = "No debería haber saltado la excepción.";
	
	private static final LocalDate DIA_LD = LocalDate.of(2018, 12, 1); 
	private static final String DIA_STR = "01/12/2018";
	private static final PermanenciaPorTramo PERMANENCIA = new PermanenciaPorTramo(DIA_LD, Tramo.MANANA);

	@Test
	public void constructorValidoDiaLDTest() {
		try {
			PermanenciaPorTramo permanencia = new PermanenciaPorTramo(DIA_LD, Tramo.MANANA);
			assertEquals(2018, permanencia.getDia().getYear());
			assertEquals(12, permanencia.getDia().getMonthValue());
			assertEquals(1, permanencia.getDia().getDayOfMonth());
			assertEquals(Tramo.MANANA, permanencia.getTramo());
			assertEquals(10, permanencia.getPuntos());
		} catch (IllegalArgumentException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void constructorValidoDiaStrTest() {
		try {
			PermanenciaPorTramo permanencia = new PermanenciaPorTramo(DIA_STR, Tramo.MANANA);
			assertEquals(2018, permanencia.getDia().getYear());
			assertEquals(12, permanencia.getDia().getMonthValue());
			assertEquals(1, permanencia.getDia().getDayOfMonth());
			assertEquals(Tramo.MANANA, permanencia.getTramo());
			assertEquals(10, permanencia.getPuntos());
		} catch (IllegalArgumentException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void constructorNoValidoTest() {
		Permanencia permanencia = null;
		try {
			permanencia = new PermanenciaPorTramo(LocalDate.of(2018, 12, 1), null);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("El tramo de una permanencia no puede ser nulo.", e.getMessage());
			assertNull(permanencia);
		}
		try {
			LocalDate dia = null;
			permanencia = new PermanenciaPorTramo(dia, Tramo.MANANA);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("El día de una permanencia no puede ser nulo.", e.getMessage());
			assertNull(permanencia);
		}
		try {
			String dia = null;
			permanencia = new PermanenciaPorTramo(dia, Tramo.MANANA);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("El día de una permanencia no puede ser nulo.", e.getMessage());
			assertNull(permanencia);
		}
		try {
			String dia = "1/12/2018";
			permanencia = new PermanenciaPorTramo(dia, Tramo.MANANA);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("El formato del día de la permanencia no es correcto.", e.getMessage());
			assertNull(permanencia);
		}
	}
	
	@Test
	public void constructorCopiaValidoTest() {
		PermanenciaPorTramo otraPermanencia;
		try {
			otraPermanencia = new PermanenciaPorTramo(PERMANENCIA);
			assertEquals(DIA_LD, otraPermanencia.getDia());
			assertEquals(Tramo.MANANA, otraPermanencia.getTramo());
		} catch (IllegalArgumentException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void constructoCopiaNoValidoTest() {
		Permanencia otraPermanencia = null;
		try {
			otraPermanencia = new PermanenciaPorTramo(null);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("No se puede copiar una permanencia nula.", e.getMessage());
			assertNull(otraPermanencia);
		}
	}
	
	@Test
	public void getTest() {
		assertEquals(DIA_LD, PERMANENCIA.getDia());
		assertEquals(Tramo.MANANA, PERMANENCIA.getTramo());
	}
	
	@Test
	public void getPuntosTest() {
		assertEquals(10, PERMANENCIA.getPuntos());
	}
	
	@Test
	public void equalsTest() {
		Permanencia permanencia1 = new PermanenciaPorTramo(DIA_LD, Tramo.MANANA);
		Permanencia permanencia2 = new PermanenciaPorTramo(DIA_LD, Tramo.TARDE);
		assertNotEquals(permanencia1, null);
		assertNotEquals(permanencia1, "");
		assertEquals(permanencia1, permanencia1);
		assertEquals(PERMANENCIA, permanencia1);
		assertNotEquals(permanencia1, permanencia2);
	}
	
	@Test
	public void hashCodeTest() {
		Permanencia permanencia1 = new PermanenciaPorTramo(DIA_LD, Tramo.MANANA);
		Permanencia permanencia2 = new PermanenciaPorTramo(DIA_LD, Tramo.TARDE);
		assertEquals(PERMANENCIA.hashCode(), PERMANENCIA.hashCode());
		assertEquals(PERMANENCIA.hashCode(), permanencia1.hashCode());
		assertNotEquals(permanencia1.hashCode(), permanencia2.hashCode());
	}
	
	@Test
	public void toStringTest() {
		assertEquals("[dia=01/12/2018, tramo=Mañana]", PERMANENCIA.toString());
	}

}
