package org.iesalandalus.programacion.alquilervehiculos.modelo;

import java.time.LocalDate;
import java.util.List;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.*;

public class ModeloCascada extends Modelo {

	/** La clase hija ModeloCascada hereda de la clase padre Modelo. El constructor de la clase
	 * hija no hereda implícitamente el constructor padre, por lo que es necesario llamarlo
	 * explícitamente con el método super(). Al método super le pasamos el parámetro de tipo
	 * factoriaFuenteDatos. El resto de los métodos los hereda de la clase padre y los 
	 * sobreescribe. Los métodos abstractos de la clase padre han sido desarrollados en la
	 * clase hija */
	
	public ModeloCascada(FactoriaFuenteDatos factoriaFuenteDatos){
		
		super(factoriaFuenteDatos); 
	}
	
	public void insertar(Cliente cliente) throws Exception {
		
		clientes.insertar(cliente);
	}
	
	public void insertar(Vehiculo vehiculo) throws Exception{

		vehiculos.insertar(vehiculo);
	}
	
	public void insertar(Alquiler alquiler) throws Exception  {
		
		if(alquiler == null) {
			
			throw new NullPointerException("ERROR: No se puede realizar un alquiler nulo.");
		}
		
		alquileres.insertar(alquiler);
	}
	
	public Cliente buscar(Cliente cliente) {
		
		return clientes.buscar(cliente); 
	}
	
	public Vehiculo buscar(Vehiculo vehiculo) {
		
		return vehiculos.buscar(vehiculo); 
	}

	public Alquiler buscar(Alquiler alquiler) {
	
		return alquileres.buscar(alquiler); 
	}
	
	public void modificar(Cliente cliente, String nombre, String Telefono) throws Exception {
		
		clientes.modificar(cliente, nombre, Telefono);
	}
	
	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws Exception {
		
		alquileres.devolver(cliente, fechaDevolucion); 
	}

	@Override
	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws Exception {
		
		alquileres.devolver(vehiculo, fechaDevolucion); 
	}
	
	public void borrar(Cliente cliente) throws Exception {
		
		clientes.borrar(cliente);
	}
	
	public void borrar(Vehiculo vehiculo) throws Exception {
		
		vehiculos.borrar(vehiculo);
	}
	
	public void borrar(Alquiler alquiler) throws Exception {
		
		alquileres.borrar(alquiler);
	}
	
	@Override
	public List<Cliente> getListaClientes() {
		
		return clientes.get();
	}

	@Override
	public List<Vehiculo> getListaVehiculos() {
		
		return vehiculos.get(); 
	}

	@Override
	public List<Alquiler> getListaAlquileres() {
		
		return alquileres.get(); 
	}

	@Override
	public List<Alquiler> getListaAlquileres(Cliente cliente) {
		
		return alquileres.get(cliente);
	}

	@Override
	public List<Alquiler> getListaAlquileres(Vehiculo vehiculo) {
		
		return alquileres.get(vehiculo);
	}
}
