package org.iesalandalus.programacion.reservasaulas.modelo.dominio;

import static org.junit.Assert.*;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.junit.Test;

public class ProfesorTest {

	private static final String ERROR_EXCEPCION = "Debería haber saltado la excepción.";
	private static final String ERROR_NO_EXCEPCION = "No debería haber saltado la excepción.";
	
	private static final String NOMBRE = "José Ramón";
	private static final String CORREO = "joseramon.jimenez@iesalandalus.org";
	private static final String TELEFONO = "950112233";
	private static Profesor PROFESOR_CON_TELEFONO = new Profesor(NOMBRE, CORREO, TELEFONO);
	private static Profesor PROFESOR_SIN_TELEFONO = new Profesor(NOMBRE, CORREO);

	@Test
	public void constructorDosParametrosValidoTest() {
		Profesor profesor = null;
		try {
			profesor = new Profesor(NOMBRE, CORREO);
			assertEquals(NOMBRE, profesor.getNombre());
			assertEquals(CORREO, profesor.getCorreo());
			assertNull(profesor.getTelefono());
		} catch (IllegalArgumentException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void constructorTresParametrosValidoTest() {
		Profesor profesor = null;
		try {
			profesor = new Profesor(NOMBRE, CORREO, TELEFONO);
			assertEquals(NOMBRE, profesor.getNombre());
			assertEquals(CORREO, profesor.getCorreo());
			assertEquals(TELEFONO, profesor.getTelefono());
		} catch (IllegalArgumentException e) {
			fail(ERROR_NO_EXCEPCION);
		}
		try {
			profesor = new Profesor(NOMBRE, CORREO, null);
			assertEquals(NOMBRE, profesor.getNombre());
			assertEquals(CORREO, profesor.getCorreo());
			assertNull(profesor.getTelefono());
		} catch (IllegalArgumentException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void constructorDosParametrosNoValidoTest() {
		Profesor profesor = null;
		try {
			profesor = new Profesor(null, CORREO);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("El nombre del profesor no puede ser nulo.", e.getMessage());
			assertNull(profesor);
		}
		try {
			profesor = new Profesor("", CORREO);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("El nombre del profesor no puede estar vacío.", e.getMessage());
			assertNull(profesor);
		}
		try {
			profesor = new Profesor(NOMBRE, null);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("El correo del profesor no puede ser nulo.", e.getMessage());
			assertNull(profesor);
		}
		try {
			profesor = new Profesor(NOMBRE, "");
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("El correo del profesor no es válido.", e.getMessage());
			assertNull(profesor);
		}
	}
	
	@Test
	public void constructorTresParametrosNoValidoTest() {
		Profesor profesor = null;
		try {
			profesor = new Profesor(NOMBRE, CORREO, "");
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("El teléfono del profesor no es válido.", e.getMessage());
			assertNull(profesor);
		}
	}
	
	@Test
	public void constructorCopiaValidoTest() {
		Profesor profesor;
		try {
			profesor = new Profesor(PROFESOR_CON_TELEFONO);
			assertEquals(NOMBRE, profesor.getNombre());
			assertEquals(CORREO, profesor.getCorreo());
			assertEquals(TELEFONO, profesor.getTelefono());
		} catch (IllegalArgumentException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void constructorCopiaNoValidoTest() {
		Profesor profesor = null;
		try {
			profesor = new Profesor(null);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("No se puede copiar un profesor nulo.", e.getMessage());
			assertNull(profesor);
		}
	}
	
	@Test
	public void getTest() {
		assertEquals(NOMBRE, PROFESOR_CON_TELEFONO.getNombre());
		assertEquals(CORREO, PROFESOR_CON_TELEFONO.getCorreo());
		assertEquals(TELEFONO, PROFESOR_CON_TELEFONO.getTelefono());
		assertNull(PROFESOR_SIN_TELEFONO.getTelefono());
	}
	
	@Test
	public void equalTest() {
		Profesor profesor = new Profesor(NOMBRE, CORREO, TELEFONO);
		Profesor profesor1 = new Profesor("Andrés", CORREO, TELEFONO);
		assertNotEquals(profesor, null);
		assertNotEquals(profesor, "");
		assertEquals(profesor, profesor);
		assertEquals(PROFESOR_CON_TELEFONO, profesor);
		assertNotEquals(profesor, profesor1);
	}
	
	@Test
	public void hashCodeTest() {
		Profesor profesor = new Profesor(NOMBRE, CORREO, TELEFONO);
		Profesor profesor1 = new Profesor("Andrés", CORREO, TELEFONO);
		assertEquals(profesor.hashCode(), profesor.hashCode());
		assertEquals(PROFESOR_CON_TELEFONO.hashCode(), profesor.hashCode());
		assertNotEquals(profesor.hashCode(), profesor1.hashCode());
	}
	
	@Test
	public void toStringTest() {
		assertEquals("[nombre=José Ramón, correo=joseramon.jimenez@iesalandalus.org, telefono=950112233]", PROFESOR_CON_TELEFONO.toString());
		assertEquals("[nombre=José Ramón, correo=joseramon.jimenez@iesalandalus.org]", PROFESOR_SIN_TELEFONO.toString());
	}

}
