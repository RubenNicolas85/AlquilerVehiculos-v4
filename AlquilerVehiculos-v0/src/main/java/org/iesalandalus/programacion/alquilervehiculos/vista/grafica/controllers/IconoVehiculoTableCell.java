package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controllers;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconoVehiculoTableCell extends TableCell<Vehiculo, Vehiculo>{
	
	@Override
	protected void updateItem(Vehiculo vehiculo, boolean empty) {
		
        super.updateItem(vehiculo, empty);
        
        if (vehiculo == null || empty) {
            setText(null);
            setGraphic(null);
            
	    } else {
	    	ImageView icono = new ImageView();
	        
	        if (vehiculo instanceof Turismo) {
	        	
	        	icono.setImage(new Image("file:imagenes/coche.jpg"));
	        	
	        } else if (vehiculo instanceof Furgoneta) {
	        	
	        	icono.setImage(new Image("file:imagenes/furgoneta.jpg"));
	        	
	        } else if (vehiculo instanceof Autobus) {
	        	
	        	icono.setImage(new Image("file:imagenes/autobus.jpg"));
	        }
	        icono.setFitHeight(30);
	        icono.setPreserveRatio(true);
	        setGraphic(icono);
	    }
	}
}



