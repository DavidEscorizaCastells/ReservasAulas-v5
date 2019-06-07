package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controladoresvistas;

import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class ControladorDatosAula {
	
	@FXML private TextField tfNombreAula;
	@FXML private Slider slPuestos;
	
	public Aula getAula() {
		return new Aula(tfNombreAula.getText(), (int)slPuestos.getValue());
	}
	
	public void setAula(Aula aula) {
		if (aula != null) {
			tfNombreAula.setText(aula.getNombre());
			slPuestos.setValue(aula.getPuestos());		
		} else {
			tfNombreAula.setText("");
			slPuestos.setValue(0);
		}
	}
	
	public void inhabilitaEdicionCampos() {
		tfNombreAula.setDisable(true);
	}
	
	
	
	
	
	
}
