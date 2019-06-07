package org.iesalandalus.programacion.reservasaulas.vista;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;

public interface IVistaReservasAulas {
	
	void setControlador(IControladorReservasAulas controlador);

	void comenzar();

}