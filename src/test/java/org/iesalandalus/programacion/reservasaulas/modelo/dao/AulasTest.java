package org.iesalandalus.programacion.reservasaulas.modelo.dao;

import static org.junit.Assert.*;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.modelo.dao.Aulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.junit.Test;

public class AulasTest {
	
	private static final String NOMBRE_AULA1 = "Aula 1";
	private static final String NOMBRE_AULA2 = "Aula 2";
	private static final String NOMBRE_SALON = "Salón de actos";
	private static final int PUESTOS1 = 30;
	private static final int PUESTOS2 = 40;
	private static final int PUESTOS3 = 100;
	private static final String ERROR_EXCEPCION = "Debería haber saltado la excepción.";
	private static final String ERROR_NO_EXCEPCION = "No debería haber saltado la excepción.";
	
	private final Aula aula1 = new Aula(NOMBRE_AULA1, PUESTOS1);
	private final Aula aula2 = new Aula(NOMBRE_AULA2, PUESTOS2);
	private final Aula salon = new Aula(NOMBRE_SALON, PUESTOS3);

	@Test
	public void constructorDefectoTest() {
		Aulas aulas = new Aulas();
		assertEquals(0, aulas.getNumAulas());
	}
	
	@Test
	public void constructorCopiaValidoTest() {
		Aulas aulas1 = new Aulas();
		Aulas aulas2;
		aulas2 = new Aulas(aulas1);
		assertEquals(0, aulas2.getNumAulas());
		assertFalse(aulas1.getAulas() == aulas2.getAulas());
	}
	
	@Test
	public void constructorCopiaNoValidoTest() {
		Aulas aulas = null;
		Aulas aulas1 = null;
		try {
			aulas1 = new Aulas(aulas);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("No se pueden copiar aulas nulas.", e.getMessage());
			assertNull(aulas1);
		}
	}
	
	@Test
	public void insertarUnoValidoTest() {
		Aulas aulas = new Aulas();
		try {
			aulas.insertar(aula1);
			assertEquals(1, aulas.getNumAulas());
			assertEquals(aula1, aulas.buscar(aula1));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void insertarNuloTest() {
		Aulas aulas = new Aulas();
		try {
			aulas.insertar(null);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("No se puede insertar un aula nula.", e.getMessage());
			assertEquals(0, aulas.getNumAulas());
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void insertarRepetidoTest() {
		Aulas aulas = new Aulas();
		try {
			aulas.insertar(aula1);
			aulas.insertar(aula1);
			fail(ERROR_EXCEPCION);
		} catch (OperationNotSupportedException e) {
			assertEquals("El aula ya existe.", e.getMessage());
			assertEquals(1, aulas.getNumAulas());
		}
	}
	
	@Test
	public void insertarTresValidoTest() {
		Aulas aulas = new Aulas();
		try {
			aulas.insertar(aula1);
			assertEquals(1, aulas.getNumAulas());
			assertEquals(aula1, aulas.buscar(aula1));
			aulas.insertar(aula2);
			assertEquals(2, aulas.getNumAulas());
			assertEquals(aula2, aulas.buscar(aula2));
			aulas.insertar(salon);
			assertEquals(3, aulas.getNumAulas());
			assertEquals(salon, aulas.buscar(salon));
			List<Aula> arrayAulas = aulas.getAulas();
			assertEquals(aula1, arrayAulas.get(0));
			assertEquals(aula2, arrayAulas.get(1));
			assertEquals(salon, arrayAulas.get(2));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void getAulasTest() {
		Aulas aulas = new Aulas();
		try {
			aulas.insertar(aula1);
			List<Aula> aulas1 = aulas.getAulas();
			assertFalse(aulas1 == aulas.getAulas());
			assertFalse(aulas1.get(0) == aulas.getAulas().get(0));
			assertEquals(aulas1.get(0), aulas.getAulas().get(0));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	private Aulas insertarTres() {
		Aulas aulas = new Aulas();
		try {
			aulas.insertar(aula1);
			aulas.insertar(aula2);
			aulas.insertar(salon);
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
		return aulas;
	}
	
	@Test
	public void borrarPrincipioValidoTest() {
		Aulas aulas = insertarTres();
		try {
			aulas.borrar(aula1);
			assertEquals(2, aulas.getNumAulas());
			assertNull(aulas.buscar(aula1));
			List<Aula> aulas1 = aulas.getAulas();
			assertEquals(aula2, aulas1.get(0));
			assertEquals(salon, aulas1.get(1));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void borrarMedioValidoTest() {
		Aulas aulas = insertarTres();
		try {
			aulas.borrar(aula2);
			assertEquals(2, aulas.getNumAulas());
			assertNull(aulas.buscar(aula2));
			List<Aula> aulas1 = aulas.getAulas();
			assertEquals(aula1, aulas1.get(0));
			assertEquals(salon, aulas1.get(1));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void borrarFinalValidoTest() {
		Aulas aulas = insertarTres();
		try {
			aulas.borrar(salon);
			assertEquals(2, aulas.getNumAulas());
			assertNull(aulas.buscar(salon));
			List<Aula> aulas1 = aulas.getAulas();
			assertEquals(aula1, aulas1.get(0));
			assertEquals(aula2, aulas1.get(1));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void borrarNuloTest() {
		Aulas aulas = insertarTres();
		try {
			aulas.borrar(null);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("No se puede borrar un aula nula.", e.getMessage());
			assertEquals(3, aulas.getNumAulas());
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void borrarNoValidoTest() {
		Aulas aulas = insertarTres();
		try {
			Aula aula = new Aula("Salón 2", 50);
			aulas.borrar(aula);
			fail(ERROR_EXCEPCION);
		} catch (OperationNotSupportedException e) {
			assertEquals("El aula a borrar no existe.", e.getMessage());
			assertEquals(3, aulas.getNumAulas());
		}
	}
	
	@Test
	public void borrarInsertarTest() {
		Aulas aulas = insertarTres();
		try {
			aulas.borrar(aula1);
			assertEquals(2, aulas.getNumAulas());
			assertNull(aulas.buscar(aula1));
			aulas.insertar(aula1);
			assertEquals(3, aulas.getNumAulas());
			assertEquals(aula1, aulas.buscar(aula1));
			List<Aula> aulas1 = aulas.getAulas();
			assertEquals(aula2, aulas1.get(0));
			assertEquals(salon, aulas1.get(1));
			assertEquals(aula1, aulas1.get(2));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void buscarNuloTest() {
		Aulas aulas = insertarTres();
		assertNull(aulas.buscar(null));
	}
	
	@Test
	public void representarTest() {
		Aulas aulas = insertarTres();
		List<String> representacion = aulas.representar();
		assertEquals(aula1.toString(), representacion.get(0));
		assertEquals(aula2.toString(), representacion.get(1));
		assertEquals(salon.toString(), representacion.get(2));
	}

}
