package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controllers; 

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerDevolverAlquileres extends ControllerVistaAlquileres {

	@FXML
    private TextField campoCliente; 
	
	@FXML
    private TextField campoVehiculo; 
    
    @FXML
    private TextField campoFechaAlquiler; 
    
	@FXML
    private DatePicker campoFechaDevolucion; 

    private ObservableList<Alquiler> listaAlquileres;
    
    private Alquiler alquiler;

    @FXML
    private Button buttonGuardar; 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void initAtributtes(ObservableList<Alquiler> listaAlquileres) {
    	
        this.listaAlquileres = listaAlquileres;
    }

    public void initAtributtes(ObservableList<Alquiler> listaAlquileres, Alquiler alquiler) {
    	
        this.listaAlquileres = listaAlquileres;
        this.alquiler = alquiler;
        
        // Se cargan los datos del alquiler: 
        
        this.campoCliente.setText(alquiler.getCliente().toString());
        this.campoVehiculo.setText(alquiler.getVehiculo().toString());
        this.campoFechaAlquiler.setText(alquiler.getFechaAlquiler().toString());
        
        this.campoCliente.setDisable(true);
        this.campoVehiculo.setDisable(true);
        this.campoFechaAlquiler.setDisable(true);
        
        this.campoFechaDevolucion.setValue(alquiler.getFechaDevolucion());
    }

    public Alquiler getAlquiler() {
    	
        return this.alquiler;
    }

    @FXML
    void guardar(ActionEvent event) {
    	
    	try {
    		
    		// Se recuperan los datos: 
        	
        	Cliente cliente = this.alquiler.getCliente(); 
            Vehiculo vehiculo = this.alquiler.getVehiculo(); 
            LocalDate fechaAlquiler = this.alquiler.getFechaAlquiler(); 
            LocalDate fechaDevolucion = this.campoFechaDevolucion.getValue();

            // Se crea el alquiler y se establece la fecha de devolución: 
            
            Alquiler alquiler = new Alquiler(cliente, vehiculo, fechaAlquiler);
                    
        	// Se comprueba si existe el alquiler: 
        	
            if (listaAlquileres.contains(alquiler)){

                // Establecer la fecha de devolución del alquiler: 
            	
            	if (this.alquiler != null) {

                    // Se modifica el objeto:
                	
                	this.alquiler.devolver(fechaDevolucion);
    					
    				Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Información");
                    alert.setContentText("Se ha finalizado el alquiler correctamente");
                    alert.showAndWait();
                }

                // Cerrar la ventana:
                
                Stage stage = (Stage) this.buttonGuardar.getScene().getWindow();
                stage.close();
            }
    	}catch(Exception e) {
    		
    		Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error:");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
    	}
    }
}
