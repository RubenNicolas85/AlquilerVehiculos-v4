package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controllers;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerVistaPrincipal extends VistaGrafica {

	@FXML
	private Button buttonSalir;

	@FXML
	private Button buttonGestionarClientes;

	@FXML
	private Button buttonGestionarVehiculos;

	@FXML
	private Button buttonGestionarAlquileres;

	@FXML
	void gestionarClientes(ActionEvent event) {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
					"/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/vistasfxml/VistaClientes.fxml"));
			Parent raiz = fxmlLoader.load();
			Scene escena = new Scene(raiz);

			// ControllerVistaClientes controlador = fxmlLoader.getController();

			// Creamos el escenario

			Stage nuevoEscenario = new Stage();
			nuevoEscenario.initModality(Modality.APPLICATION_MODAL);

			Image icono = new Image("file:imagenes/coche_alquiler.jpeg");
			nuevoEscenario.getIcons().add(icono);

			nuevoEscenario.setTitle("Gestión de Clientes");

			// Establecemos la escena

			nuevoEscenario.setScene(escena);
			nuevoEscenario.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void gestionarVehiculos(ActionEvent event) {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
					"/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/vistasfxml/VistaVehiculos.fxml"));
			Parent raiz = fxmlLoader.load();
			Scene escena = new Scene(raiz);

			// ControllerVistaVehiculos controlador = fxmlLoader.getController();

			// Creamos el escenario

			Stage nuevoEscenario = new Stage();
			nuevoEscenario.initModality(Modality.APPLICATION_MODAL);

			Image icono = new Image("file:imagenes/coche_alquiler.jpeg");
			nuevoEscenario.getIcons().add(icono);

			nuevoEscenario.setTitle("Gestión de Vehículos");

			// Establecemos la escena

			nuevoEscenario.setScene(escena);
			nuevoEscenario.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void gestionarAlquileres(ActionEvent event) {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
					"/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/vistasfxml/VistaAlquileres.fxml"));
			Parent raiz = fxmlLoader.load();
			Scene escena = new Scene(raiz);

			// ControllerVistaAlquileres controlador = fxmlLoader.getController();

			// Creamos el escenario

			Stage nuevoEscenario = new Stage();
			nuevoEscenario.initModality(Modality.APPLICATION_MODAL);

			Image icono = new Image("file:imagenes/coche_alquiler.jpeg");
			nuevoEscenario.getIcons().add(icono);

			nuevoEscenario.setTitle("Gestión de Alquileres");

			// Establecemos la escena

			nuevoEscenario.setScene(escena);
			nuevoEscenario.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void terminar() {
		
		Image icono = new Image("file:imagenes/coche_alquiler.jpeg");

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setTitle("Información");
		alert.setContentText("La aplicación de alquiler de vehículos va a finalizar, hasta pronto...");

		// Crear un objeto ImageView con la imagen que deseas mostrar
		ImageView imageView = new ImageView(icono);
		imageView.setFitHeight(50); // ajustar la altura del icono
		imageView.setFitWidth(50); // ajustar el ancho del icono

		// Asignar la imagen a la ventana de alerta
		alert.setGraphic(imageView);

		alert.showAndWait();
		Controlador.getInstancia().terminar();
		
		/* Alert alert = new Alert(Alert.AlertType.INFORMATION);
		
		
		alert.setHeaderText(null);
		alert.setTitle("Información");
		alert.setContentText("La aplicación de alquiler de vehículos va a finalizar, hasta pronto...");
		alert.showAndWait();

		Controlador.getInstancia().terminar(); */
	}
}
