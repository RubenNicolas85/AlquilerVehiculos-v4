package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

import java.util.Objects;

public abstract class Vehiculo {

	private static final String ER_MARCA="Seat|Land Rover|KIA|Rolls-Royce|SsangYong|Ford|Renault|"
			+ "Opel|Hyundai|Toyota|Volkswagen|Citroën|BMW|Mercedes-Benz|Fiat|Irizar|Iveco|MAN|Volvo";
	protected static final String ER_MATRICULA="^[0-9]{4}[B-Z]{3}$";
	
	private String marca; 
	private String modelo; 
	protected String matricula;
	protected Vehiculo tipoVehiculo; 
	
	/* La clase abstracta Vehículo contiene los atributos y métodos comunes
	 * que heredarán las 3 clases hijas: Turismo, Autobus, Furgoneta */
	
	protected Vehiculo() {
		
	}
	
	protected Vehiculo(String marca, String modelo, String matricula) {
		
		setMarca(marca); 
		setModelo(modelo); 
		setMatricula(matricula); 
	}
	
	protected Vehiculo(Vehiculo vehiculo) {
		
		setMarca(vehiculo.getMarca()); 
		setModelo(vehiculo.getModelo()); 
		setMatricula(vehiculo.getMatricula()); 
	}
	
	/* En el método estático copiar pasamos como parámetro un objeto de tipo
	 * vehículo y nos tiene que devolver una copia de ese vehículo generada 
	 * mediante constructor copia. Como la clase Vehículo es abstracta y no 
	 * se puede instanciar, al parámetro vehículo se aplica el operador
	 * instanceof para saber de cuál de las 3 clases hijas es y, a continuación, 
	 * se hace un casting al parámetro vehículo para poder obtener todos los 
	 * parámetros para construir un nuevo objeto Turismo, Autobus o Furgoneta */
	
	public static Vehiculo copiar (Vehiculo vehiculo) {
		
		if(vehiculo instanceof Turismo) {
			
			Turismo turismo = (Turismo) vehiculo; 
			
			return new Turismo(turismo.getMarca(), turismo.getModelo(), turismo.getCilindrada() ,turismo.getMatricula()); 
		}
		
		if(vehiculo instanceof Autobus) {
			
			Autobus autobus = (Autobus) vehiculo; 
			
			return new Autobus(autobus.getMarca(), autobus.getModelo(), autobus.getPlazas() , autobus.getMatricula()); 
		}
		
		if(vehiculo instanceof Furgoneta) {
			
			Furgoneta furgoneta = (Furgoneta) vehiculo; 
			
			return new Furgoneta(furgoneta.getMarca(), furgoneta.getModelo(), furgoneta.getPma(), furgoneta.getPlazas(), furgoneta.getMatricula()); 
		
		}else
			
			return null; 
	}
	
	public static Vehiculo getVehiculoConMatricula(String matricula) {
		
		if(matricula==null) {
			
			throw new NullPointerException("ERROR: La matrícula no puede ser nula."); 
			
		}
		
		if(!matricula.matches(ER_MATRICULA)) {
			
			throw new IllegalArgumentException("ERROR: La matrícula no tiene un formato válido."); 
			
		}
		
		if(matricula.matches(ER_MATRICULA)) {
			
		/** En este caso indicamos que devuelva un Turismo, por ejemplo, el tipo de vehículo es 
		 * indiferente, sólo se requiere un nuevo vehículo para poder realizar comparaciones con 
		 * la matrícula para saber si se trata o no del mismo vehículo en las búsquedas */
			
			return new Turismo("Seat", "León", 2000, matricula); 
			
		}else
		
		return null; 
	}
	
	/* Se define el método abstracto getFactorPrecio, que será implementado por
	 * cada una de las clases hijas. En cada clase el factor precio devuelto 
	 * será distinto, pero gracias al polimorfismo de la herencia un objeto de 
	 * tipo vehículo dispondrá del mismo método pero con distinto valor, 
	 * dependiendo de la clase hija a la que pertenezca */
	
	public abstract int getFactorPrecio(); 
	
	public String getMarca() {
		
		return marca;
	}
	
	private void setMarca(String marca) {
		
		if(marca==null) {
			
			throw new NullPointerException("ERROR: La marca no puede ser nula."); 
			
		}else if(!marca.matches(ER_MARCA)) {
			
			throw new IllegalArgumentException("ERROR: La marca no tiene un formato válido."); 
		}
		
		else if(marca.matches(ER_MARCA)) {
			
			this.marca = marca;
		}
	}
	
	public String getModelo() {
		
		return modelo;
	}
	
	private void setModelo(String modelo) {
		
		if(modelo==null) {
			
			throw new NullPointerException("ERROR: El modelo no puede ser nulo.");
			
		}else if(modelo.trim().isEmpty()) {
			
			throw new IllegalArgumentException("ERROR: El modelo no puede estar en blanco."); 
			
		}else{
			
			this.modelo = modelo;
		}
	}
	
	public String getMatricula() {
		
		return matricula;
	}

	private void setMatricula(String matricula) {
		
		if(matricula==null) {
			
			throw new NullPointerException("ERROR: La matrícula no puede ser nula."); 
			
		}else if(!matricula.matches(ER_MATRICULA)) {
			
			throw new IllegalArgumentException("ERROR: La matrícula no tiene un formato válido."); 
		}else if(matricula.matches(ER_MATRICULA)) {
			
			this.matricula = matricula;
		}
	}
	
	public Vehiculo getTipoVehiculo() { // Método getTipoVehiculo
		
        return tipoVehiculo;
    }
	
	@Override
	public int hashCode() {
		
		return Objects.hash(matricula);
	}

	/* Implementamos llamada al operador instance of en el método equals, según 
	 * el enunciado. El atributo para diferenciar a un vehículo de otro es el 
	 * único que no se puede repetir, es decir, la matrícula de cada vehículo */
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (!(obj instanceof Vehiculo)) {
	        return false;
	    }
	    Vehiculo other = (Vehiculo) obj;
	    return Objects.equals(matricula, other.matricula);
	}
}
