package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controllers; 

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class ControllerAddAlquileres extends ControllerVistaAlquileres {

	@FXML
    private ChoiceBox selCliente; 
	
	@FXML
    private ChoiceBox selVehiculo; 
    
    @FXML
    private DatePicker selFechaAlquiler; 

    private Alquiler alquiler;

    private ObservableList<Alquiler> listaAlquileres;
    
    @FXML
    private Button buttonGuardar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    	// Obtener la lista de clientes y vehículos
        ArrayList<Cliente> listaClientes = (ArrayList<Cliente>) Controlador.getInstancia().getClientes();
        ArrayList<Vehiculo> listaVehiculos = (ArrayList<Vehiculo>) Controlador.getInstancia().getVehiculos();
        
        // Agregar la lista de clientes y vehículos a las ChoiceBox
        selCliente.getItems().addAll(listaClientes);
        selVehiculo.getItems().addAll(listaVehiculos);
    }

    public void initAtributtes(ObservableList<Alquiler> listaAlquileres) {
    	
    	this.listaAlquileres = listaAlquileres;
    }

    public void initAtributtes(ObservableList<Alquiler> listaAlquileres, Alquiler alquiler) {
    	
        this.listaAlquileres = listaAlquileres;
        this.alquiler = alquiler;
        
        // Se cargan los datos del alquiler: 
        
        this.selCliente.setValue(this.alquiler.getCliente()); 
        this.selVehiculo.setValue(this.alquiler.getVehiculo()); 
        this.selFechaAlquiler.setValue(this.alquiler.getFechaAlquiler()); 
    }

    public Alquiler getAlquiler() {
    	
        return this.alquiler;
    }

	@FXML
	void guardar(ActionEvent event) {

		try {

			// Se recuperan los datos del alquiler:

			Cliente cliente = (Cliente) this.selCliente.getValue();
			Vehiculo vehiculo = (Vehiculo) this.selVehiculo.getValue();
			LocalDate fechaAlquiler = selFechaAlquiler.getValue();

			// Se crea el alquiler:

			Alquiler alquiler = new Alquiler(cliente, vehiculo, fechaAlquiler);
			
			Controlador.getInstancia().insertar(alquiler);
			
			if (!listaAlquileres.contains(alquiler)) {

				if (this.alquiler == null) {

					// Se inserta el alquiler:

					this.alquiler = alquiler;
					
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setTitle("Información");
					alert.setContentText("Se ha añadido el alquiler correctamente");
					alert.showAndWait();
					
					// Cerrar la ventana:

					Stage stage = (Stage) this.buttonGuardar.getScene().getWindow();
					stage.close();
					
				} else {
					
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setTitle("Error");
					alert.setContentText("El Alquiler ya existe");
					alert.showAndWait();
					
					// Cerrar la ventana:
					
					Stage stage = (Stage) this.buttonGuardar.getScene().getWindow();
					stage.close();
				}
			}
		} catch (Exception e) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

}
