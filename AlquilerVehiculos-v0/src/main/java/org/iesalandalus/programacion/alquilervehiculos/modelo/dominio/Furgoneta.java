package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

public class Furgoneta extends Vehiculo {

	private static final int FACTOR_PMA=100; 
	private static final int FACTOR_PLAZAS=1; 
	
	private int pma; 
	private int plazas;
	
	public Furgoneta() {
		
	}

	public Furgoneta(String marca, String modelo, int pma, int plazas, String matricula) {
		
	/* Con el método super llamamos al constructor de la clase padre y le pasamos 
	 * sus parámetros correspondientes, el resto lo hacemos con el método set
	 * del atributo de la clase hija correspondiente */
		
		super(marca, modelo, matricula);
		setPma(pma); 
		setPlazas(plazas); 
		
		this.tipoVehiculo = new Furgoneta(); 
	}
	
	public Furgoneta(Furgoneta furgoneta) {
		
	/* Con el método super llamamos al constructor de la clase padre y le pasamos 
	 * sus parámetros correspondientes, el resto lo hacemos con el método set
	 * del atributo de la clase hija correspondiente */
		
		super(furgoneta.getMarca(), furgoneta.getModelo(), furgoneta.getMatricula());
		setPma(furgoneta.getPma()); 
		setPlazas(furgoneta.getPlazas()); 
	}
	
	public int getPma() {
		return pma;
	}
	
	private void setPma(int pma) {
		
		if(pma<1 || pma>1500) {
			
			throw new IllegalArgumentException("ERROR: El PMA debe estar entre 1 y 1500 Kg."); 
		}else {
		
			this.pma = pma;
		}
	}
	
	public int getPlazas() {
		return plazas;
	}
	
	private void setPlazas(int plazas) {
		
		if(plazas<1 || plazas>10) {
			
			throw new IllegalArgumentException("ERROR: El número de plazas no es correcto."); 
		}else {
		
			this.plazas = plazas;
		}
	}
	
	@Override
	public int getFactorPrecio() {
		
		int factorPrecio=(getPma() / FACTOR_PMA) + (getPlazas() * FACTOR_PLAZAS); 
		
		return factorPrecio;  
	}
	
	@Override
	public String toString() {
		return String.format("%s %s - (%s Kg. PMA) - (%s Plazas) - %s", getMarca(), getModelo(), getPma(), getPlazas(), getMatricula()); 
	}
}
