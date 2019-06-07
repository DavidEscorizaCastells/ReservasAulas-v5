package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controladoresvistas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ControladorDatosProfesor {
	
	@FXML private TextField tfNombreProfesor;
	@FXML private TextField tfCorreo;
	@FXML private TextField tfTelefono;
	
	public Profesor getProfesor() {
		if (tfTelefono.getText()!=null || !tfTelefono.getText().trim().equals(""))
			return new Profesor(tfNombreProfesor.getText(), tfCorreo.getText(), tfTelefono.getText());
		else
			return new Profesor(tfNombreProfesor.getText(), tfCorreo.getText());
	}
	
	public void setProfesor(Profesor profesor) {
		if (profesor != null) {
			tfNombreProfesor.setText(profesor.getNombre());
			tfCorreo.setText(profesor.getCorreo());
			tfTelefono.setText(profesor.getTelefono());
		} else {
			tfNombreProfesor.setText("");
			tfCorreo.setText("");
			tfTelefono.setText("");
		}
	}
	
	public void inhabilitaEdicionCampos() {
		tfNombreProfesor.setDisable(true);
		tfCorreo.setDisable(true);
		tfTelefono.setDisable(true);
		
	}
}
