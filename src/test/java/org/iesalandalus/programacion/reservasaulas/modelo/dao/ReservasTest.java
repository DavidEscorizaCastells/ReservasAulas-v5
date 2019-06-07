package org.iesalandalus.programacion.reservasaulas.modelo.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Tramo;
import org.junit.Test;

public class ReservasTest {

	private static final String NOMBRE_PROFESOR1 = "José Ramón";
	private static final String NOMBRE_PROFESOR2 = "Andrés";
	private static final String CORREO = "a@b.cc";
	private static final String NOMBRE_AULA1 = "Aula 1";
	private static final String NOMBRE_AULA2 = "Aula 2";
	private static final String NOMBRE_SALON = "Salón de actos";
	private static final int PUESTOS1 = 30;
	private static final int PUESTOS2 = 50;
	private static final int PUESTOS3 = 100;
	private static final LocalDate MES_SIGUIENTE = LocalDate.now().plusMonths(1);
	private static final LocalDate DENTRO_DOS_MESES = LocalDate.now().plusMonths(2);
	private static final LocalDate DIA1 = LocalDate.of(MES_SIGUIENTE.getYear(), MES_SIGUIENTE.getMonthValue(), 1);
	private static final LocalDate DIA2 = LocalDate.of(MES_SIGUIENTE.getYear(), MES_SIGUIENTE.getMonthValue(), 2);
	private static final LocalDate DIA3 = LocalDate.of(DENTRO_DOS_MESES.getYear(), DENTRO_DOS_MESES.getMonthValue(), 1);
	private static final LocalDate DIA4 = LocalDate.of(DENTRO_DOS_MESES.getYear(), DENTRO_DOS_MESES.getMonthValue(), 2);
	private static final LocalTime HORA1 = LocalTime.of(10, 0);
	private static final LocalTime HORA2 = LocalTime.of(11, 0);
	private static final String ERROR_EXCEPCION = "Debería haber saltado la excepción.";
	private static final String ERROR_NO_EXCEPCION = "No debería haber saltado la excepción.";
	
	private static final Profesor PROFESOR1 = new Profesor(NOMBRE_PROFESOR1, CORREO);
	private static final Profesor PROFESOR2 = new Profesor(NOMBRE_PROFESOR2, CORREO);
	private static final Aula AULA1 = new Aula(NOMBRE_AULA1, PUESTOS1);
	private static final Aula AULA2 = new Aula(NOMBRE_AULA2, PUESTOS2);
	private static final Aula AULA3 = new Aula(NOMBRE_SALON, PUESTOS3);
	private static final Permanencia PERMANENCIA1 = new PermanenciaPorTramo(DIA1, Tramo.MANANA);
	private static final Permanencia PERMANENCIA2 = new PermanenciaPorHora(DIA2, HORA1);
	private static final Permanencia PERMANENCIA3 = new PermanenciaPorHora(DIA2, HORA2);
	private static final Permanencia PERMANENCIA4 = new PermanenciaPorTramo(DIA2, Tramo.MANANA);
	private static final Reserva RESERVA1 = new Reserva(PROFESOR1, AULA1, PERMANENCIA1);
	private static final Reserva RESERVA2 = new Reserva(PROFESOR1, AULA1, PERMANENCIA2);
	private static final Reserva RESERVA3 = new Reserva(PROFESOR1, AULA2, PERMANENCIA1);
	private static final Reserva RESERVA4 = new Reserva(PROFESOR1, AULA2, PERMANENCIA2);
	private static final Reserva RESERVA5 = new Reserva(PROFESOR2, AULA1, PERMANENCIA1);


	@Test
	public void constructorDefectoTest() {
		Reservas reservas = new Reservas();
		assertEquals(0, reservas.getNumReservas());
	}
	
	@Test
	public void constructorCopiaValidoTest() {
		Reservas reservas1 = new Reservas();
		Reservas reservas2;
		reservas2 = new Reservas(reservas1);
		assertEquals(0, reservas2.getNumReservas());
		assertFalse(reservas1.getReservas() == reservas2.getReservas());
	}
	
	@Test
	public void constructorCopiaNoValidoTest() {
		Reservas reservas = null;
		Reservas reservas1 = null;
		try {
			reservas1 = new Reservas(reservas);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("No se pueden copiar reservas nulas.", e.getMessage());
			assertNull(reservas1);
		}
	}
	
	@Test
	public void insertarUnaValidaTest() {
		Reservas reservas = new Reservas();
		try {
			reservas.insertar(RESERVA1);
			assertEquals(1, reservas.getNumReservas());
			assertEquals(RESERVA1, reservas.buscar(RESERVA1));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void insertarNulaTest() {
		Reservas reservas = new Reservas();
		try {
			reservas.insertar(null);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("No se puede realizar una reserva nula.", e.getMessage());
			assertEquals(0, reservas.getNumReservas());
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void insertarReservaRepetidaTest() {
		Reservas reservas = new Reservas();
		Reserva reserva = null;
		try {
			reserva = new Reserva(PROFESOR1, AULA3, PERMANENCIA1);
			reservas.insertar(reserva);
			reserva = new Reserva(PROFESOR2, AULA3, PERMANENCIA1);
			reservas.insertar(reserva);
			fail(ERROR_EXCEPCION);
		} catch (OperationNotSupportedException e) {
			assertEquals("La reserva ya existe.", e.getMessage());
			assertEquals(1, reservas.getNumReservas());
		} 
	}
	
	@Test
	public void insertarReservaDiferentePermanenciaTest() {
		Reservas reservas = new Reservas();
		Reserva reserva = null;
		try {
			reserva = new Reserva(PROFESOR1, AULA3, PERMANENCIA3);
			reservas.insertar(reserva);
			reserva = new Reserva(PROFESOR2, AULA3, PERMANENCIA4);
			reservas.insertar(reserva);
			fail(ERROR_EXCEPCION);
		} catch (OperationNotSupportedException e) {
			assertEquals("Ya se ha realizado una reserva por hora para este día y aula.", e.getMessage());
			assertEquals(1, reservas.getNumReservas());
		} 
		reservas = new Reservas();
		try {
			reserva = new Reserva(PROFESOR1, AULA3, PERMANENCIA4);
			reservas.insertar(reserva);
			reserva = new Reserva(PROFESOR2, AULA3, PERMANENCIA3);
			reservas.insertar(reserva);
			fail(ERROR_EXCEPCION);
		} catch (OperationNotSupportedException e) {
			assertEquals("Ya se ha realizado una reserva por tramo para este día y aula.", e.getMessage());
			assertEquals(1, reservas.getNumReservas());
		} 
	}
	
	@Test
	public void insertarReservaMesActualTest() {
		Reservas reservas = new Reservas();
		Reserva reserva = null;
		try {
			reserva = new Reserva(PROFESOR1, AULA3, new PermanenciaPorTramo(LocalDate.now(), Tramo.MANANA));
			reservas.insertar(reserva);
			fail(ERROR_EXCEPCION);
		} catch (OperationNotSupportedException e) {
			assertEquals("Sólo se pueden hacer reservas para el mes que viene o posteriores.", e.getMessage());
			assertEquals(0, reservas.getNumReservas());
		} 
	}
	
	@Test
	public void insertarPuntosSobrepasadosTest() {
		Reservas reservas = new Reservas();
		Reserva reserva = null;
		try {
			reserva = new Reserva(PROFESOR1, AULA3, new PermanenciaPorTramo(DIA1, Tramo.MANANA));
			reservas.insertar(reserva);
			reserva = new Reserva(PROFESOR1, AULA3, new PermanenciaPorTramo(DIA1, Tramo.TARDE));
			reservas.insertar(reserva);
			reserva = new Reserva(PROFESOR1, AULA3, new PermanenciaPorTramo(DIA2, Tramo.MANANA));
			reservas.insertar(reserva);
			reserva = new Reserva(PROFESOR1, AULA3, new PermanenciaPorTramo(DIA2, Tramo.TARDE));
			reservas.insertar(reserva);
			fail(ERROR_EXCEPCION);
		} catch (OperationNotSupportedException e) {
			assertEquals("Esta reserva excede los puntos máximos por mes para dicho profesor.", e.getMessage());
			assertEquals(3, reservas.getNumReservas());
		} 
		try {
			reserva = new Reserva(PROFESOR1, AULA3, new PermanenciaPorTramo(DIA3, Tramo.MANANA));
			reservas.insertar(reserva);
			reserva = new Reserva(PROFESOR1, AULA3, new PermanenciaPorTramo(DIA3, Tramo.TARDE));
			reservas.insertar(reserva);
			reserva = new Reserva(PROFESOR1, AULA3, new PermanenciaPorTramo(DIA4, Tramo.MANANA));
			reservas.insertar(reserva);
			reserva = new Reserva(PROFESOR1, AULA3, new PermanenciaPorTramo(DIA4, Tramo.TARDE));
			reservas.insertar(reserva);
			fail(ERROR_EXCEPCION);
		} catch (OperationNotSupportedException e) {
			assertEquals("Esta reserva excede los puntos máximos por mes para dicho profesor.", e.getMessage());
			assertEquals(6, reservas.getNumReservas());
		} 
	}
	
	@Test
	public void insertarRepetidaTest() {
		Reservas reservas = new Reservas();
		try {
			reservas.insertar(RESERVA1);
			reservas.insertar(RESERVA1);
			fail(ERROR_EXCEPCION);
		} catch (OperationNotSupportedException e) {
			assertEquals("La reserva ya existe.", e.getMessage());
			assertEquals(1, reservas.getNumReservas());
		}
		try {
			reservas.insertar(RESERVA1);
			reservas.insertar(RESERVA5);
			fail(ERROR_EXCEPCION);
		} catch (OperationNotSupportedException e) {
			assertEquals("La reserva ya existe.", e.getMessage());
			assertEquals(1, reservas.getNumReservas());
		}
	}
	
	@Test
	public void insertarTresValidoTest() {
		Reservas reservas = new Reservas();
		try {
			reservas.insertar(RESERVA1);
			assertEquals(1, reservas.getNumReservas());
			assertEquals(RESERVA1, reservas.buscar(RESERVA1));
			reservas.insertar(RESERVA2);
			assertEquals(2, reservas.getNumReservas());
			assertEquals(RESERVA2, reservas.buscar(RESERVA2));
			reservas.insertar(RESERVA3);
			assertEquals(3, reservas.getNumReservas());
			assertEquals(RESERVA3, reservas.buscar(RESERVA3));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void getReservasTest() {
		Reservas reservas = new Reservas();
		try {
			reservas.insertar(RESERVA1);
			List<Reserva> reservas1 = reservas.getReservas();
			assertFalse(reservas1 == reservas.getReservas());
			assertFalse(reservas1.get(0) == reservas.getReservas().get(0));
			assertEquals(reservas1.get(0), reservas.getReservas().get(0));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	private Reservas insertarCuatro() {
		Reservas reservas = new Reservas();
		try {
			reservas.insertar(RESERVA1);
			reservas.insertar(RESERVA2);
			reservas.insertar(RESERVA3);
			reservas.insertar(RESERVA4);
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
		return reservas;
	}
	
	@Test
	public void borrarPrincipioValidoTest() {
		Reservas reservas = insertarCuatro();
		try {
			reservas.borrar(RESERVA1);
			assertEquals(3, reservas.getNumReservas());
			assertNull(reservas.buscar(RESERVA1));
			List<Reserva> reservas1 = reservas.getReservas();
			assertEquals(RESERVA2, reservas1.get(0));
			assertEquals(RESERVA3, reservas1.get(1));
			assertEquals(RESERVA4, reservas1.get(2));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void borrarMedioValidoTest() {
		Reservas reservas = insertarCuatro();
		try {
			reservas.borrar(RESERVA2);
			assertEquals(3, reservas.getNumReservas());
			assertNull(reservas.buscar(RESERVA2));
			List<Reserva> reservas1 = reservas.getReservas();
			assertEquals(RESERVA1, reservas1.get(0));
			assertEquals(RESERVA3, reservas1.get(1));
			assertEquals(RESERVA4, reservas1.get(2));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void borrarFinalValidoTest() {
		Reservas reservas = insertarCuatro();
		try {
			reservas.borrar(RESERVA4);
			assertEquals(3, reservas.getNumReservas());
			assertNull(reservas.buscar(RESERVA4));
			List<Reserva> reservas1 = reservas.getReservas();
			assertEquals(RESERVA1, reservas1.get(0));
			assertEquals(RESERVA2, reservas1.get(1));
			assertEquals(RESERVA3, reservas1.get(2));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void borrarNuloTest() {
		Reservas reservas = insertarCuatro();
		try {
			reservas.borrar(null);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("No se puede anular una reserva nula.", e.getMessage());
			assertEquals(4, reservas.getNumReservas());
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void borrarNoValidoTest() {
		Reservas reservas = insertarCuatro();
		try {
			Reserva reserva = new Reserva(PROFESOR1, AULA3, PERMANENCIA1);
			reservas.borrar(reserva);
			fail(ERROR_EXCEPCION);
		} catch (OperationNotSupportedException e) {
			assertEquals("La reserva a anular no existe.", e.getMessage());
			assertEquals(4, reservas.getNumReservas());
		}
		try {
			Reserva reserva = new Reserva(PROFESOR1, AULA2, PERMANENCIA3);
			reservas.borrar(reserva);
			fail(ERROR_EXCEPCION);
		} catch (OperationNotSupportedException e) {
			assertEquals("La reserva a anular no existe.", e.getMessage());
			assertEquals(4, reservas.getNumReservas());
		}
	}
	
	@Test
	public void borrarInsertarTest() {
		Reservas reservas = insertarCuatro();
		try {
			reservas.borrar(RESERVA1);
			assertEquals(3, reservas.getNumReservas());
			assertNull(reservas.buscar(RESERVA1));
			reservas.insertar(RESERVA1);
			assertEquals(4, reservas.getNumReservas());
			assertEquals(RESERVA1, reservas.buscar(RESERVA1));
			List<Reserva> reservas1 = reservas.getReservas();
			assertEquals(RESERVA2, reservas1.get(0));
			assertEquals(RESERVA3, reservas1.get(1));
			assertEquals(RESERVA4, reservas1.get(2));
			assertEquals(RESERVA1, reservas1.get(3));
		} catch (OperationNotSupportedException e) {
			fail(ERROR_NO_EXCEPCION);
		}
	}
	
	@Test
	public void buscarNuloTest() {
		Reservas reservas = insertarCuatro();
		assertNull(reservas.buscar(null));
	}
	
	@Test
	public void representarTest() {
		Reservas reservas = insertarCuatro();
		List<String> representacion = reservas.representar();
		assertEquals(RESERVA1.toString(), representacion.get(0));
		assertEquals(RESERVA2.toString(), representacion.get(1));
		assertEquals(RESERVA3.toString(), representacion.get(2));
		assertEquals(RESERVA4.toString(), representacion.get(3));
	}
	
	@Test
	public void getReservasProfesorTest() {
		Reservas reservas = insertarCuatro();
		List<Reserva> reservasProfesor = reservas.getReservasProfesor(PROFESOR1);
		assertEquals(RESERVA1, reservasProfesor.get(0));
		assertEquals(RESERVA2, reservasProfesor.get(1));
		assertEquals(RESERVA3, reservasProfesor.get(2));
		assertEquals(RESERVA4, reservasProfesor.get(3));
		assertEquals(4, reservasProfesor.size());
		reservasProfesor = reservas.getReservasProfesor(PROFESOR2);
		assertEquals(0, reservasProfesor.size());
	}
	
	@Test
	public void getReservasAulaTest() {
		Reservas reservas = insertarCuatro();
		List<Reserva> reservasAula = reservas.getReservasAula(AULA1);
		assertEquals(RESERVA1, reservasAula.get(0));
		assertEquals(RESERVA2, reservasAula.get(1));
		assertEquals(2, reservasAula.size());
		reservasAula = reservas.getReservasAula(AULA3);
		assertEquals(0, reservasAula.size());
	}
	
	@Test
	public void getReservasPermanenciaTest() {
		Reservas reservas = insertarCuatro();
		List<Reserva> reservasPermanencia = reservas.getReservasPermanencia(PERMANENCIA1);
		assertEquals(RESERVA1, reservasPermanencia.get(0));
		assertEquals(RESERVA3, reservasPermanencia.get(1));
		assertEquals(2, reservasPermanencia.size());
		reservasPermanencia = reservas.getReservasPermanencia(PERMANENCIA3);
		assertEquals(0, reservasPermanencia.size());
	}
	
	@Test
	public void consultarDisponibilidadValidoTest() {
		Reservas reservas = insertarCuatro();
		assertFalse(reservas.consultarDisponibilidad(AULA1, PERMANENCIA1));
		Aula aula = new Aula("Aula", PUESTOS1);
		Permanencia permanencia = new PermanenciaPorTramo(LocalDate.of(2018, 12, 22), Tramo.MANANA);
		assertTrue(reservas.consultarDisponibilidad(AULA1, permanencia));
		assertTrue(reservas.consultarDisponibilidad(aula, PERMANENCIA1));
		assertTrue(reservas.consultarDisponibilidad(aula, permanencia));
	}
	
	@Test
	public void consultarDisponibilidadNoValidoTest() {
		Reservas reservas = insertarCuatro();
		try {
			reservas.consultarDisponibilidad(null, PERMANENCIA1);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("No se puede consultar la disponibilidad de un aula nula.", e.getMessage());
		}
		try {
			reservas.consultarDisponibilidad(AULA1, null);
			fail(ERROR_EXCEPCION);
		} catch (IllegalArgumentException e) {
			assertEquals("No se puede consultar la disponibilidad de una permanencia nula.", e.getMessage());
		}
	}

}
