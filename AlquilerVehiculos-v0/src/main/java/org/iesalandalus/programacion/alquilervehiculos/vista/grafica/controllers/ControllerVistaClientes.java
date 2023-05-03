package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
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

public class ControllerVistaClientes implements Initializable {

	@FXML
    private Button buttonAdd; 
	
	@FXML
    private Button buttonEdit; 
	
	@FXML
	private Button buttonVerAlquileres; 
	
	@FXML
    private Button buttonDelete; 
	
	@FXML
    private Button buttonBack; 
	
	@FXML
    private TextField campoBuscar;
	
	@FXML
    private TableColumn<Cliente,String> colNombre;
    
    @FXML
    private TableColumn<Cliente,String> colDni;

    @FXML
    private TableColumn<Cliente,String> colTelefono;

    @FXML
	protected TableView<Cliente> tablaClientes;
    
    private ObservableList<Cliente> listaClientes;
    
    private ObservableList<Cliente> listaClientesVisible;
    
    protected Cliente registro;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	listaClientes = FXCollections.observableArrayList();
    	listaClientes.setAll(Controlador.getInstancia().getClientes()); 
    	
    	listaClientesVisible = FXCollections.observableArrayList();
    	listaClientesVisible.setAll(Controlador.getInstancia().getClientes()); 
    	
    	this.colNombre.setCellValueFactory(new PropertyValueFactory<Cliente,String>("nombre"));
		this.colDni.setCellValueFactory(new PropertyValueFactory<Cliente,String>("dni"));
		this.colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente,String>("telefono"));
		
		this.tablaClientes.setItems(listaClientesVisible);
		this.tablaClientes.refresh();
		
		SortedList<Cliente> sortedList = new SortedList<>(tablaClientes.getItems());
	    tablaClientes.setItems(sortedList);
	    sortedList.comparatorProperty().bind(tablaClientes.comparatorProperty());
	}
	
    @FXML
	void addCliente (ActionEvent event) {
		
		try {
			
			// Cargo la vista
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/vistasfxml/VistaAddClientes.fxml")); 
			
			// Cargo la ventana
			
			Parent root = loader.load(); 
			
			
			 // Creamos una instancia del controlador de AddClientes: 
			
			ControllerAddClientes controlador = loader.getController();
            controlador.initAtributtes(listaClientes);

            // Se crea Stage y Scene: 
            
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            
            Image icono = new Image("file:imagenes/coche_alquiler.jpeg"); 
            stage.getIcons().add(icono); 
            
            stage.setTitle("Añadir Cliente");
	        
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            // Se recupera el cliente devuelto: 
            
            Cliente cliente = controlador.getCliente();
            
            if (cliente != null) {
            	
                listaClientes.add(cliente);
                
                if (cliente.getNombre().toLowerCase().contains(this.campoBuscar.getText().toLowerCase())) {
                    
                	this.listaClientesVisible.add(cliente);
                	
                	try {
						Controlador.getInstancia().insertar(cliente);
					} catch (Exception e) {
						
						e.printStackTrace();
					} 
                }
                
                this.tablaClientes.refresh();
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
			
			this.tablaClientes.setItems(listaClientes);
		
		}else {
			
			this.listaClientesVisible.clear();
			
			for (Cliente cliente : this.listaClientes) {
				
                if (cliente.getNombre().toLowerCase().contains(filtroNombre.toLowerCase())) {
                	
                    this.listaClientesVisible.add(cliente);
                }
            }
			
			this.tablaClientes.setItems(listaClientesVisible);
		}
    }
	
	@FXML
	void editCliente (ActionEvent event) {
		
		Cliente cliente = this.tablaClientes.getSelectionModel().getSelectedItem();

        if (cliente == null) {
        	
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No se ha seleccionado ningún cliente");
            alert.showAndWait();
            
        } else {

            try {

                // Cargo la vista
            	FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/vistasfxml/VistaEditClientes.fxml")); 

                // Cargo la ventana
                Parent root = loader.load();

                // Se crea una instancia del controlador de EditClientes: 
                
                ControllerEditClientes controlador = loader.getController();
                controlador.initAtributtes(listaClientes, cliente);

                // Se crean Stage y Scene: 
                
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                
                Image icono = new Image("file:imagenes/coche_alquiler.jpeg"); 
                stage.getIcons().add(icono); 
                
                stage.setTitle("Modificar Cliente");
                
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();
                
                // Se recupera el cliente devuelto: 
                
                Cliente clienteSeleccionado = controlador.getCliente();
                
                if (clienteSeleccionado != null) {
                	
                	this.tablaClientes.refresh();
                	
                    if (!clienteSeleccionado.getNombre().toLowerCase().contains(this.campoBuscar.getText().toLowerCase())) {
                        
                    	this.listaClientesVisible.remove(clienteSeleccionado);
                    }
                    try {
                    	
						Controlador.getInstancia().modificar(clienteSeleccionado, clienteSeleccionado.getNombre(), clienteSeleccionado.getTelefono());
					
                    } catch (Exception e) {
						
						e.printStackTrace();
					} 
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
		
		/* SortedList<Cliente> sortedList = new SortedList<>(tablaClientes.getItems());
	    tablaClientes.setItems(sortedList);
	    sortedList.comparatorProperty().bind(tablaClientes.comparatorProperty()); */
	}
	
	@FXML
	void verAlquileres(ActionEvent event) {

		try {

			// Cargo la vista
			FXMLLoader loader = new FXMLLoader(getClass().getResource(
					"/org/iesalandalus/programacion/alquilervehiculos/vista/grafica/vistasfxml/VistaClienteAlquiler.fxml"));

			// Cargo la ventana
			Parent root = loader.load();

			// Se crea una instancia del controlador de EditClientes:

			ControllerVistaClienteAlquiler controlador = loader.getController();

			// Se crean Stage y Scene:

			Scene scene = new Scene(root);
			Stage stage = new Stage();

			Image icono = new Image("file:imagenes/coche_alquiler.jpeg");
			stage.getIcons().add(icono);

			stage.setTitle("Ver Alquileres Cliente");

			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();

		} catch (IOException e) {

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
	
	@FXML
	void deleteCliente (ActionEvent event) throws Exception {
		
		Cliente cliente = this.tablaClientes.getSelectionModel().getSelectedItem();

        if (cliente == null) {
        	
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("No se ha seleccionado ningún cliente");
            alert.showAndWait();
            
        } else {
        	
            // Se elimina el cliente
        	
            this.listaClientes.remove(cliente);
            this.listaClientesVisible.remove(cliente);
            
            Controlador.getInstancia().borrar(cliente);
            
            this.tablaClientes.refresh();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Info");
            alert.setContentText("Cliente eliminado");
            alert.showAndWait();
        }
	} 
	
	@FXML
    void seleccionar(MouseEvent event) {
    	
		this.registro = this.tablaClientes.getSelectionModel().getSelectedItem();
    }
	
	@FXML
	void volver(ActionEvent event) {
		
		Stage escenarioActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	escenarioActual.close();
	}
}
