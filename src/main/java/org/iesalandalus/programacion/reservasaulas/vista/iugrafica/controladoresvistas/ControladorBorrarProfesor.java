package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controladoresvistas;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorBorrarProfesor {
	private static final String CORREO_VALIDO="a@a.es";
	private static final String TELEFONO_VALIDO="666666666";
	
	@FXML private Label lbNombreProfesor;
	@FXML private TextField tfNombreProfesor;

    @FXML
    private Button btBorrar;

    @FXML
    private Button btCancelar;
	
	public Profesor getProfesor() {
		return new Profesor(tfNombreProfesor.getText(),CORREO_VALIDO, TELEFONO_VALIDO);
	}
	
	public void setProfesor(Profesor profesor) {
		if (profesor != null) {
			tfNombreProfesor.setText(profesor.getNombre());
		} else {
			tfNombreProfesor.setText("");
		}
	}
	
	public void inhabilitaEdicionCampos() {
		tfNombreProfesor.setDisable(true);
	}
	private IControladorReservasAulas controladorMVC;
	
	public void setControlador(IControladorReservasAulas controladorMVC) {
		this.controladorMVC = controladorMVC;
	}

    @FXML
	private void borrar() {
		Profesor profesor = null;
		try {
			profesor = getProfesor();
			controladorMVC.borrarProfesor(profesor);
			Stage propietario =((Stage) btBorrar.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Borrar Profesor", "Profesor borrado satisfactoriamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Borrar Profesor", e.getMessage());
		}	
	}
	
	@FXML
	private void cancelar() {
		((Stage) btCancelar.getScene().getWindow()).close();
	}
}
