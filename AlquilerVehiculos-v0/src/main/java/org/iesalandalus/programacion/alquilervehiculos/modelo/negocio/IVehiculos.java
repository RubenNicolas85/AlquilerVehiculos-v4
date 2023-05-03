package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public interface IVehiculos {

	void comenzar();

	void terminar(); 
	
	List<Vehiculo> get();

	void insertar(Vehiculo vehiculo) throws Exception;

	Vehiculo buscar(Vehiculo vehiculo);

	void borrar(Vehiculo vehiculo) throws Exception;
}