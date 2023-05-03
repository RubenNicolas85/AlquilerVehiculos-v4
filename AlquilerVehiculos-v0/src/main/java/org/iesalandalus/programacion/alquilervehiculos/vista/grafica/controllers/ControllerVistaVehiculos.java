package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerVistaVehiculos implements Initializable {
    
	@FXML
    private Button buttonAdd; 
	
	@FXML
    private Button buttonBack;
	
	@FXML
    private Button buttonEdit; 
	
	@FXML
    private Button buttonDelete; 
	
	@FXML
    private TextField campoBuscar;
	
	@FXML
    private TableColumn<Vehiculo,Vehiculo> colTipoVehiculo; 
	
	@FXML
    private TableColumn<Vehiculo,String> colMarca; 
    
    @FXML
    private TableColumn<Vehiculo,String> colModelo;

    @FXML
    private TableColumn<Vehiculo,String> colMatricula;
    
    @FXML
    private TableColumn<Vehiculo,Integer> colCilindrada;
    
    @FXML
    private TableColumn<Vehiculo,Integer> colNumPlazas;
    
    @FXML
    private TableColumn<Vehiculo,Integer> colNumPma;
    
    @FXML
    private TableView<Vehiculo> tablaVehiculos;
    
    private ObservableList<Vehiculo> listaVehiculos;
    
    private ObservableList<Vehiculo> listaVehiculosVisible;
    
    protected Vehiculo registro;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	listaVehiculos = FXCollections.observableArrayList();
    	listaVehiculos.setAll(Controlador.getInstancia().getVehiculos()); 
    	
    	listaVehiculosVisible = FXCollections.observableArrayList();
    	listaVehiculosVisible.setAll(Controlador.getInstancia().getVehiculos()); 
    	
    	this.colMarca.setCellValueFactory(new PropertyValueFactory<Vehiculo,String>("marca"));
		this.colModelo.setCellValueFactory(new PropertyValueFactory<Vehiculo,String>("modelo"));
		this.colMatricula.setCellValueFactory(new PropertyValueFactory<Vehiculo,String>("matricula"));
		this.colCilindrada.setCellValueFactory(new PropertyValueFactory<Vehiculo,Integer>("cilindrada"));
		this.colNumPlazas.setCellValueFactory(new PropertyValueFactory<Vehiculo,Integer>("plazas"));
		this.colNumPma.setCellValueFactory(new PropertyValueFactory<Vehiculo,Integer>("pma"));
		
		this.colTipoVehiculo.setCellValueFactory(new PropertyValueFactory<Vehiculo,Vehiculo>("tipoVehiculo"));
		colTipoVehiculo.setCellValueFactory(new PropertyValueFactory<>("tipoVehiculo"));
		colTipoVehiculo.setCellFactory(columna -> new IconoVehiculoTableCell());
		
		this.tablaVehiculos.setItems(listaVehiculosVisible);
		this.tablaVehiculos.refresh();
		
		SortedList<Vehiculo> sortedList = new SortedList<>(tablaVehiculos.getItems());
		tablaVehiculos.setItems(sortedList);
	    sortedList.comparatorProperty().bind(tablaVehiculos.comparatorProperty());
	}
    
	@FXML
	void addVehiculo (ActionEvent event) {
		
		try {
			
			// Cargo la vista
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/vistasfxml/VistaAddVehiculos.fxml")); 
			
			// Cargo la ventana
			
			Parent root = loader.load(); 
			
			
			 // Creamos una instancia del controlador de AddVehiculos: 
			
			ControllerAddVehiculos controlador = loader.getController();
            controlador.initAtributtes(listaVehiculos);

            // Se crea Stage y Scene: 
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            Image icono = new Image("file:imagenes/coche_alquiler.jpeg"); 
            stage.getIcons().add(icono); 
            
            stage.setTitle("Añadir Vehículo");
	        
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            // Se recupera el vehiculo devuelto: 
            
            Vehiculo vehiculo = controlador.getVehiculo();
            
            if (vehiculo != null) {
            	
                listaVehiculos.add(vehiculo);
                
                if (vehiculo.getModelo().toLowerCase().contains(this.campoBuscar.getText().toLowerCase())) {
                    
                	this.listaVehiculosVisible.add(vehiculo);
                	
                	try {
                		
						Controlador.getInstancia().insertar(vehiculo);
						
					} catch (Exception e) {
						
						e.printStackTrace();
					} 
                }
                
                this.tablaVehiculos.refresh();
            }

        } catch (IOException e) {
        	
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
	 
	@FXML
    void buscar(KeyEvent event) {
		
		String filtroNombre = this.campoBuscar.getText(); 
		
		if(filtroNombre.isEmpty()) {
			
			this.tablaVehiculos.setItems(listaVehiculos);
		
		}else {
			
			this.listaVehiculosVisible.clear();
			
			for (Vehiculo vehiculo : this.listaVehiculos) {
				
                if (vehiculo.getModelo().toLowerCase().contains(filtroNombre.toLowerCase())) {
                	
                    this.listaVehiculosVisible.add(vehiculo);
                }
            }
			
			this.tablaVehiculos.setItems(listaVehiculosVisible);
		}
    }
	
	@FXML
	void ordenar (ActionEvent event) {
		
		/* SortedList<Vehiculo> sortedList = new SortedList<>(tablaVehiculos.getItems());
		tablaVehiculos.setItems(sortedList);
	    sortedList.comparatorProperty().bind(tablaVehiculos.comparatorProperty()); */
	}
	
	@FXML
	void deleteVehiculo (ActionEvent event) throws Exception {
		
		Vehiculo vehiculo = this.tablaVehiculos.getSelectionModel().getSelectedItem();

        if (vehiculo == null) {
        	
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No se ha seleccionado ningún vehículo");
            alert.showAndWait();
            
        } else {
        	
            // Se elimina el vehículo: 
        	
            this.listaVehiculos.remove(vehiculo);
            this.listaVehiculosVisible.remove(vehiculo);
            
            Controlador.getInstancia().borrar(vehiculo);
            
            this.tablaVehiculos.refresh();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Info");
            alert.setContentText("Vehículo eliminado");
            alert.showAndWait();
        }
	} 
	
	@FXML
    void seleccionar(MouseEvent event) {
    	
		this.registro = this.tablaVehiculos.getSelectionModel().getSelectedItem();
    }
	
	@FXML
	void volver(ActionEvent event) {
		
		Stage escenarioActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	escenarioActual.close();
	}
}