package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

public class Autobus extends Vehiculo{

	private static final int FACTOR_PLAZAS=2; 
	
	private int plazas;
	
	public Autobus() {
		
	}
	public Autobus(String marca, String modelo, int plazas, String matricula) {
		
	/* Con el método super llamamos al constructor de la clase padre y le pasamos 
	 * sus parámetros correspondientes, el resto lo hacemos con el método set
	 * del atributo de la clase hija correspondiente */
		
		super(marca, modelo, matricula);
		setPlazas(plazas); 
		
		this.tipoVehiculo = new Autobus(); 
	}

	public Autobus(Autobus autobus) {
		
	/* Con el método super llamamos al constructor de la clase padre y le pasamos 
	 * sus parámetros correspondientes, el resto lo hacemos con el método set
	 * del atributo de la clase hija correspondiente */
		
		super(autobus.getMarca(), autobus.getModelo(), autobus.getMatricula());
		setPlazas(autobus.getPlazas()); 
	}

	public int getPlazas() {
		return plazas;
	}

	private void setPlazas(int plazas) {
		
		if(plazas<1 || plazas>50) {
			
			throw new IllegalArgumentException("ERROR: El número de plazas no es correcto."); 
		}else {
		
			this.plazas = plazas;
		}
	} 
	
	@Override
	public int getFactorPrecio() {
		
		return getPlazas()*FACTOR_PLAZAS;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s - (%s Plazas) - %s", getMarca(), getModelo(), getPlazas(), getMatricula());
	}
}
