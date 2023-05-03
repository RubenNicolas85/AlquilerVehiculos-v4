package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria;

import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.*;

public class FuenteDatosMemoria implements IFuenteDatos {

	// Lógica IClientes
	
	@Override
	public IClientes crearClientes() {
		
		return new Clientes(); 
	}

	// Lógica IVehículos
	
	@Override
	public IVehiculos crearVehiculos() {
		
		return new Vehiculos(); 
	}

	// Lógica IAlquileres
	
	@Override
	public IAlquileres crearAlquileres() {
		
		return new Alquileres(); 
	}
}