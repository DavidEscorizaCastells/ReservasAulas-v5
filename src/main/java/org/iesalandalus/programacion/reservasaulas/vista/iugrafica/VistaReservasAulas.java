package org.iesalandalus.programacion.reservasaulas.vista.iugrafica;

import org.iesalandalus.programacion.reservasaulas.controlador.IControladorReservasAulas;
import org.iesalandalus.programacion.reservasaulas.vista.IVistaReservasAulas;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.controladoresvistas.ControladorMenuPrincipal;
import org.iesalandalus.programacion.reservasaulas.vista.iugrafica.utilidades.Dialogos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class VistaReservasAulas extends Application implements IVistaReservasAulas {
	
	private IControladorReservasAulas controlador = null;
	private static VistaReservasAulas instancia = null;
	
	public VistaReservasAulas() {
		if (instancia != null) {
			controlador = instancia.controlador;
		} else {
			instancia = this;
		}
	}

	@Override
	public void setControlador(IControladorReservasAulas controlador) {
		this.controlador=controlador;
		
	}

	@Override
	public void comenzar() {
		launch(this.getClass());
		
	}

	@Override
	public void start(Stage escenarioPrincipal) throws Exception {
		try {	
			FXMLLoader cargadorMenuPrincipal = new FXMLLoader(getClass().getResource("vistas/MenuPrincipal.fxml"));
			VBox raiz = cargadorMenuPrincipal.load();
			ControladorMenuPrincipal cMenuPrincipal = cargadorMenuPrincipal.getController();
			cMenuPrincipal.setControlador(controlador);
			Scene escena = new Scene(raiz);
			escenarioPrincipal.setOnCloseRequest(e -> confirmarSalida(escenarioPrincipal, e));
			escenarioPrincipal.setTitle("Gestión de reserva de aulas");
			escenarioPrincipal.setScene(escena);
			escenarioPrincipal.setResizable(false);
			escenarioPrincipal.show(); 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void confirmarSalida(Stage escenarioPrincipal, WindowEvent e) {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir de la aplicación?", escenarioPrincipal)) {
			controlador.salir();
			escenarioPrincipal.close();
		}
		else
			e.consume();	
	}
	
}




