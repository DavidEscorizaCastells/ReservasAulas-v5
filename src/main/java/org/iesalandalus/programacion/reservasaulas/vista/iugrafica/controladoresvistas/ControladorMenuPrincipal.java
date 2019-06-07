package org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controladoresvistas;

import java.io.IOException;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorMenuPrincipal {

	private IControladorReservasAulas controladorMVC;
	private Stage listadoAulas;
	private Stage anadirAula; 
	private Stage borrarAula;
	private Stage listarReservasAulas;
	private Stage consultarDisponibilidad;
	private Stage listadoProfesores;
	private Stage anadirProfesor;
	private Stage borrarProfesor;
	private Stage listarReservasProfesor;
	private Stage listadoReservas;
	private Stage realizaReserva;
	private Stage anulaReserva;
	private ControladorListarAulas cListadoAulas;
	private ControladorAnadirAula cAnadirAula;
	private ControladorBorrarAula cBorrarAula;
	private ControladorListarReservasAulas cListarReservasAulas;
	private ControladorConsultarDisponibilidad cConsultarDisponibilidad;
	private ControladorListarProfesores cListadoProfesores;
	private ControladorAnadirProfesor cAnadirProfesor;
	private ControladorBorrarProfesor cBorrarProfesor;
	private ControladorListarReservasProfesor cListarReservasProfesor;
	private ControladorRealizaReserva cRealizaReserva;
	private ControladorListarReservas cListadoReservas;
	private ControladorAnularReserva cAnularReserva;
	
	public void setControlador(IControladorReservasAulas controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	
	@FXML
	private void salir() {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir de la aplicación?", null)) {
			controladorMVC.salir();
			System.exit(0);
		}
	}
	
    @FXML
    void listarAulas(ActionEvent event) throws IOException {
    	crearListadoAulas();
    	listadoAulas.showAndWait();
    }
    
    @FXML
    void anadirAulas(ActionEvent event) throws IOException {
    	crearAnadirAula();
		anadirAula.showAndWait();
    }

    @FXML
    void borrarAulas(ActionEvent event) throws IOException {
    	crearBorrarAula();
		borrarAula.showAndWait();
    }

    @FXML
    void mostrarReservasAula(ActionEvent event) throws IOException {
    	crearListarReservasAula();
    	listarReservasAulas.showAndWait();
    }
    
    @FXML
    void consultarDisponibilidad(ActionEvent event) throws IOException {
    	crearConsultarDisponibilidad();
    	consultarDisponibilidad.showAndWait();
    }
    
    private void crearListadoAulas() throws IOException {
		if (listadoAulas == null) {
			listadoAulas = new Stage();
			FXMLLoader cargadorListadoAulas = new FXMLLoader(getClass().getResource("../vistas/ListarAulas.fxml"));
			VBox raizListadoAulas = cargadorListadoAulas.load();
			cListadoAulas = cargadorListadoAulas.getController();
			cListadoAulas.setControlador(controladorMVC);
			cListadoAulas.actualizaAulas();
			Scene escenaListadoAulas = new Scene(raizListadoAulas);
			listadoAulas.setTitle("Listar aulas");
			listadoAulas.initModality(Modality.APPLICATION_MODAL); 
			listadoAulas.setScene(escenaListadoAulas);
		} else {
			cListadoAulas.actualizaAulas();
		}
	}
    
    private void crearAnadirAula() throws IOException {
		if (anadirAula == null) {
			anadirAula = new Stage();
			FXMLLoader cargadorAnadirAula = new FXMLLoader(getClass().getResource("../vistas/AnadirAulas.fxml"));
			VBox raizAnadirAula = cargadorAnadirAula.load();
			cAnadirAula = cargadorAnadirAula.getController();
			cAnadirAula.setControlador(controladorMVC);

			FXMLLoader cargadorDatosAula = new FXMLLoader(getClass().getResource("../vistas/DatosAula.fxml"));
			GridPane gpDatosAula = cargadorDatosAula.load();
			ControladorDatosAula cDatosAula = cargadorDatosAula.getController();
			cAnadirAula.setDatosAula(cDatosAula);
			raizAnadirAula.getChildren().add(0, gpDatosAula);
			Scene escenaAnadirAula = new Scene(raizAnadirAula);
			anadirAula.setTitle("Añadir aula");
			anadirAula.initModality(Modality.APPLICATION_MODAL); 
			anadirAula.setScene(escenaAnadirAula);
		} else {
			cAnadirAula.setAula(null);
		}
    }
    
    private void crearBorrarAula() throws IOException {
		if (borrarAula == null) {
			borrarAula = new Stage();
			FXMLLoader cargadorBorrarAula = new FXMLLoader(getClass().getResource("../vistas/BorrarAula.fxml"));
			VBox raizBorrarAula = cargadorBorrarAula.load();
			cBorrarAula = cargadorBorrarAula.getController();
			cBorrarAula.setControlador(controladorMVC);
			Scene escenaBorrarAula = new Scene(raizBorrarAula);
			borrarAula.setTitle("Borrar aula");
			borrarAula.initModality(Modality.APPLICATION_MODAL); 
			borrarAula.setScene(escenaBorrarAula);
		} else {
			cBorrarAula.setAula(null);
		}
    }

   private void crearListarReservasAula() throws IOException {
	   if (listarReservasAulas == null) {
			listarReservasAulas = new Stage();
			FXMLLoader cargadorListarReservasAulas = new FXMLLoader(getClass().getResource("../vistas/ListarReservasAula.fxml"));
			VBox raizListarReservasAulas = cargadorListarReservasAulas.load();
			cListarReservasAulas = cargadorListarReservasAulas.getController();
			cListarReservasAulas.setControlador(controladorMVC);
			cListarReservasAulas.actualizaAulas();
			Scene escenaListarReservasAulas = new Scene(raizListarReservasAulas);
			listarReservasAulas.setTitle("Listar reservas por aula");
			listarReservasAulas.initModality(Modality.APPLICATION_MODAL); 
			listarReservasAulas.setScene(escenaListarReservasAulas);
		} else {
			cListarReservasAulas.actualizaAulas();
		}
   }
   
   private void crearConsultarDisponibilidad() throws IOException {
   	if (consultarDisponibilidad == null) {
			consultarDisponibilidad = new Stage();
			FXMLLoader cargadorConsultarDisponibilidad = new FXMLLoader(getClass().getResource("../vistas/ConsultarDisponibilidad.fxml"));
			VBox raizConsultarDisponibilidad = cargadorConsultarDisponibilidad.load();
			cConsultarDisponibilidad = cargadorConsultarDisponibilidad.getController();
			cConsultarDisponibilidad.setControlador(controladorMVC);
			cConsultarDisponibilidad.actualizaAulas();
			Scene escenaConsultarDisponibilidad = new Scene(raizConsultarDisponibilidad);
			consultarDisponibilidad.setTitle("Consultar disponibilidad");
			consultarDisponibilidad.initModality(Modality.APPLICATION_MODAL); 
			consultarDisponibilidad.setScene(escenaConsultarDisponibilidad);
		} else {
			cConsultarDisponibilidad.actualizaAulas();
		}
   }

    @FXML
    void listarProfesores(ActionEvent event) throws IOException {
    	crearListadoProfesores();
    	listadoProfesores.showAndWait();
    }

    @FXML
    void anadirProfesor(ActionEvent event) throws IOException {
    	crearAnadirProfesor();
		anadirProfesor.showAndWait();
    }

    @FXML
    void borrarProfesor(ActionEvent event) throws IOException {
    	crearBorrarProfesor();
		borrarProfesor.showAndWait();
    }

    @FXML
    void listarReservasProfesor(ActionEvent event) throws IOException {
    	crearListarReservasProfesor();
    	listarReservasProfesor.showAndWait();
    }
    
    private void crearListadoProfesores() throws IOException {
		if (listadoProfesores == null) {
			listadoProfesores = new Stage();
			FXMLLoader cargadorListadoProfesores = new FXMLLoader(getClass().getResource("../vistas/ListarProfesores.fxml"));
			VBox raizListadoProfesores = cargadorListadoProfesores.load();
			cListadoProfesores = cargadorListadoProfesores.getController();
			cListadoProfesores.setControlador(controladorMVC);
			cListadoProfesores.actualizaProfesores();
			Scene escenaListadoProfesores = new Scene(raizListadoProfesores);
			listadoProfesores.setTitle("Listar aulas");
			listadoProfesores.initModality(Modality.APPLICATION_MODAL); 
			listadoProfesores.setScene(escenaListadoProfesores);
		} else {
			cListadoProfesores.actualizaProfesores();
		}
    }
    
    private void crearAnadirProfesor() throws IOException {
		if (anadirProfesor == null) {
			anadirProfesor = new Stage();
			FXMLLoader cargadorAnadirProfesor = new FXMLLoader(getClass().getResource("../vistas/AnadirProfesor.fxml"));
			VBox raizAnadirProfesor = cargadorAnadirProfesor.load();
			cAnadirProfesor = cargadorAnadirProfesor.getController();
			cAnadirProfesor.setControlador(controladorMVC);

			FXMLLoader cargadorDatosProfesor = new FXMLLoader(getClass().getResource("../vistas/DatosProfesor.fxml"));
			GridPane gpDatosProfesor = cargadorDatosProfesor.load();
			ControladorDatosProfesor cDatosProfesor = cargadorDatosProfesor.getController();
			cAnadirProfesor.setDatosProfesor(cDatosProfesor);
			raizAnadirProfesor.getChildren().add(0, gpDatosProfesor);
			Scene escenaAnadirProfesor = new Scene(raizAnadirProfesor);
			anadirProfesor.setTitle("Añadir profesor"); 
			anadirProfesor.initModality(Modality.APPLICATION_MODAL); 
			anadirProfesor.setScene(escenaAnadirProfesor);
		} else {
			cAnadirProfesor.setProfesor(null);
		}
    }
    
    private void crearBorrarProfesor() throws IOException {
		if (borrarProfesor == null) {
			borrarProfesor = new Stage();
			FXMLLoader cargadorBorrarProfesor = new FXMLLoader(getClass().getResource("../vistas/BorrarProfesor.fxml"));
			VBox raizBorrarProfesor = cargadorBorrarProfesor.load();
			cBorrarProfesor = cargadorBorrarProfesor.getController();
			cBorrarProfesor.setControlador(controladorMVC);
			Scene escenaBorrarProfesor = new Scene(raizBorrarProfesor);
			borrarProfesor.setTitle("Borrar profesor");
			borrarProfesor.initModality(Modality.APPLICATION_MODAL); 
			borrarProfesor.setScene(escenaBorrarProfesor);
		} else {
			cBorrarProfesor.setProfesor(null);
		}
    }
    
    private void crearListarReservasProfesor() throws IOException {
 	   if (listarReservasProfesor == null) {
 			listarReservasProfesor = new Stage();
 			FXMLLoader cargadorListarReservasProfesor = new FXMLLoader(getClass().getResource("../vistas/ListarReservasProfesor.fxml"));
 			VBox raizListarReservasProfesor = cargadorListarReservasProfesor.load();
 			cListarReservasProfesor = cargadorListarReservasProfesor.getController();
 			cListarReservasProfesor.setControlador(controladorMVC);
 			cListarReservasProfesor.actualizaProfesores();
 			Scene escenaListarReservasProfesor = new Scene(raizListarReservasProfesor);
 			listarReservasProfesor.setTitle("Listar reservas por profesor");
 			listarReservasProfesor.initModality(Modality.APPLICATION_MODAL); 
 			listarReservasProfesor.setScene(escenaListarReservasProfesor);
 		} else {
 			cListarReservasProfesor.actualizaProfesores();
 		}
    }
    
    @FXML
    void listarReservas(ActionEvent event) throws IOException {
    	crearListadoReservas();
    	listadoReservas.showAndWait();
    }

    @FXML
    void realizarReserva(ActionEvent event) throws IOException {
    	crearRealizaReserva();
		realizaReserva.showAndWait();
    }

    @FXML
    void anularReserva(ActionEvent event) throws IOException {
    	crearAnulaReserva();
		anulaReserva.showAndWait();
    }
    
    private void crearListadoReservas() throws IOException {
		if (listadoReservas == null) {
			listadoReservas = new Stage();
			FXMLLoader cargadorListadoReservas = new FXMLLoader(getClass().getResource("../vistas/ListarReservas.fxml"));
			VBox raizListadoReservas = cargadorListadoReservas.load();
			cListadoReservas = cargadorListadoReservas.getController();
			cListadoReservas.setControlador(controladorMVC);
			cListadoReservas.actualizaReservas();
			Scene escenaListadoReservas = new Scene(raizListadoReservas);
			listadoReservas.setTitle("Listar reservas");
			listadoReservas.initModality(Modality.APPLICATION_MODAL); 
			listadoReservas.setScene(escenaListadoReservas);
		} else {
			cListadoReservas.actualizaReservas();
		}
    }
    
    private void crearRealizaReserva() throws IOException {
    	if (realizaReserva == null) {
			realizaReserva = new Stage();
			FXMLLoader cargadorRealizaReserva = new FXMLLoader(getClass().getResource("../vistas/RealizaReserva.fxml"));
			VBox raizRealizaReserva = cargadorRealizaReserva.load();
			cRealizaReserva = cargadorRealizaReserva.getController();
			cRealizaReserva.setControlador(controladorMVC);
			cRealizaReserva.actualizaDatos();
			Scene escenaRealizaReserva = new Scene(raizRealizaReserva);
			realizaReserva.setTitle("Realizar reserva");
			realizaReserva.initModality(Modality.APPLICATION_MODAL); 
			realizaReserva.setScene(escenaRealizaReserva);
		} else {
			cRealizaReserva.actualizaDatos();
		}
    }

    private void crearAnulaReserva() throws IOException {
       	if (anulaReserva == null) {
    			anulaReserva = new Stage();
    			FXMLLoader cargadorAnulaReserva = new FXMLLoader(getClass().getResource("../vistas/AnularReserva.fxml"));
    			VBox raizAnulaReserva = cargadorAnulaReserva.load();
    			cAnularReserva = cargadorAnulaReserva.getController();
    			cAnularReserva.setControlador(controladorMVC);
    			cAnularReserva.actualizaAulas();
    			Scene escenaAnulaReserva = new Scene(raizAnulaReserva);
    			anulaReserva.setTitle("Anular reserva");
    			anulaReserva.initModality(Modality.APPLICATION_MODAL); 
    			anulaReserva.setScene(escenaAnulaReserva);
    		} else {
    			cAnularReserva.actualizaAulas();
    		}
       }
}
