package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.*;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;

public class Vehiculos implements IVehiculos {
	
	private static List<Vehiculo> coleccionVehiculos; 
	
	public Vehiculos() {
		
		coleccionVehiculos = new ArrayList<>(); 
	}
	
	@Override
	public List<Vehiculo> get() {
		
		ArrayList<Vehiculo> copiaVehiculos = new ArrayList<>(); 
		copiaVehiculos.addAll(coleccionVehiculos);
		
		return copiaVehiculos; 
	}
	
	public int getCantidad() {
		
		return coleccionVehiculos.size(); 
	}
	
	@Override
	public void insertar(Vehiculo vehiculo) throws Exception {
		
		if(vehiculo==null) {
			
			throw new NullPointerException("ERROR: No se puede insertar un vehículo nulo."); 
			
		}else if((coleccionVehiculos.contains(vehiculo))){
			
			throw new OperationNotSupportedException("ERROR: Ya existe un vehículo con esa matrícula."); 
		}
		
		coleccionVehiculos.add(vehiculo);
	}
	
	@Override
	public Vehiculo buscar(Vehiculo vehiculo) {
		
		Vehiculo vehiculo2 = null; 
		
		Iterator <Vehiculo> iterador=coleccionVehiculos.iterator(); 
		
		while (iterador.hasNext()) { 
			
			vehiculo2=iterador.next(); 
			
			if(vehiculo2.getMatricula().equals(vehiculo.getMatricula())) {
				
				return vehiculo2; 
			} 
		}
		
		if(vehiculo==null) {
			
			throw new NullPointerException("ERROR: No se puede buscar un vehículo nulo."); 
		}
		
		return null;
	}
	
	@Override
	public void borrar(Vehiculo vehiculo) throws Exception {
		
		Vehiculo vehiculo2 = buscar(vehiculo); 
		
		if(vehiculo2==null) {
			
			throw new NullPointerException("ERROR: No se puede borrar un vehículo nulo."); 
			
		}else if(!coleccionVehiculos.contains(vehiculo2)) {
			
			throw new OperationNotSupportedException("ERROR: No existe ningún vehículo con esa matrícula."); 
			
		}else if(coleccionVehiculos.contains(vehiculo2)){
			
			coleccionVehiculos.remove(vehiculo2);
		}
	}

	@Override
	public void comenzar() {
		
	}

	@Override
	public void terminar() {
		
	}
}