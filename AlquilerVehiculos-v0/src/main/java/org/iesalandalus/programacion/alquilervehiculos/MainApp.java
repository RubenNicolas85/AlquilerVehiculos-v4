package org.iesalandalus.programacion.alquilervehiculos;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;

public class MainApp {
	
	/** Debido al uso del patrón singleton en el controlador, nos hemos llevado
	 * la factoría abstracta al constructor privado de la clase Controlador */
	
	public static void main(String[] args) {
		
		try {
			
			Controlador.getInstancia().comenzar(); 
			
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}  
	}
}
