package org.iesalandalus.programacion.reservasaulas.vista.iutextual;

import org.iesalandalus.programacion.reservasaulas.vista.IVistaReservasAulas;

public enum Opcion {
	SALIR("Salir") {
		public void ejecutar() {
			vista.salir();
		}
	},
	
	INSERTAR_AULA("Insertar aula") {
		public void ejecutar() {
			vista.insertarAula();
		}
	},
	
	BORRAR_AULA("Borrar aula") {
		public void ejecutar() {
			vista.borrarAula();
		}
	},
	
	BUSCAR_AULA("Buscar aula") {
		public void ejecutar() {
			vista.buscarAula();
		}
	},
	
	LISTAR_AULAS("Listar aulas") {
		public void ejecutar() {
			vista.listarAulas();
		}
	},
	
	INSERTAR_PROFESOR("Insertar profesor") {
		public void ejecutar() {
			vista.insertarProfesor();
		}
	},
	
	BORRAR_PROFESOR("Borrar profesor") {
		public void ejecutar() {
			vista.borrarProfesor();
		}
	},
	
	BUSCAR_PROFESOR("Buscar profesor") {
		public void ejecutar() {
			vista.buscarProfesor();
		}
	},
	
	LISTAR_PROFESORES("Listar profesores") {
		public void ejecutar() {
			vista.listarProfesores();
		}
	},
	
	INSERTAR_RESERVA("Insertar reserva") {
		public void ejecutar() {
			vista.realizarReserva();
		}
	},
	
	BORRAR_RESERVA("Borrar reserva") {
		public void ejecutar() {
			vista.aunlaReserva();
		}
	},
	
	LISTAR_RESERVAS("Listar reservas") {
		public void ejecutar() {
			vista.listarReservas();
		}
	},
	
	LISTAR_RESERVAS_AULA("Listar reservas por aula") {
		public void ejecutar() {
			vista.listarReservasAula();
		}
	},
	
	LISTAR_RESERVAS_PROFESOR("Listar reservas por profesor") {
		public void ejecutar() {
			vista.listarReservasProfesor();
		}
	},
	
	LISTAR_RESERVAS_PERMANENCIA("Listar reservas por permanencia") {
		public void ejecutar() {
			vista.listarReservasPermanencia();
		}
	},
	
	CONSULTAR_DISPONIBILIDAD("Consultar disponibilidad") {
		public void ejecutar() {
			vista.consultarDisponibilidad();
		}
	};
	
	private String mensajeAMostrar;
	private static VistaReservasAulas vista;
	
	private Opcion(String mensajeAMostrar) {
		this.mensajeAMostrar=mensajeAMostrar;
	}
	
	public String getMensaje() {
		return mensajeAMostrar;
	}
	
	public abstract void ejecutar();
	
	protected static void setVista(VistaReservasAulas vista) {
		Opcion.vista = vista;
	}
	
	@Override
	public String toString() {
		return String.format("%d.- %s", ordinal(), mensajeAMostrar);
	}
	
	public static Opcion getOpcionSegunOrdinal(int ordinal) {
		if (esOrdinalValido(ordinal))
			return values()[ordinal];
		else
			throw new IllegalArgumentException("Ordinal de la opción no válido");
	}
	
	public static boolean esOrdinalValido(int ordinal) {
		return (ordinal >= 0 && ordinal <= values().length - 1);
	}
}
