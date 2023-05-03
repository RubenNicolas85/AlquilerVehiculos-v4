package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.*;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;

public class Alquileres implements IAlquileres{
	
	private static List<Alquiler> coleccionAlquileres; 
	
	public Alquileres() {
		
		coleccionAlquileres = new ArrayList<>(); 
	}
	
	@Override
	public ArrayList<Alquiler> get() {
		
		// Ordenamos por orden de fecha de alquiler, por nombre de cliente y por DNI de cliente:  
		
		ArrayList<Alquiler> copiaAlquileres = new ArrayList<>(); 
		copiaAlquileres.addAll(coleccionAlquileres);
		
		return copiaAlquileres; 
	}
	
	/* Se crea el método get para un cliente dado, que devolverá una nueva lista con los alquileres para 
	 * dicho cliente (no debe crear nuevas instancias) */
	
	@Override
	public ArrayList<Alquiler> get(Cliente cliente) {
		
		//Ordenamos por orden de fecha de alquiler, por nombre de cliente y por DNI de cliente:  
		
		ArrayList<Alquiler> alquileresCliente = new ArrayList<>(); 
		
		Iterator <Alquiler> alquilerIterador=coleccionAlquileres.iterator(); 
		
		while (alquilerIterador.hasNext()) { // Mientras que haya un siguiente elemento, seguiremos en el bucle
			
			Alquiler clienteAlquiler=alquilerIterador.next(); // Escogemos el siguiente elemento
			if(clienteAlquiler.getCliente().equals(cliente)){
				
                alquileresCliente.add(clienteAlquiler);
            }
		}
		
		return alquileresCliente;
    }
	
	@Override
	public ArrayList<Alquiler> get(Vehiculo vehiculo) {
		
		//Ordenamos por orden de fecha de alquiler, por nombre de cliente y por DNI de cliente:  
		
		ArrayList<Alquiler> alquileresVehiculo = new ArrayList<>(); 
		
		Iterator <Alquiler> alquilerIterador=coleccionAlquileres.iterator(); 
		
		while (alquilerIterador.hasNext()) { // Mientras que haya un siguiente elemento, seguiremos en el bucle
			
			Alquiler vehiculoAlquiler=alquilerIterador.next(); // Escogemos el siguiente elemento
			
			if(vehiculoAlquiler.getVehiculo().equals(vehiculo)){
				alquileresVehiculo.add(vehiculoAlquiler);
            }
		}
		
		return alquileresVehiculo;
	}
	
	public int getCantidad() {
		
		return coleccionAlquileres.size(); 
	}
	
	@Override
	public void insertar(Alquiler alquiler) throws Exception {
		
		if(alquiler==null) {
			
			throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo."); 
		}

		comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler());
        
		coleccionAlquileres.add(alquiler);
	}
	
	private void comprobarAlquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler) throws Exception {
		
		//Ordenamos por orden de fecha de alquiler, por nombre de cliente y por DNI de cliente:  
		
		Iterator <Alquiler> alquilerIterador=coleccionAlquileres.iterator(); 
		
		while (alquilerIterador.hasNext()) { // Mientras que haya un siguiente elemento, seguiremos en el bucle
			
			Alquiler alquiler2=alquilerIterador.next(); // Escogemos el siguiente elemento
			
			/* Si la fecha de devolución es nula y si el cliente es nuestro cliente, quiere decir que el cliente 
			 * tiene otro alquiler aún sin devolver. Si la fecha de devolución es nula y si el vehículo es el mismo,
			 * quiere decir que el vehículo sigue estando alquilado aún: */
			
			if (alquiler2.getFechaDevolucion() == null) {
				
				if (alquiler2.getCliente().equals(cliente)) {
					
					throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver.");
					
				} else if (alquiler2.getVehiculo().equals(vehiculo)) {
					
					throw new OperationNotSupportedException("ERROR: El vehículo está actualmente alquilado.");
				}
			} else {
			
			/* Si la fecha de alquiler es igual a la fecha de devolución y si tanto el cliente como el vehículo son
			 * los mismos, se lanzará la excepción de que el cliente y el vehículo tienen un alquiler posterior: */
				
				if (fechaAlquiler.isEqual(alquiler2.getFechaDevolucion())) {
					
					if (alquiler2.getCliente().equals(cliente)) {
						
						throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");
						
					} else if (alquiler2.getVehiculo().equals(vehiculo) && !alquiler2.getFechaAlquiler().isAfter(fechaAlquiler)){
						
						throw new OperationNotSupportedException("ERROR: El vehículo tiene un alquiler posterior.");
					}
				}
			}
		}
	}
	
	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws Exception {
		
		Alquiler alquiler = getAlquilerAbierto(cliente); 
		
		comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler()); 
		
		alquiler.devolver(fechaDevolucion);
	}
	
	private Alquiler getAlquilerAbierto(Cliente cliente) {
		
		Vehiculo vehiculo = new Turismo("Seat", "León", 1900, "0000BBB");
		LocalDate fechaAlquiler = LocalDate.of(1990, 1, 1); 
		
		Alquiler alquilerInicial = new Alquiler(cliente, vehiculo, fechaAlquiler); 
		Alquiler alquiler = null; 
		
		if(buscar(alquilerInicial) == null){
			
			throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");
		}

		else 
			
			alquiler = buscar(alquilerInicial); 
		
		return alquiler;
	}

	@Override
	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws Exception {
		
		Alquiler alquiler = getAlquilerAbierto(vehiculo); 
		
		comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler()); 
		
		alquiler.devolver(fechaDevolucion);
	}
	
	private Alquiler getAlquilerAbierto(Vehiculo vehiculo) {
		
		Cliente cliente = new Cliente("Nombre", "53765556C", "900900900"); 
		LocalDate fechaAlquiler = LocalDate.of(1990, 1, 1); 
		
		Alquiler alquilerInicial = new Alquiler(cliente, vehiculo, fechaAlquiler); 
		Alquiler alquiler = null; 
		
		if(buscar(alquilerInicial) == null){
			
			throw new NullPointerException("ERROR: No se puede devolver un alquiler nulo.");
		}

		else 
			
			alquiler = buscar(alquilerInicial); 
		
		return alquiler;
	}
	
	@Override
	public Alquiler buscar(Alquiler alquiler) {
		
		if(alquiler==null) {
			
			throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo."); 
		}
		
		//Ordenamos por orden de fecha de alquiler, por nombre de cliente y por DNI de cliente:  
		
		Alquiler alquiler2 = null; 
		
		Iterator <Alquiler> Iterador=coleccionAlquileres.iterator(); 
		
		while (Iterador.hasNext()) { // Mientras que haya un siguiente elemento, seguiremos en el bucle
			
			alquiler2=Iterador.next(); // Escogemos el siguiente elemento
			
			if(alquiler2.getCliente().getDni().equals(alquiler.getCliente().getDni()) ||
					alquiler2.getVehiculo().getMatricula().equals(alquiler.getVehiculo().getMatricula())) {
				
				 return alquiler2; 
			}
		}
		return null;
	}
	
	@Override
	public void borrar(Alquiler alquiler) throws Exception {
		
		Alquiler alquiler2 = buscar(alquiler); 
		
		if(alquiler2==null) {
			
			throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo."); 
			
		}else if(!coleccionAlquileres.contains(alquiler2)){
			
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual."); 
			
		}else if(coleccionAlquileres.contains(alquiler2)){
		
			coleccionAlquileres.remove(alquiler2);
		}
	}

	@Override
	public void comenzar() {
		
	}

	@Override
	public void terminar() {
		
	}
}	