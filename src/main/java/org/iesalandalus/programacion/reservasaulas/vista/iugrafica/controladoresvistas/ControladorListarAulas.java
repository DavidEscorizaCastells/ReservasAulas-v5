package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controladoresvistas;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ControladorListarAulas {

	private IControladorReservasAulas controladorMVC;
	private ObservableList<String> aulas = FXCollections.observableArrayList();
	
	@FXML
	private ListView<String> lvAulas;

	@FXML
	private Label lbTitulo;
	
	@FXML
	private void initialize() {
		lvAulas.setItems(aulas);
	}
	
	public void setControlador(IControladorReservasAulas controlador) {
		this.controladorMVC = controlador;
	}
	
	public void actualizaAulas() {
		aulas.setAll(controladorMVC.representarAulas());
	}
	
}
