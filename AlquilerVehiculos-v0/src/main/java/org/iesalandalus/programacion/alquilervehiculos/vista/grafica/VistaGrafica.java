package org.iesalandalus.programacion.alquilervehiculos.vista.grafica;

import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class VistaGrafica extends Vista {

	private Stage stage; 
	
	@FXML
	private Image imagenLlave; 
	
	public VistaGrafica() {
		
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/vistasfxml/VistaPrincipal.fxml"));
		Parent raiz = fxmlLoader.load();
		Scene escena = new Scene(raiz);
		
		Image icono = new Image("file:imagenes/coche_alquiler.jpeg"); 
		stage.getIcons().add(icono); 
		
		stage.setTitle("Aplicación de Alquiler de Vehículos I.E.S. Al-Ándalus (Almería)");
		stage.setScene(escena); 
		stage.show(); 
	}

	@Override
	public void comenzar() throws Exception {
		
		launch();
		stage = new Stage(); 
		start(stage); 
		
	}

	@Override
	public void terminar() {
		
		
	}

}
