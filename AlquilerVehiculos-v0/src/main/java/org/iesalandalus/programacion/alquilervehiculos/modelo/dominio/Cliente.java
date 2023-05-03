package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

import java.util.Objects;

public class Cliente{
	
	private static final String  ER_NOMBRE=("^([A-Z]{1}[a-z]+[ ]?){1,2}$");
	private static final String  ER_DNI="\\d{8}[A-Za-z]";
	private static final String  ER_TELEFONO="\\d{9}";
	
	private String nombre; 
	private String dni; 
	private String telefono; 
	
	public Cliente(String nombre, String dni, String telefono) {
		
		setNombre(nombre); 
		setDni(dni); 
		setTelefono(telefono); 
	}
	
	public Cliente(Cliente cliente) {
		
		if(cliente==null) {
			
			throw new NullPointerException("ERROR: No es posible copiar un cliente nulo."); 
		}
		
		setNombre(cliente.getNombre()); 
		setDni(cliente.getDni()); 
		setTelefono(cliente.getTelefono()); 
	}

	/* Se crea el método de clase (estático) que se indica en el diagrama, que dado un DNI 
	 * correcto nos devuelva un cliente válido con ese DNI y que será utilizado en las futuras 
	 * búsquedas */
	
	public static Cliente getClienteConDni(String dni) {
		
		Cliente cliente = null; 
		
		if(dni==null) {
			
			throw new NullPointerException("ERROR: El DNI no puede ser nulo."); 
		}
		
		if(!dni.matches(ER_DNI)) {
			
			throw new IllegalArgumentException("ERROR: El DNI no tiene un formato válido."); 
		}
		
		if(!Cliente.comprobarLetraDni(dni)) {
			
			throw new IllegalArgumentException("ERROR: La letra del DNI no es correcta."); 
		
		}else{
			
			cliente = new Cliente("Bob Esponja", dni, "950112233"); 
		}
		
		 return cliente;  
	}

	public String getNombre() {
		
		return nombre;
	}

	public void setNombre(String nombre) {
		
		/* En nuestro caso, la constante de clase ER_NOMBRE la hemos establecido sólo para un 
		 * nombre con 2 palabras, aunque esto se puede cambiar indicándolo sólo en el campo de 
		 * la constante y automáticamente todas sus referencias aplicarían el nuevo valor */
		
		if(nombre==null) {
			
			throw new NullPointerException("ERROR: El nombre no puede ser nulo.");
		}
		if(nombre.isEmpty()) {
			
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
			
		}if(!nombre.matches(ER_NOMBRE)) {
			
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
						
		}else{
			
			this.nombre=nombre;
		}
	}
		
	public String getDni() {
		
		return dni;
	}

	public void setDni(String dni) {
		
		if (dni == null) {
			
			throw new NullPointerException("ERROR: El DNI no puede ser nulo.");
		}
		
		String dni1 = dni;
		dni1.replaceAll("\\W","");
		dni1.toUpperCase();
		
		if (!dni1.matches(ER_DNI)) {
			throw new IllegalArgumentException("ERROR: El DNI no tiene un formato válido.");
		}
		
		if(!comprobarLetraDni(dni1)) {
			
			throw new IllegalArgumentException("ERROR: La letra del DNI no es correcta.");
		}	
		
		this.dni = dni1;
	}
	
	private static boolean comprobarLetraDni(String dni) {
		
		/* En este método, primero filtramos que el DNI pasado como parámetro cumpla el formato
		 * que debe llevar todo DNI y, después, comprobamos que la parte numérica y la letra
		 * introducidos sean válidos: */
		
		String dniConFormato=dni.toUpperCase();
		int numeroDni=Integer.parseInt(dniConFormato.substring(0, 8)); 
		int resto=(numeroDni%23); 
		char letraDni=dniConFormato.charAt(8); 
		
		if(dniConFormato.matches(ER_DNI)
				&& (resto==0) && letraDni=='T') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==1) && letraDni=='R') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==2) && letraDni=='W') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==3) && letraDni=='A') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==4) && letraDni=='G') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==5) && letraDni=='M') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==6) && letraDni=='Y') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==7) && letraDni=='F') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==8) && letraDni=='P') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==9) && letraDni=='D') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==10) && letraDni=='X') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==11) && letraDni=='B') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==12) && letraDni=='N') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==13) && letraDni=='J') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==14) && letraDni=='Z') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==15) && letraDni=='S') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==16) && letraDni=='Q') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==17) && letraDni=='V') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==18) && letraDni=='H') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==19) && letraDni=='L') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==20) && letraDni=='C') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==21) && letraDni=='K') {
			return true; 
		}else if(dniConFormato.matches(ER_DNI)
					&& (resto==22) && letraDni=='E') {
			return true; 
		}else
			
			return false;
	}

	public String getTelefono() {
		
		return telefono;
	}

	public void setTelefono(String telefono) {
		
		if (telefono == null) {
			
			throw new NullPointerException("ERROR: El teléfono no puede ser nulo.");
		
		}else if(!telefono.matches(ER_TELEFONO)) {
			
			throw new IllegalArgumentException("ERROR: El teléfono no tiene un formato válido.");
		}else {
			
			this.telefono = telefono;
		}
	}
	
	/* Dos clientes serán iguales si tienen el mismo DNI, por lo que se implementan los métodos
	 * Hashcode y Equals con este atributo: */
	
	@Override
	public int hashCode() {
		
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(dni, other.dni);
	}

	@Override
	public String toString() {
		return String.format("%s - %s (%s)", getNombre(), getDni(), getTelefono());
	}
}
