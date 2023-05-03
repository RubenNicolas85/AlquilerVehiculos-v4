package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

public class Turismo extends Vehiculo {
	
	private static final int FACTOR_CILINDRADA=10; 
	
	private int cilindrada; 
	
	public Turismo() {
		
	}

	public Turismo(String marca, String modelo, int cilindrada, String matricula) {
		
	/* Con el método super llamamos al constructor de la clase padre y le pasamos 
	 * sus parámetros correspondientes, el resto lo hacemos con el método set
	 * del atributo de la clase hija correspondiente */
		
		super(marca, modelo, matricula); 
		setCilindrada(cilindrada); 
		
		this.tipoVehiculo = new Turismo(); 
	}
	
	public Turismo(Turismo turismo) {
		
	/* Con el método super llamamos al constructor de la clase padre y le pasamos 
	 * sus parámetros correspondientes, el resto lo hacemos con el método set
	 * del atributo de la clase hija correspondiente */
		
		super(turismo.getMarca(), turismo.getModelo(), turismo.getMatricula());
		setCilindrada(turismo.getCilindrada()); 
	}
	
	public int getCilindrada() {
		
		return cilindrada;
	}
	
	private void setCilindrada(int cilindrada) {
		
		if(cilindrada<1 || cilindrada>5000) {
			
			throw new IllegalArgumentException("ERROR: La cilindrada no es correcta."); 
		}else {
		
			this.cilindrada = cilindrada;
		}
	}
	
	@Override
	public int getFactorPrecio() {
		
		return getCilindrada()/FACTOR_CILINDRADA;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s (%s CC) - %s",getMarca(), getModelo(), getCilindrada(), getMatricula());
	}
}
