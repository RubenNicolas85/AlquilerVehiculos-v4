package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio;

import java.time.LocalDate;
import java.util.List;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public interface IAlquileres {

	void comenzar();

	void terminar(); 
	
	List<Alquiler> get();

	List<Alquiler> get(Cliente cliente);

	List<Alquiler> get(Vehiculo vehiculo);

	void insertar(Alquiler alquiler) throws Exception;

	void devolver(Cliente cliente, LocalDate fechaDevolucion) throws Exception;
	
	void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws Exception;

	Alquiler buscar(Alquiler alquiler);

	void borrar(Alquiler alquiler) throws Exception;
}