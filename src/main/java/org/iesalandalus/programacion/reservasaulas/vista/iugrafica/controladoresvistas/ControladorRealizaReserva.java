package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controladoresvistas;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Aula;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Permanencia;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorHora;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.PermanenciaPorTramo;
import org.iesalandalus.programacion.reservasaulas.modelo.dominio.permanencia.Tramo;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ControladorRealizaReserva {
	private static final ObservableList<String> HORAS = FXCollections.observableArrayList("08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00" );
	
	private IControladorReservasAulas controladorMVC;
	private ObservableList<Aula> aulas = FXCollections.observableArrayList();
	private ObservableList<Profesor> profesores = FXCollections.observableArrayList();
	
    @FXML private DatePicker dpDia;
    @FXML private Label lbProfesor;
    @FXML private Label lbDia;
    @FXML private ChoiceBox<Aula> cbAulas;
    @FXML private ChoiceBox<Profesor> cbProfesores;
    @FXML private Button btReservar;
    @FXML private Label lbNombreAula;
    @FXML private Button btCancelar;
    @FXML private RadioButton rbTramo;
    @FXML private ComboBox<Tramo> cbTramo;
    @FXML private RadioButton rbHora;
    @FXML private ComboBox<String> cbHora;
    
    private ToggleGroup tgTipoPermanencia = new ToggleGroup();
    
    public void setControlador(IControladorReservasAulas controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
    
    @FXML private void initialize() {
    	cbTramo.setItems(FXCollections.observableArrayList(Tramo.values()));
    	cbHora.setItems(HORAS);
    	rbTramo.setToggleGroup(tgTipoPermanencia);
    	rbHora.setToggleGroup(tgTipoPermanencia);
    	tgTipoPermanencia.selectedToggleProperty().addListener((observable, oldValue, newValue) -> habilitaSeleccion());
    	cbTramo.setDisable(true);
    	cbHora.setDisable(true);
    	cbProfesores.setItems(profesores);
    	cbProfesores.setConverter(new StringConverter<Profesor>() {
    		
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
    	
    	
    	cbAulas.setItems(aulas);
    	cbAulas.setConverter(new StringConverter<Aula>() {
    		
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
    }
    
    private void habilitaSeleccion() {
    	RadioButton seleccionado=(RadioButton)tgTipoPermanencia.getSelectedToggle();
    	if (seleccionado==rbHora) {
    		cbHora.setDisable(false);
    		cbTramo.setDisable(true);
    	} else {
    		cbHora.setDisable(true);
    		cbTramo.setDisable(false);
    	}
    }
    
	@FXML void realizarReserva(ActionEvent event) {
		Reserva reserva=null;
		Aula aula = cbAulas.getValue();
		Profesor profesor = cbProfesores.getValue();
		RadioButton seleccionado=(RadioButton)tgTipoPermanencia.getSelectedToggle();
		Permanencia permanencia=null;
		try {
			if (seleccionado==rbHora) {
				permanencia=new PermanenciaPorHora(dpDia.getValue(),cbHora.getValue());
			} else {
				permanencia=new PermanenciaPorTramo (dpDia.getValue(), cbTramo.getValue());
			}
			
				reserva = new Reserva(profesor, aula, permanencia);
				controladorMVC.realizarReserva(reserva);
				Stage propietario =((Stage) btReservar.getScene().getWindow());
				Dialogos.mostrarDialogoInformacion("Realizar reserva", "Reserva realizada satisfactoriamente", propietario);
			} catch (Exception e) {
				Dialogos.mostrarDialogoError("Reservar aula", e.getMessage());
			}
    }

    @FXML void cancelar(ActionEvent event) {
    	((Stage) btCancelar.getScene().getWindow()).close();
    }
    
    public void actualizaDatos() {
		aulas.setAll(controladorMVC.getAulas());
		profesores.setAll(controladorMVC.getProfesores());
    }

}