package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.iesalandalus.programacion.alquilervehiculos.controlador.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

import javafx.stage.Stage;

public class VistaTexto extends Vista {

	public VistaTexto() {
		
		Accion.setVista(this);
	}
		
	public void comenzar() throws Exception {
		
		boolean salir = false;
		
		do {
			
			try {
				
				Consola.mostrarMenuAcciones();
				System.out.println(); 
				Accion accion = Consola.elegirAccion(); 
				System.out.println(); 
				accion.ejecutar();
				System.out.println(); 
				
			}catch(Exception e) {
				
				System.out.println(e.getMessage()); 
			}
		} while (!salir); 
	}

	public void terminar() {

		Controlador.getInstancia().terminar();
	}

	public void insertarCliente() throws Exception {

		Consola.mostrarCabecera("Ha elegido la opción: " + Accion.INSERTAR_CLIENTE);

		try {
			Cliente cliente = new Cliente(Consola.leerCliente());
			Controlador.getInstancia().insertar(cliente);

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}

	public void insertarVehiculo() throws Exception {

		Consola.mostrarCabecera("Ha elegido la opción: " + Accion.INSERTAR_VEHICULO);

		try {
			
			Controlador.getInstancia().insertar(Consola.leerVehiculo()); 

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}

	public void insertarAlquiler() throws Exception {

		/*
		 * Para insertar un nuevo alquiler se busca un cliente y un vehículo ya creados
		 * previamente. El cliente y el vehículo se buscan por DNI y matrícula
		 * respectivamente:
		 */

		Consola.mostrarCabecera("Ha elegido la opción: " + Accion.INSERTAR_ALQUILER);

		try {

			Cliente cliente = Controlador.getInstancia().buscar(Consola.leerClienteDni());

			Vehiculo vehiculo = Controlador.getInstancia().buscar(Consola.leerVehiculoMatricula());

			Alquiler alquiler = new Alquiler(cliente, vehiculo, Consola.leerFechaAlquiler());
			Controlador.getInstancia().insertar(alquiler);

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}
	
	public void buscarCliente() {

		Consola.mostrarCabecera("Ha elegido la opción: " + Accion.BUSCAR_CLIENTE);

		try {
			System.out.println(Controlador.getInstancia().buscar(Consola.leerClienteDni()));

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}

	public void buscarVehiculo() {

		Consola.mostrarCabecera("Ha elegido la opción: " + Accion.BUSCAR_VEHICULO);

		try {

			System.out.println(Controlador.getInstancia().buscar(Consola.leerVehiculoMatricula()));

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}

	public void buscarAlquiler() {
		
		/* Tanto buscando por DNI de cliente como por matrícula de Vehículo, se crea un alquiler
		 * genérico cuyo único parámetro para comparar con otro a existente o no es el propio DNI
		 * o la matrícula del Vehículo: */
		
		Consola.mostrarCabecera("Ha elegido la opción: " + Accion.BUSCAR_ALQUILER);

		System.out.println("1. Buscar por DNI de cliente :\n2. Buscar por matrícula de vehículo: ");

		int opcion = Entrada.entero();

		switch (opcion) {

		case 1:

			try {

				Cliente cliente = Consola.leerClienteDni();
				Vehiculo vehiculo = new Turismo("Seat", "León", 1900, "0000BBB");
				LocalDate fechaAlquiler = LocalDate.of(1990, 1, 1);

				Alquiler alquiler = new Alquiler(cliente, vehiculo, fechaAlquiler);

				System.out.println(Controlador.getInstancia().buscar(alquiler));

			} catch (Exception e) {

				System.out.println(e.getMessage());
			}

			break;

		case 2:

			try {

				Cliente cliente = new Cliente("Nombre", "16832383V", "900900900");
				Vehiculo vehiculo = Consola.leerVehiculoMatricula();
				LocalDate fechaAlquiler = LocalDate.of(1990, 1, 1);

				Alquiler alquiler = new Alquiler(cliente, vehiculo, fechaAlquiler);

				System.out.println(Controlador.getInstancia().buscar(alquiler));

			} catch (Exception e) {

				System.out.println(e.getMessage());
			}

			break;
		}
	}

	public void modificarCliente() throws Exception {

		Consola.mostrarCabecera("Ha elegido la opción: " + Accion.MODIFICAR_CLIENTE);

		try {

			Controlador.getInstancia().modificar(Consola.leerClienteDni(), Consola.leerNombre(), Consola.leerTelefono());

		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}
	
	public void devolverAlquilerCliente() {
		
		Consola.mostrarCabecera("Ha elegido la opción: " + Accion.DEVOLVER_ALQUILER_CLIENTE);

		try {
			
			Cliente cliente = Consola.leerClienteDni();
			Vehiculo vehiculo = new Turismo("Seat", "León", 1900, "0000BBB");
			LocalDate fechaAlquiler = LocalDate.of(1990, 1, 1);

			Alquiler alquiler = new Alquiler(cliente, vehiculo, fechaAlquiler);
			
			
			if(Controlador.getInstancia().buscar(alquiler) != null) {
				
				Controlador.getInstancia().devolver(cliente, Consola.leerFechaDevolucion());
			}
			
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}
	
	public void devolverAlquilerVehiculo() {
		
		Consola.mostrarCabecera("Ha elegido la opción: " + Accion.DEVOLVER_ALQUILER_VEHICULO);
		
		try {
			
			Cliente cliente = new Cliente("Nombre", "16832383V", "900900900");
			Vehiculo vehiculo = Consola.leerVehiculoMatricula();
			LocalDate fechaAlquiler = LocalDate.of(1990, 1, 1);

			Alquiler alquiler = new Alquiler(cliente, vehiculo, fechaAlquiler);
			
			
			if(Controlador.getInstancia().buscar(alquiler) != null) {
				
				Controlador.getInstancia().devolver(vehiculo, Consola.leerFechaDevolucion());
			}
			
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}

	public void borrarCliente() throws Exception {

		Consola.mostrarCabecera("Ha elegido la opción: " + Accion.BORRAR_CLIENTE);

		try {
			
			List<Alquiler> alquileresCliente = Controlador.getInstancia().getAlquileres(Consola.leerClienteDni());
			
			Iterator<Alquiler> iterador = alquileresCliente.iterator();

			while (iterador.hasNext()) { // Mientras que haya un siguiente elemento, seguiremos en el bucle

				Alquiler alquiler = iterador.next(); // Escogemos el siguiente elemento
				
				Controlador.getInstancia().borrar(alquiler.getVehiculo());
				Controlador.getInstancia().borrar(alquiler.getCliente());
				Controlador.getInstancia().borrar(alquiler);
			}
			
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}

	public void borrarVehiculo() throws Exception {

		Consola.mostrarCabecera("Ha elegido la opción: " + Accion.BORRAR_VEHICULO);

		try {
			
			List<Alquiler> alquileresVehiculo = Controlador.getInstancia().getAlquileres(Consola.leerVehiculoMatricula());
			
			Iterator<Alquiler> iterador = alquileresVehiculo.iterator();

			while (iterador.hasNext()) { // Mientras que haya un siguiente elemento, seguiremos en el bucle

				Alquiler alquiler = iterador.next(); // Escogemos el siguiente elemento
				
				Controlador.getInstancia().borrar(alquiler.getVehiculo());
				Controlador.getInstancia().borrar(alquiler.getCliente());
				Controlador.getInstancia().borrar(alquiler);
			}
			
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}

	public void borrarAlquiler() throws Exception {

		Consola.mostrarCabecera("Ha elegido la opción: " + Accion.BORRAR_ALQUILER);

		System.out.println("1. Buscar por DNI de cliente: \n2. Buscar por matrícula de vehículo: ");

		int opcion = Entrada.entero();

		switch (opcion) {

		case 1:

			try {

				Cliente cliente = Consola.leerClienteDni();
				Vehiculo vehiculo = new Turismo("Seat", "León", 1900, "0000BBB");
				LocalDate fechaAlquiler = LocalDate.of(1990, 1, 1);

				Alquiler alquilerProvisional = new Alquiler(cliente, vehiculo, fechaAlquiler);

				Alquiler alquiler = Controlador.getInstancia().buscar(alquilerProvisional);
					
				Controlador.getInstancia().borrar(alquiler);
				
			} catch (Exception e) {

				System.out.println(e.getMessage());
			}

			break;

		case 2:

			try {

				Cliente cliente = new Cliente("Nombre", "16832383V", "900900900");
				Vehiculo vehiculo = Consola.leerVehiculoMatricula();
				LocalDate fechaAlquiler = LocalDate.of(1990, 1, 1);

				Alquiler alquilerProvisional = new Alquiler(cliente, vehiculo, fechaAlquiler);

				Alquiler alquiler = Controlador.getInstancia().buscar(alquilerProvisional);
					
				Controlador.getInstancia().borrar(alquiler);

			} catch (Exception e) {

				System.out.println(e.getMessage());
			}

			break;
		}
	}

	public void listarClientes() {

		Consola.mostrarCabecera("Ha elegido la opción: " + Accion.LISTAR_CLIENTES);
		
		Collections.sort(Controlador.getInstancia().getClientes(), new Comparator<Cliente>() {
			
			   public int compare(Cliente cliente1, Cliente cliente2) {
				   
			      return cliente1.getNombre().compareTo(cliente2.getNombre());
			   }
		});
		// Se cambia el bucle for:each por un Iterador para recorrer la lista
		
		Iterator<Cliente> iterador = Controlador.getInstancia().getClientes().iterator();
		
		while(iterador.hasNext()){ // Mientras que haya un siguiente elemento, seguiremos en el bucle
			
		    Cliente cliente=iterador.next(); // Escogemos el siguiente elemento
		    System.out.println(cliente.toString());
		}
	}

	public void listarVehiculos() {

		Consola.mostrarCabecera("Ha elegido la opción: " + Accion.LISTAR_VEHICULOS);

		Collections.sort(Controlador.getInstancia().getVehiculos(), new Comparator<Vehiculo>() {
			
			public int compare(Vehiculo vehiculo1, Vehiculo vehiculo2) {
				   
			      return vehiculo1.getMatricula().compareTo(vehiculo2.getMatricula());
			 }
		}); 
		
		Collections.sort(Controlador.getInstancia().getVehiculos(), new Comparator<Vehiculo>() {
			
			public int compare(Vehiculo vehiculo1, Vehiculo vehiculo2) {
				   
			      return vehiculo1.getModelo().compareTo(vehiculo2.getModelo());
			 }
		}); 
		
		Collections.sort(Controlador.getInstancia().getVehiculos(), new Comparator<Vehiculo>() {
			
			public int compare(Vehiculo vehiculo1, Vehiculo vehiculo2) {
				   
			      return vehiculo1.getMarca().compareTo(vehiculo2.getMarca());
			 }
		});  
		
		// Se cambia el bucle for:each por un Iterador para recorrer la lista

		Iterator<Vehiculo> iterador = Controlador.getInstancia().getVehiculos().iterator();

		while (iterador.hasNext()) { // Mientras que haya un siguiente elemento, seguiremos en el bucle

			Vehiculo vehiculo = iterador.next(); // Escogemos el siguiente elemento
			System.out.println(vehiculo.toString());
		}
	}

	public void listarAlquileres() {

		Consola.mostrarCabecera("Ha elegido la opción: " + Accion.LISTAR_ALQUILERES);

		Collections.sort(Controlador.getInstancia().getAlquileres(), new Comparator<Alquiler>() {

			public int compare(Alquiler alquiler1, Alquiler alquiler2) {

				return alquiler1.getCliente().getDni().compareTo(alquiler2.getCliente().getDni());
			}
		}); 
		
		Collections.sort(Controlador.getInstancia().getAlquileres(), new Comparator<Alquiler>() {

			public int compare(Alquiler alquiler1, Alquiler alquiler2) {

				return alquiler1.getCliente().getNombre().compareTo(alquiler2.getCliente().getNombre());
			}
		});

		Collections.sort(Controlador.getInstancia().getAlquileres(), new Comparator<Alquiler>() {

			public int compare(Alquiler alquiler1, Alquiler alquiler2) {

				return alquiler1.getFechaAlquiler().compareTo(alquiler2.getFechaAlquiler());
			}
		});
		
		// Se cambia el bucle for:each por un Iterador para recorrer la lista

		Iterator<Alquiler> iterador = Controlador.getInstancia().getAlquileres().iterator();

		while (iterador.hasNext()) { // Mientras que haya un siguiente elemento, seguiremos en el bucle

			Alquiler alquiler = iterador.next(); // Escogemos el siguiente elemento
			System.out.println(alquiler.toString());
		}
	}

	public void listarAlquileresCliente() {

		Consola.mostrarCabecera("Ha elegido la opción: " + Accion.LISTAR_ALQUILERES_CLIENTE);
		
		Collections.sort(Controlador.getInstancia().getAlquileres(), new Comparator<Alquiler>() {

			public int compare(Alquiler alquiler1, Alquiler alquiler2) {

				return alquiler1.getCliente().getDni().compareTo(alquiler2.getCliente().getDni());
			}
		}); 
		
		Collections.sort(Controlador.getInstancia().getAlquileres(), new Comparator<Alquiler>() {

			public int compare(Alquiler alquiler1, Alquiler alquiler2) {

				return alquiler1.getCliente().getNombre().compareTo(alquiler2.getCliente().getNombre());
			}
		});

		Collections.sort(Controlador.getInstancia().getAlquileres(), new Comparator<Alquiler>() {

			public int compare(Alquiler alquiler1, Alquiler alquiler2) {

				return alquiler1.getFechaAlquiler().compareTo(alquiler2.getFechaAlquiler());
			}
		});
		
		try {
			
			System.out.println(Controlador.getInstancia().getAlquileres(Consola.leerClienteDni()));
			
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}

	public void listarAlquileresVehiculo() {

		Consola.mostrarCabecera("Ha elegido la opción: " + Accion.LISTAR_ALQUILERES_VEHICULO);
		
		Collections.sort(Controlador.getInstancia().getAlquileres(), new Comparator<Alquiler>() {

			public int compare(Alquiler alquiler1, Alquiler alquiler2) {

				return alquiler1.getCliente().getDni().compareTo(alquiler2.getCliente().getDni());
			}
		}); 
		
		Collections.sort(Controlador.getInstancia().getAlquileres(), new Comparator<Alquiler>() {

			public int compare(Alquiler alquiler1, Alquiler alquiler2) {

				return alquiler1.getCliente().getNombre().compareTo(alquiler2.getCliente().getNombre());
			}
		});

		Collections.sort(Controlador.getInstancia().getAlquileres(), new Comparator<Alquiler>() {

			public int compare(Alquiler alquiler1, Alquiler alquiler2) {

				return alquiler1.getFechaAlquiler().compareTo(alquiler2.getFechaAlquiler());
			}
		});
		try {
			
			System.out.println(Controlador.getInstancia().getAlquileres(Consola.leerVehiculoMatricula()));
			
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}
	
	/* Se implementa el método mostrarEstadisticasMensualesTipoVehiculo que utilizará 
	 * el método inicializarEstadisticas para inicializar el mapa, y que mostrará por 
	 * pantalla el número total de veces que ha sido alquilado cada vehículo en el mes 
	 * indicado por el usuario (debe usarse el método leerMes de la clase Consola) */
	
	public void mostrarEstadisticasMensualesTipoVehiculo() {
		
		Consola.mostrarCabecera("Ha elegido la opción: " + Accion.MOSTRAR_ESTADISTICAS_MENSUALES);
		
		// Llamamos al método privado inicializarEstadisticas
		
		    Map<TipoVehiculo, Integer> estadisticas = inicializarEstadisticas();

		// Leemos el mes sobre el cual queremos consultar las estadísticas
		    
		    Month mes = Consola.leerMes();
		    
		/* Declaramos unas variables de tipo entero que serán los contadores 
		 * de cada tipo de vehículo */
		  
		    int contadorTurismos=0; 
		    int contadorAutobuses=0; 
		    int contadorFurgonetas=0; 
		   
		/* Con un bucle for:each establecemos el objeto alquiler, recorremos el
		 * array de alquileres y extraemos los vehículos de los alquileres que
		 * sean instancias de una de las 3 clases y, además, con la condición de
		 * que el mes de la fecha del alquiler sea igual al mes que el usuario
		 * ha introducido por Consola  */
		    
		    for(Alquiler alquiler: Controlador.getInstancia().getAlquileres()) {
		    	
		    	if(alquiler.getVehiculo() instanceof Turismo && alquiler.getFechaAlquiler().getMonth().equals(mes)) {
		    		
		    		contadorTurismos++; 
		    		estadisticas.put(TipoVehiculo.TURISMO, contadorTurismos);
		    				    	 
		    	}else if(alquiler.getVehiculo() instanceof Autobus && alquiler.getFechaAlquiler().getMonth().equals(mes)) {
		    		
		    		contadorAutobuses++; 
		    		estadisticas.put(TipoVehiculo.AUTOBUS, contadorAutobuses);
		    				    		
		    	}else if(alquiler.getVehiculo() instanceof Furgoneta && alquiler.getFechaAlquiler().getMonth().equals(mes)) {
		    		
		    		contadorFurgonetas++; 
		    		estadisticas.put(TipoVehiculo.FURGONETA, contadorFurgonetas);
		    	}
		    }
		    
		    System.out.println("Las estadísticas de alquileres por tipo de vehículo durante "
		    		+ "el mes de "+ mes.getDisplayName(TextStyle.FULL, Locale.getDefault())+" es "
		    				+ "de: " + "\n" + "\n" +estadisticas);  
	}
	
	/* Se implementa el método inicializarEstadisticas que devolverá un Mapa de tipo 
	 * EnumMap, cuya clave será el tipo de vehículo y que tendrá inicialmente un valor 
	 * 0 para todos los tipos de vehículos */
	
	private Map<TipoVehiculo, Integer> inicializarEstadisticas() {
		
		Map<TipoVehiculo, Integer> mapaVehiculos = new EnumMap<>(TipoVehiculo.class);
		
		// Inicializar todas las claves con el valor 0
		
		mapaVehiculos.put(TipoVehiculo.AUTOBUS, 0);
		mapaVehiculos.put(TipoVehiculo.FURGONETA, 0);
		mapaVehiculos.put(TipoVehiculo.TURISMO, 0);
		
		// Devolver el EnumMap inicializado
		
		return mapaVehiculos; 
	}

	@Override
	public void start(Stage arg0) throws Exception {
		
	}
}

