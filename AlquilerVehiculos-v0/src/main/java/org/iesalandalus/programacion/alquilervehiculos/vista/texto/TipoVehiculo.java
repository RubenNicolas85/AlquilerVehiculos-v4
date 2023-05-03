package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.*;


public enum TipoVehiculo {
	
	TURISMO("Turismo"), AUTOBUS("Autobús"), FURGONETA("Furgoneta");
	
	private String nombre; 
	
	private TipoVehiculo(String nombre) {
		
		this.nombre=nombre; 		
	}
	
	public static boolean esOrdinalValido(int ordinal) {
		
		return (ordinal >= 0 && ordinal <= Accion.values().length - 1);
	}
	
	public static TipoVehiculo get(int ordinal) {
		
		if (esOrdinalValido(ordinal)) {
			
			return values()[ordinal];
			
		}else 
			
			throw new IllegalArgumentException("Ordinal de la opción no válido");
	}
	
	public static TipoVehiculo get(Vehiculo vehiculo) {
		
		if(vehiculo instanceof Turismo) {
			
			return TipoVehiculo.TURISMO;
			
		}else if(vehiculo instanceof Autobus) {
			
			return TipoVehiculo.AUTOBUS;
			
		}else if(vehiculo instanceof Furgoneta) {
			
			return TipoVehiculo.FURGONETA; 
			
		}else {
			
			return null;
		}
	}

	public String toString() {
		
		return String.format("%d.- %s", ordinal(), nombre);
	}
}