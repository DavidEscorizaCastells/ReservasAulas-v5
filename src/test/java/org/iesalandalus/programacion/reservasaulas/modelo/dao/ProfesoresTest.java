package org.iesalandalus.programacion.reservasaulas.modelo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.junit.Test;

public class ProfesoresTest {

	private static final String NOMBRE_PROFESOR1 = "José Ramón";
	private static final String NOMBRE_PROFESOR2 = "Andrés";
	private static final String NOMBRE_PROFESOR3 = "Begoña";
	private static final String CORREO_PROFESOR1 = "a@b.cc";
	private static final String CORREO_PROFESOR2 = "b@c.dd";
	private static final String CORREO_PROFESOR3 = "c@d.ee";
	private static final String ERROR_EXCEPCION = "Debería haber saltado la excepción.";
	private static final String ERROR_NO_EXCEPCION = "No debería haber saltado la excepción.";
	
	private final Profesor profesor1 = new Profesor(NOMBRE_PROFESOR1, CORREO_PROFESOR1);
	private final Profesor profesor2 = new Profesor(NOMBRE_PROFESOR2, CORREO_PROFESOR2);
	private final Profesor profesor3 = new Profesor(NOMBRE_PROFESOR3, CORREO_PROFESOR3);

	@Test
	public void constructorDefectoTest() {
		Profesores profesores = new Profesores();
		assertEquals(0, profesores.getNumProfesores());
	}
	
	@Test
	public void constructorCopiaValidoTest() {
		Profesores profesores1 = new Profesores();
		Profesores profesores2;
		profesores2 = new Profesores(profesores1);
		assertEquals(0, profesores2.getNumProfesores());
		assertFalse(profesores1.getProfesores() == profesores2.getProfesores());
	}
	
	@Test
	public void constructorCopiaNoValidoTest() {
		Profesores profesores = null;
		Profesores profesores1 = null;
		try {
			profesores1 = new Profesores(profesores);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("No se pueden copiar profesores nulos.", e.getMessage());
			assertNull(profesores1);
		}
	}
	
	@Test
	public void insertarUnoValidoTest() {
		Profesores profesores = new Profesores();
		try {
			profesores.insertar(profesor1);
			assertEquals(1, profesores.getNumProfesores());
			assertEquals(profesor1, profesores.buscar(profesor1));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void insertarNuloTest() {
		Profesores profesores = new Profesores();
		try {
			profesores.insertar(null);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("No se puede insertar un profesor nulo.", e.getMessage());
			assertEquals(0, profesores.getNumProfesores());
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void insertarRepetidoTest() {
		Profesores profesores = new Profesores();
		try {
			profesores.insertar(profesor1);
			profesores.insertar(profesor1);
			fail(ERROR_EXCEPCION);
		} catch (OperationNotSupportedException e) {
			assertEquals("El profesor ya existe.", e.getMessage());
			assertEquals(1, profesores.getNumProfesores());
		}
	}
	
	@Test
	public void insertarTresValidoTest() {
		Profesores profesores = new Profesores();
		try {
			profesores.insertar(profesor1);
			assertEquals(1, profesores.getNumProfesores());
			assertEquals(profesor1, profesores.buscar(profesor1));
			profesores.insertar(profesor2);
			assertEquals(2, profesores.getNumProfesores());
			assertEquals(profesor2, profesores.buscar(profesor2));
			profesores.insertar(profesor3);
			assertEquals(3, profesores.getNumProfesores());
			assertEquals(profesor3, profesores.buscar(profesor3));
			List<Profesor> profesores1 = profesores.getProfesores();
			assertEquals(profesor1, profesores1.get(0));
			assertEquals(profesor2, profesores1.get(1));
			assertEquals(profesor3, profesores1.get(2));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void getProfesoresTest() {
		Profesores profesores = new Profesores();
		try {
			profesores.insertar(profesor1);
			List<Profesor> profesores1 = profesores.getProfesores();
			assertFalse(profesores1 == profesores.getProfesores());
			assertFalse(profesores1.get(0) == profesores.getProfesores().get(0));
			assertEquals(profesores1.get(0), profesores.getProfesores().get(0));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	private Profesores insertarTres() {
		Profesores profesores = new Profesores();
		try {
			profesores.insertar(profesor1);
			profesores.insertar(profesor2);
			profesores.insertar(profesor3);
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
		return profesores;
	}
	
	@Test
	public void borrarPrincipioValidoTest() {
		Profesores profesores = insertarTres();
		try {
			profesores.borrar(profesor1);
			assertEquals(2, profesores.getNumProfesores());
			assertNull(profesores.buscar(profesor1));
			List<Profesor> profesores1 = profesores.getProfesores();
			assertEquals(profesor2, profesores1.get(0));
			assertEquals(profesor3, profesores1.get(1));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void borrarMedioValidoTest() {
		Profesores profesores = insertarTres();
		try {
			profesores.borrar(profesor2);
			assertEquals(2, profesores.getNumProfesores());
			assertNull(profesores.buscar(profesor2));
			List<Profesor> profesores1 = profesores.getProfesores();
			assertEquals(profesor1, profesores1.get(0));
			assertEquals(profesor3, profesores1.get(1));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void borrarFinalValidoTest() {
		Profesores profesores = insertarTres();
		try {
			profesores.borrar(profesor3);
			assertEquals(2, profesores.getNumProfesores());
			assertNull(profesores.buscar(profesor3));
			List<Profesor> profesores1 = profesores.getProfesores();
			assertEquals(profesor1, profesores1.get(0));
			assertEquals(profesor2, profesores1.get(1));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void borrarNuloTest() {
		Profesores profesores = insertarTres();
		try {
			profesores.borrar(null);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("No se puede borrar un profesor nulo.", e.getMessage());
			assertEquals(3, profesores.getNumProfesores());
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void borrarNoValidoTest() {
		Profesores profesores = insertarTres();
		try {
			Profesor profesor = new Profesor("Profesor 4","d@e.ff");
			profesores.borrar(profesor);
			fail(ERROR_EXCEPCION);
		} catch (OperationNotSupportedException e) {
			assertEquals("El profesor a borrar no existe.", e.getMessage());
			assertEquals(3, profesores.getNumProfesores());
		}
	}
	
	@Test
	public void borrarInsertarTest() {
		Profesores profesores = insertarTres();
		try {
			profesores.borrar(profesor1);
			assertEquals(2, profesores.getNumProfesores());
			assertNull(profesores.buscar(profesor1));
			profesores.insertar(profesor1);
			assertEquals(3, profesores.getNumProfesores());
			assertEquals(profesor1, profesores.buscar(profesor1));
			List<Profesor> profesores1 = profesores.getProfesores();
			assertEquals(profesor2, profesores1.get(0));
			assertEquals(profesor3, profesores1.get(1));
			assertEquals(profesor1, profesores1.get(2));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void buscarNuloTest() {
		Profesores profesores = insertarTres();
		assertNull(profesores.buscar(null));
	}
	
	@Test
	public void representarTest() {
		Profesores profesores = insertarTres();
		List<String> representacion = profesores.representar();
		assertEquals(profesor1.toString(), representacion.get(0));
		assertEquals(profesor2.toString(), representacion.get(1));
		assertEquals(profesor3.toString(), representacion.get(2));
	}

}
