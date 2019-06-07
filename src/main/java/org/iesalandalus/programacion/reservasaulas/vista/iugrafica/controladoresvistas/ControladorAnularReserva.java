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

public class ControladorAnularReserva {
	private static final ObservableList<String> HORAS = FXCollections.observableArrayList("08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00" );
	private static final Profesor PROFESOR_VALIDO= new Profesor ("a", "a@a.es");
	
	private IControladorReservasAulas controladorMVC;
	private ObservableList<Aula> aulas = FXCollections.observableArrayList();

    @FXML private ComboBox<String> cbHora;
    @FXML private RadioButton rbTramo;
    @FXML private DatePicker dpDia;
    @FXML private ChoiceBox<Aula> cbAulas;
    @FXML private Button btAnular;
    @FXML private Label lbDia;
    @FXML private RadioButton rbHora;
    @FXML private Label lbNombreAula;
    @FXML private ComboBox<Tramo> cbTramo;
    @FXML private Button btCancelar;
    
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
    
    @FXML
    void anularReserva(ActionEvent event) {
    	Reserva reserva=null;
    	Aula aula = cbAulas.getValue();
    	RadioButton seleccionado=(RadioButton)tgTipoPermanencia.getSelectedToggle();
		Permanencia permanencia=null;
		try {
			if (seleccionado==rbHora) {
				permanencia=new PermanenciaPorHora(dpDia.getValue(),cbHora.getValue());
			} else {
				permanencia=new PermanenciaPorTramo (dpDia.getValue(), cbTramo.getValue());
			}
			
				reserva = new Reserva(PROFESOR_VALIDO, aula, permanencia);
				controladorMVC.anularReserva(reserva);
				Stage propietario =((Stage) btAnular.getScene().getWindow());
				Dialogos.mostrarDialogoInformacion("Anular reserva", "Reserva anulada satisfactoriamente", propietario);
			} catch (Exception e) {
				Dialogos.mostrarDialogoError("Anular reserva", e.getMessage());
			}
    }

    @FXML
    void cancelar(ActionEvent event) {
    	((Stage) btCancelar.getScene().getWindow()).close();
    }

    public void actualizaAulas() {
		aulas.setAll(controladorMVC.getAulas());
    }

}