package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;

public class FuenteDatosFicheros implements IFuenteDatos {
	
	// Lógica IClientes
	
	@Override
	public IClientes crearClientes(){
		
		return Clientes.getInstancia();
	}

	// Lógica IVehículos
	
	@Override
	public IVehiculos crearVehiculos() {
		
		return Vehiculos.getInstancia(); 
	}

	// Lógica IAlquileres
	
	@Override
	public IAlquileres crearAlquileres() {
		
		return Alquileres.getInstancia(); 
	}
}
