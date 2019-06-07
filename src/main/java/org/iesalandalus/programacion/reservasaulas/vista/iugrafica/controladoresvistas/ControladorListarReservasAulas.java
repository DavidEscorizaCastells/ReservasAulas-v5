package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controladoresvistas;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
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

public class ControladorListarReservasAulas {
	private IControladorReservasAulas controladorMVC;
    private ObservableList<Reserva> reservas = FXCollections.observableArrayList();
    private ObservableList<Aula> aulas = FXCollections.observableArrayList();
	
    @FXML private Label lbAula;
	@FXML private Button btListar;
    @FXML private ComboBox<Aula> cbAula;
    @FXML private Button btCancelar;
    @FXML private ListView<Reserva> lvReservasAulas;
    
    @FXML private void initialize() {
		cbAula.setItems(aulas);
    	cbAula.setConverter(new StringConverter<Aula>() {
    		
    		@Override
    		public String toString (Aula aula) {
    			return aula.getNombre();
    		}
    		
    		@Override
    		public Aula fromString(String nombre) {
    			Aula encontrada = null;
    			for (Aula aula: aulas) {
    				if (aula.getNombre().equals(nombre))
    					encontrada=aula;
    			}
    			return encontrada;
    		}
    	});
    	lvReservasAulas.setItems(reservas);
    }
    
    
    public void setControlador(IControladorReservasAulas controlador) {
		this.controladorMVC = controlador;
	}
    
    public void actualizaAulas() {
		aulas.setAll(controladorMVC.getAulas());
	}
    
    public void actualizaReservasAula() {
    	try {
    		Aula aula=cbAula.getValue();
    		reservas.setAll(controladorMVC.getReservasAula(aula));
    	} catch (Exception e) {
			Dialogos.mostrarDialogoError("Listar reservas por aula", "El aula no puede ser nula");
		}
    }
    
    @FXML
    void listarReservasAula(ActionEvent event) {
    	actualizaReservasAula();
    }

    @FXML
    void Cancelar(ActionEvent event) {
    	((Stage) btCancelar.getScene().getWindow()).close();
    }
    
    
    
}
