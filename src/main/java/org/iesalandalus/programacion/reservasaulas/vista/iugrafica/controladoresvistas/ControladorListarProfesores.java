package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controladoresvistas;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ControladorListarProfesores {
	private IControladorReservasAulas controladorMVC;
	private ObservableList<String> profesores = FXCollections.observableArrayList();
	
	@FXML
	private ListView<String> lvProfesores;

	@FXML
	private Label lbTitulo;
	
	@FXML
	private void initialize() {
		lvProfesores.setItems(profesores);
	}
	
	public void setControlador(IControladorReservasAulas controlador) {
		this.controladorMVC = controlador;
	}
	
	public void actualizaProfesores() {
		profesores.setAll(controladorMVC.representarProfesores());
	}
}
