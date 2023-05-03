package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {
	
	public static final String PATRON_FECHA="dd/MM/yyyy"; 
	public static final String PATRON_MES="MM"; 
	public static final DateTimeFormatter FORMATO_FECHA=DateTimeFormatter.ofPattern(PATRON_FECHA);
	
	private Consola() {
		
	}
	
	/* Para que este método funcione definimos el String str como un guión, después, definimos
	 * otro String repeated, que será igual a str repetido el número de veces del tamaño del 
	 * mensaje introducido como parámetro. Por último, se imprime el mensaje concatenado con
	 * salto de línea y concatenado con el String repeated */
	
	public static void mostrarCabecera(String mensaje) {
		
		String str = "-"; 
		String repeated = str.repeat(mensaje.length()); 
		
		System.out.println(mensaje + "\n" + repeated); 
	}
	
	public static void mostrarMenuAcciones() {
		
		System.out.println(); 
		mostrarCabecera("MENÚ PRINCIPAL - APLICACIÓN DE ALQUILER DE VEHÍCULOS"); 
		
		for (Accion accion: Accion.values()) {
			System.out.println(accion);
		} 
	}
	
	/* Se crea el método elegirAccion que leerá un entero (utilizando el método anteriormente 
	 * creado) asociado a la opción y devolverá la opción correspondiente. Si el entero 
	 * introducido no se corresponde con ninguna opción deberá volver a leerlo hasta que éste 
	 * sea válido */
	
	public static Accion elegirAccion() {
		
		int ordinalOpcion = 0;
		boolean error=false; 
		
		do {
			
			try {
			
				error=false; 
				ordinalOpcion=leerEntero("Elige una opción: "); 
				
				Accion.esOrdinalValido(ordinalOpcion);
				
			} catch (Exception e) {
				
				System.out.println(e.getMessage());
				error = true;
			}
			
		} while (error);
		
		return Accion.get(ordinalOpcion);
	}
	
	private static String leerCadena(String mensaje) {
		
		System.out.println(mensaje); 
		String cadena=Entrada.cadena(); 
		
		return cadena; 
	}
	
	private static Integer leerEntero(String mensaje) {
		
		System.out.println(mensaje); 
		int entero=Entrada.entero(); 
		
		return entero; 
	}
	
	private static LocalDate leerFecha(String mensaje) {
		
		int dia=0; 
		int mes=0; 
		int year=0; 
		
		LocalDate fecha=null; 
		
		do {
			
			fecha=null; 
			System.out.println(mensaje);
			
			dia=leerEntero("Por favor, indique el día: "); 
			mes=leerEntero("Por favor, indique el mes: ");
			year=leerEntero("Por favor, indique el año: ");
			
			fecha = LocalDate.of(year, mes, dia);
			
		}while(fecha==null); 
		
		return fecha;
	}
	
	public static Cliente leerCliente() {
		
		Cliente cliente = new Cliente(leerNombre(), 
				leerCadena("Introduzca el DNI del cliente"),
				leerTelefono()); 
		
		return cliente;
	}
	
	public static Cliente leerClienteDni() {
		
		String dni = leerCadena("Introduzca el DNI del cliente");
		
		Cliente cliente = new Cliente("Cliente", 
				dni,"900900900"); 
		
		return cliente; 
	}
	
	public static String leerNombre() {
		
		return leerCadena("Introduzca el nombre del cliente"); 
	}
	
	public static String leerTelefono() {
		
		return leerCadena("Introduzca el teléfono del cliente"); 
	}
	
	public static Vehiculo leerVehiculo() {
		
		mostrarMenuTiposVehiculos(); 
		
		TipoVehiculo tipoVehiculo = elegirTipoVehiculo(); 
		
		return leerVehiculo(tipoVehiculo); 
	}
	
	private static void mostrarMenuTiposVehiculos() {
		
		System.out.println(); 
		mostrarCabecera("TIPOS DE VEHÍCULOS"); 
		
		for (TipoVehiculo tipoVehiculo: TipoVehiculo.values()) {
			System.out.println(tipoVehiculo);
		} 
	}
	
	private static TipoVehiculo elegirTipoVehiculo() {
		
		int ordinalOpcion = 0;
		
		try {
				ordinalOpcion=leerEntero("\nElige una opción: "); 
				
				TipoVehiculo.esOrdinalValido(ordinalOpcion);
				
			} catch (Exception e) {
				
				System.out.println(e.getMessage());
			}
		
		return TipoVehiculo.get(ordinalOpcion);
	}
	
	private static Vehiculo leerVehiculo(TipoVehiculo tipoVehiculo) {
		
		if(tipoVehiculo.equals(TipoVehiculo.TURISMO)){
			
			Turismo turismo = new Turismo(leerCadena("Introduzca la marca del turismo: "
					+ "Seat|Land Rover|KIA|Rolls-Royce|SsangYong|Ford|Renault|Opel|Hyundai|Toyota|Volkswagen|Citroën|BMW"), 
					leerCadena("Introduzca el modelo del turismo: "),
					leerEntero("Introduzca la cilindrada del turismo (Entre 0 y 5000): "),
					leerCadena("Introduzca la matrícula del turismo en formato: 1111BBB")); 
			
			return turismo; 
		}
		
		if(tipoVehiculo.equals(TipoVehiculo.AUTOBUS)){
			
			Autobus autobus = new Autobus(leerCadena("Introduzca la marca del autobús: "
					+ "Mercedes-Benz|Irizar|Iveco|MAN|Volvo|Fiat"), 
					leerCadena("Introduzca el modelo del autobús: "),
					leerEntero("Introduzca el número de plazas del autobús (Entre 1 y 50): "),
					leerCadena("Introduzca la matrícula del autobús en formato: 1111BBB")); 
			
			return autobus; 
		}
		
		if(tipoVehiculo.equals(TipoVehiculo.FURGONETA)){
			
			Furgoneta furgoneta = new Furgoneta(leerCadena("Introduzca la marca de la furgoneta: "
					+ "Volkswagen|Mercedes-Benz|Renault|Citroën|Fiat|Ford"), 
					leerCadena("Introduzca el modelo de la furgoneta: "),
					leerEntero("Introduzca el PMA de la furgoneta (Entre 1 y 1500 Kg): "),
					leerEntero("Introduzca el número de plazas de la furgoneta (Entre 1 y 10): "),
					leerCadena("Introduzca la matrícula de la furgoneta en formato: 1111BBB")); 
			
			return furgoneta; 
		}
		return null;
	}
	
	public static Vehiculo leerVehiculoMatricula() {
		
		String matricula = leerCadena("Introduzca la matrícula del vehículo en formato: 1111BBB");
		
		Vehiculo vehiculo = new Turismo("Seat", "León", 1500, matricula); 
		
		return vehiculo; 
	}
	
	public static Alquiler leerAlquiler() {
		
		Alquiler alquiler = new Alquiler(leerClienteDni(), leerVehiculoMatricula(), 
				leerFecha("Por favor, introduzca la fecha del alquiler: Día / Mes / Año: ")); 
		
		return alquiler;
	}
	
	public static LocalDate leerFechaAlquiler() {
		
		LocalDate fechaAlquiler = leerFecha("Por favor, introduzca la fecha de alquiler del turismo: Día / Mes / Año: "); 
	
		return fechaAlquiler; 
	}

	public static LocalDate leerFechaDevolucion() {
		
		LocalDate fechaDevolucion = leerFecha("Por favor, introduzca la fecha de devolución del turismo: Día / Mes / Año: "); 
	
		return fechaDevolucion; 
	}
	
	public static Month leerMes() {
		
		int numero;
		
		do {
			
			numero = leerEntero("Por favor, introduzca el mes (1-12): "); 
			
		}while(numero<1 || numero>12); 
		
		Month mes = Month.of(numero);
		
		return mes; 
	}
}
