package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controladoresvistas;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ControladorListarReservas {
	private IControladorReservasAulas controladorMVC;
	private ObservableList<String> reservas = FXCollections.observableArrayList();
	
	@FXML
	private ListView<String> lvReservas;

	@FXML
	private Label lbTitulo;
	
	@FXML
	private void initialize() {
		lvReservas.setItems(reservas);
	}
	
	public void setControlador(IControladorReservasAulas controlador) {
		this.controladorMVC = controlador;
	}
	
	public void actualizaReservas() {
		reservas.setAll(controladorMVC.representarReservas());
	}
}
