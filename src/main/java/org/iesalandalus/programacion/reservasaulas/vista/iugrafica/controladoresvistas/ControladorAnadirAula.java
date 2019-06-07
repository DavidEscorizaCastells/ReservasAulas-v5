package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controladoresvistas;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControladorAnadirAula {
	
	private IControladorReservasAulas controladorMVC;
	private ControladorDatosAula datosAula;
	
	public void setControlador(IControladorReservasAulas controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	
	public void setDatosAula(ControladorDatosAula datosAula) {
		this.datosAula = datosAula;
	}
	
	public void setAula (Aula aula) {
		datosAula.setAula(aula);
	}

    @FXML
    private Button btAnadir;

    @FXML
    private Button btCancelar;

    @FXML
	private void anadir() {
		Aula aula = null;
		try {
			aula = datosAula.getAula();
			controladorMVC.insertarAula(aula);
			Stage propietario =((Stage) btAnadir.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Aula", "Aula añadida satisfactoriamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Añadir Aula", e.getMessage());
		}	
	}
	
	@FXML
	private void cancelar() {
		((Stage) btCancelar.getScene().getWindow()).close();
	}
}