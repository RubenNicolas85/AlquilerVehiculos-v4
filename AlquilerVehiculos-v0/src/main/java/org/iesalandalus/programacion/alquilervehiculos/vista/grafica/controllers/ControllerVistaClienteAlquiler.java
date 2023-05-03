package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControllerVistaClienteAlquiler implements Initializable {

	@FXML
    private Button buttonBack; 
	
	@FXML ChoiceBox selCliente; 
	
	@FXML
    private TableColumn<Alquiler,Cliente> colCliente;
    
    @FXML
    private TableColumn<Alquiler,Vehiculo> colVehiculo; 
    
    @FXML
    private TableColumn<Alquiler,Vehiculo> colDibujoVehiculo; 
    
    @FXML
    private TableColumn<Alquiler,LocalDate> colFechaAlquiler; 
    
    @FXML
    private TableColumn<Alquiler,LocalDate> colFechaDevolucion; 
    
    @FXML
    private TableColumn<Alquiler,Double> colGetPrecio; 

    @FXML
	protected TableView<Alquiler> tablaAlquileres;
    
    protected ObservableList<Alquiler> listaAlquileres;
    
    protected ObservableList<Alquiler> listaAlquileresVisible;

    protected Cliente cliente; 
    
    protected Alquiler registro;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	// Obtener la lista de clientes: 
    	
        ArrayList<Cliente> listaClientes = (ArrayList<Cliente>) Controlador.getInstancia().getClientes();
        
        // Agregar la lista de clientes a la ChoiceBox: 
        
        selCliente.getItems().addAll(listaClientes);
        
        listaAlquileres = FXCollections.observableArrayList();
    	
    	listaAlquileresVisible = FXCollections.observableArrayList();
    	
    	this.colCliente.setCellValueFactory(new PropertyValueFactory<Alquiler,Cliente>("cliente"));
		this.colVehiculo.setCellValueFactory(new PropertyValueFactory<Alquiler,Vehiculo>("vehiculo"));
		this.colFechaAlquiler.setCellValueFactory(new PropertyValueFactory<Alquiler,LocalDate>("fechaAlquiler"));
		this.colFechaDevolucion.setCellValueFactory(new PropertyValueFactory<Alquiler,LocalDate>("fechaDevolucion"));
		this.colGetPrecio.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrecio()).asObject());
		
		this.colDibujoVehiculo.setCellValueFactory(new PropertyValueFactory<Alquiler,Vehiculo>("vehiculo"));
		colDibujoVehiculo.setCellValueFactory(new PropertyValueFactory<>("vehiculo"));
		colDibujoVehiculo.setCellFactory(columna -> new TipoVehiculoTableCell());	
		    
		this.tablaAlquileres.setItems(listaAlquileresVisible);
		this.tablaAlquileres.refresh();
	}
    
    @FXML
    void filtrarAlquilerCliente() {
    	
        this.cliente = (Cliente) selCliente.getValue(); 
        listaAlquileres.setAll(Controlador.getInstancia().getAlquileres(this.cliente));
        listaAlquileresVisible.setAll(listaAlquileres);
        
        this.tablaAlquileres.setItems(listaAlquileresVisible);
        
        this.tablaAlquileres.refresh();
    }
    
    @FXML
    void seleccionar(MouseEvent event) {
    	
		this.registro = this.tablaAlquileres.getSelectionModel().getSelectedItem();
    }
	
    @FXML
	void ordenar (ActionEvent event) {
		
		/* SortedList<Cliente> sortedList = new SortedList<>(tablaClientes.getItems());
	    tablaClientes.setItems(sortedList);
	    sortedList.comparatorProperty().bind(tablaClientes.comparatorProperty()); */
	}
	
	@FXML
	void volver(ActionEvent event) {
		
		Stage escenarioActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	escenarioActual.close();
	}
}
