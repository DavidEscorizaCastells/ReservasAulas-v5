package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controladoresvistas;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ControladorListarReservasProfesor {

	private IControladorReservasAulas controladorMVC;
    private ObservableList<Reserva> reservas = FXCollections.observableArrayList();
    private ObservableList<Profesor> profesores = FXCollections.observableArrayList();
	
    @FXML private Button btListarReservasProfesor;
    @FXML private ListView<Reserva> lvReservasProfesor;
    @FXML private Label lbProfesor;
    @FXML private ComboBox<Profesor> cbProfesor;
    @FXML private Button btCancelar;

    @FXML
    private void initialize() {
    	cbProfesor.setItems(profesores);
    	cbProfesor.setConverter(new StringConverter<Profesor>() {
    		
    		@Override
    		public String toString (Profesor profesor) {
    			return profesor.getNombre();
    		}
    		
    		@Override
    		public Profesor fromString(String nombre) {
    			Profesor encontrado = null;
    			for (Profesor profesor: profesores) {
    				if (profesor.getNombre().equals(nombre))
    					encontrado=profesor;
    			}
    			return encontrado;
    		}
    	});
    	lvReservasProfesor.setItems(reservas);
    }
    
    public void setControlador(IControladorReservasAulas controlador) {
		this.controladorMVC = controlador;
	}
    
    public void actualizaProfesores() {
		profesores.setAll(controladorMVC.getProfesores());
	}
    
    public void actualizaReservasProfesor() {
    	try {
    		Profesor profesor=cbProfesor.getValue();
    		reservas.setAll(controladorMVC.getReservasProfesor(profesor));
    	} catch (Exception e) {
			Dialogos.mostrarDialogoError("Listar reservas por aula", "El profesor no puede ser nula");
		}
    }
    
    @FXML
    void listarReservasProfesor(ActionEvent event) {
    	actualizaReservasProfesor();
    }

    @FXML
    void cancelar(ActionEvent event) {
    	((Stage) btCancelar.getScene().getWindow()).close();
    }

}
