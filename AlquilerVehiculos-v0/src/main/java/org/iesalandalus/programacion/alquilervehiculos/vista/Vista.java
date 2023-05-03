package org.iesalandalus.programacion.alquilervehiculos.vista;

import javafx.application.Application;

public abstract class Vista extends Application {

	public abstract void comenzar() throws Exception; 
	
	public abstract void terminar(); 
}
