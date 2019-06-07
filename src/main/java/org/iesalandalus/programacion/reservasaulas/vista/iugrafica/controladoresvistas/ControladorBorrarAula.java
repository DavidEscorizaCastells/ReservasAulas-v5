package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controladoresvistas;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorBorrarAula {
	private static final int PUESTOS_VALIDOS=10;
	
	@FXML private Label lbNombreAula;
	@FXML private TextField tfNombreAula;

    @FXML
    private Button btBorrar;

    @FXML
    private Button btCancelar;
	
	public Aula getAula() {
		return new Aula(tfNombreAula.getText(),PUESTOS_VALIDOS);
	}
	
	public void setAula(Aula aula) {
		if (aula != null) {
			tfNombreAula.setText(aula.getNombre());
		} else {
			tfNombreAula.setText("");
		}
	}
	
	public void inhabilitaEdicionCampos() {
		tfNombreAula.setDisable(true);
	}
	private IControladorReservasAulas controladorMVC;
	
	public void setControlador(IControladorReservasAulas controladorMVC) {
		this.controladorMVC = controladorMVC;
	}

    @FXML
	private void borrar() {
		Aula aula = null;
		try {
			aula = getAula();
			controladorMVC.borrarAula(aula);
			Stage propietario =((Stage) btBorrar.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Borrar Aula", "Aula Borrada satisfactoriamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Borrar Aula", e.getMessage());
		}	
	}
	
	@FXML
	private void cancelar() {
		((Stage) btCancelar.getScene().getWindow()).close();
	}
}