package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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

public class ControllerVistaAlquileres implements Initializable {

	@FXML
    private Button buttonAdd; 
	
	@FXML
    private Button buttonDevolver; 
	
	@FXML
    private Button buttonBack; 
	
	@FXML
    private TextField campoBuscar;
	
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
    
    protected Alquiler registro;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	listaAlquileres = FXCollections.observableArrayList();
    	listaAlquileres.setAll(Controlador.getInstancia().getAlquileres()) ;
    	
    	listaAlquileresVisible = FXCollections.observableArrayList();
    	listaAlquileresVisible.setAll(Controlador.getInstancia().getAlquileres()); 
    	
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
		
		
	
		
		/* SortedList<Alquiler> sortedList = new SortedList<>(tablaAlquileres.getItems());
	    tablaAlquileres.setItems(sortedList);
	    sortedList.comparatorProperty().bind(tablaAlquileres.comparatorProperty()); */
	}
    
    @FXML
	void addAlquiler (ActionEvent event) throws Exception {
		
		try {
			
			// Cargo la vista
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/vistasfxml/VistaAddAlquileres.fxml")); 
			
			// Cargo la ventana
			
			Parent root = loader.load(); 
			
			
			 // Creamos una instancia del controlador de AddClientes: 
			
			ControllerAddAlquileres controlador = loader.getController();
            controlador.initAtributtes(listaAlquileres);

            // Se crea Stage y Scene: 
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            Image icono = new Image("file:imagenes/coche_alquiler.jpeg"); 
            stage.getIcons().add(icono); 
            
            stage.setTitle("Añadir Alquiler");
	        
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            // Se recupera el alquiler : 
            
            Alquiler alquiler = controlador.getAlquiler();
            
            if (alquiler != null) {
            	
                listaAlquileres.add(alquiler);
                
                if (alquiler.getCliente().getNombre().toLowerCase().contains(this.campoBuscar.getText().toLowerCase())) {
                    
                	this.listaAlquileresVisible.add(alquiler);
                	
                }
                
                this.tablaAlquileres.refresh();
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
			
			this.tablaAlquileres.setItems(listaAlquileres);
		
		}else {
			
			this.listaAlquileresVisible.clear();
			
			for (Alquiler alquiler : this.listaAlquileres) {
				
                if (alquiler.getCliente().getNombre().toLowerCase().contains(filtroNombre.toLowerCase())) {
                	
                    this.listaAlquileresVisible.add(alquiler);
                }
            }
			
			this.tablaAlquileres.setItems(listaAlquileresVisible);
		}
    }
	
	@FXML
	void devolver(ActionEvent event) {

		Alquiler alquiler = this.tablaAlquileres.getSelectionModel().getSelectedItem();

		if (alquiler == null) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("No se ha seleccionado ningún alquiler");
			alert.showAndWait();
		
		}else if(this.tablaAlquileres.getSelectionModel().getSelectedItem().getFechaDevolucion() != null) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("No se puede devolver un alquiler ya devuelto");
			alert.showAndWait();
			
			
		}else {

			try {
				
				// Cargo la vista

				FXMLLoader loader = new FXMLLoader(getClass().getResource(
						"/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/vistasfxml/VistaDevolverAlquileres.fxml"));

				// Cargo la ventana

				Parent root = loader.load();

				// Creamos una instancia del controlador de AddClientes:

				ControllerDevolverAlquileres controlador = loader.getController();
				controlador.initAtributtes(listaAlquileres, alquiler);

				// Se crea Stage y Scene:

				Scene scene = new Scene(root);
				Stage stage = new Stage();

				Image icono = new Image("file:imagenes/coche_alquiler.jpeg");
				stage.getIcons().add(icono);

				stage.setTitle("Devolver Alquiler");

				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setScene(scene);
				stage.showAndWait();

				// Se recupera el alquiler devuelto:

				Alquiler alquilerSeleccionado = controlador.getAlquiler();

				if (alquiler != null) {

					if (alquilerSeleccionado.getCliente().getNombre().toLowerCase()
							.contains(this.campoBuscar.getText().toLowerCase())) {

						try {

							Controlador.getInstancia().devolver(alquilerSeleccionado.getCliente(), alquiler.getFechaDevolucion());

						} catch (Exception e) {

							e.printStackTrace();
						}
					}

					this.tablaAlquileres.refresh();
				}

			} catch (IOException e) {

				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Error");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}
	}
	
	@FXML
	void ordenar (ActionEvent event) {
		
		/* SortedList<Alquiler> sortedList = new SortedList<>(tablaAlquileres.getItems());
	    tablaAlquileres.setItems(sortedList);
	    sortedList.comparatorProperty().bind(tablaAlquileres.comparatorProperty()); */
	}
	
	@FXML
    void seleccionar(MouseEvent event) {
    	
		this.registro = this.tablaAlquileres.getSelectionModel().getSelectedItem();
    }
	
	@FXML
	void volver(ActionEvent event) {
		
		Stage escenarioActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	escenarioActual.close();
	}
}
