package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controllers; 

import java.net.URL;
import java.util.ResourceBundle;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerEditClientes implements Initializable {

    @FXML
    private TextField campoNombre; 
    @FXML
    private TextField campoDni; ;
    @FXML
    private TextField campoTelefono; 

    private Cliente cliente;

    private ObservableList<Cliente> listaClientes;
    @FXML
    private Button buttonGuardar; 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void initAtributtes(ObservableList<Cliente> listaClientes) {
    	
        this.listaClientes = listaClientes;
    }

    public void initAtributtes(ObservableList<Cliente> listaClientes, Cliente cliente) {
    	
        this.listaClientes = listaClientes;
        this.cliente = cliente;
        
        // Se cargan los datos del cliente: 
        
        this.campoNombre.setText(this.cliente.getNombre());
        this.campoDni.setText(this.cliente.getDni());
        this.campoTelefono.setText(this.cliente.getTelefono());
        
        this.campoDni.setDisable(true);
    }

    public Cliente getCliente() {
    	
        return this.cliente;
    }

	@FXML
	void guardar(ActionEvent event) {

		try {

			// Se recuperan los datos:

			String nombre = this.campoNombre.getText();
			String dni = this.campoDni.getText();
			String telefono = this.campoTelefono.getText();

			// Se crea el cliente:

			Cliente cliente = new Cliente(nombre, dni, telefono);

			// Se comprueba si existe el cliente:

			if (listaClientes.contains(cliente)) {

				// Modificar el cliente:

				if (this.cliente != null) {

					// Se modifica el objeto:
					this.cliente.setNombre(nombre);
					this.cliente.setTelefono(telefono);

					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setTitle("Información");
					alert.setContentText("Se ha modificado el cliente correctamente");
					alert.showAndWait();
				}

				// Cerrar la ventana:

				Stage stage = (Stage) this.buttonGuardar.getScene().getWindow();
				stage.close();
			}
		} catch (Exception e) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error:");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
}
