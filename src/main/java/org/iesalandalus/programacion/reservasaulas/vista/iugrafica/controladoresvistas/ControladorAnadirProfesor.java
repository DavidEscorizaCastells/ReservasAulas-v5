package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controladoresvistas;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControladorAnadirProfesor {
	
	private IControladorReservasAulas controladorMVC;
	private ControladorDatosProfesor datosProfesor;

	public void setControlador(IControladorReservasAulas controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	
	public void setDatosProfesor(ControladorDatosProfesor datosProfesor) {
		this.datosProfesor = datosProfesor;
	}
	
	public void setProfesor (Profesor profesor) {
		datosProfesor.setProfesor(profesor);
	}

    @FXML
    private Button btAnadir;

    @FXML
    private Label lbTitulo;

    @FXML
    private Button btCancelar;

    @FXML
	private void anadir() {
		Profesor profesor = null;
		try {
			profesor = datosProfesor.getProfesor();
			controladorMVC.insertarProfesor(profesor);
			Stage propietario =((Stage) btAnadir.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Profesor", "Profesor añadido satisfactoriamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Añadir Profesor", e.getMessage());
		}	
	}
	
	@FXML
	private void cancelar() {
		((Stage) btCancelar.getScene().getWindow()).close();
	}
}
