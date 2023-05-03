package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.*;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.utilidades.UtilidadesXml;
import org.iesalandalus.programacion.alquilervehiculos.vista.texto.Consola;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Alquileres implements IAlquileres{
	
	private static final String RUTA_FICHERO = "datos/alquileres.xml";  
	private static final DateTimeFormatter FORMATO_FECHA = Consola.FORMATO_FECHA;
	private static final String RAIZ = "Alquileres"; 
	private static final String ALQUILER = "Alquiler"; 
	private static final String DNI_CLIENTE = "Dni"; 
	private static final String MATRICULA_VEHICULO = "Matricula"; 
	private static final String FECHA_ALQUILER = "FechaAlquiler"; 
	private static final String FECHA_DEVOLUCION = "FechaDevolucion"; 
	private static final String FORMATO = "Formato"; 
	private static final String TIPO_DATO = "TipoDato"; 
	
	private static Alquileres instancia; 
	private static List<Alquiler> coleccionAlquileres; 
	
	private Alquileres() {
		
	}
	
	/** Con el empleo del patrón Singleton se hace una sóla instancia de la 
	 * propia clase de tipo estático, con lo que se consigue establecer una
	 * única referencia para comunicarse con las clases con las que tenga 
	 * relación. Mediante el método getInstancia, si la instancia de la clase
	 * aún no está creada se crea y devuelve la propia instanicia. Para evitar
	 * problemas con el encapsulamiento, este método se establece con visibilidad
	 * de paquete, por lo que el resto de clases del programa que no estén en 
	 * el mismo paquete no podrán interactuar con éste método */
	
	static Alquileres getInstancia() {
		
		if(instancia == null) {
			
			instancia = new Alquileres(); 
        }
       
		return instancia;
	}
	
	/** El método comenzar inicializa los atributos estáticos de la clase y llama
	 * al método privado leerXml, que volcará toda la información del fichero
	 * XML de Alquileres al Arraylist de alquileres */
	
	@Override
	public void comenzar(){
		
		coleccionAlquileres = new ArrayList<>();
		instancia = new Alquileres(); 
		
		try {
			
			leerXml();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
	}
	
	/** El método privado leerXml volcará toda la información del fichero
	 * XML al Arraylist */
	
	private void leerXml() throws Exception {
		
		try {
			
			Document documento = UtilidadesXml.xmlToDom(RUTA_FICHERO);
			
			NodeList listaNodos = documento.getDocumentElement().getChildNodes();

			for (int i=0; i<listaNodos.getLength();i++){
				
				Node nodo=listaNodos.item(i);
				
				if(nodo.getNodeType() == Node.ELEMENT_NODE){
					
					Element elemento = (Element) nodo;
					
					Alquiler alquiler = elementToAlquiler(elemento); 
					
					insertar(alquiler);
				}
			}
			
		}catch(Exception e) {
			
			e.getMessage();
		}
		
	}
	
	/** El método privado elementToAlquileres es el que se encarga de 
	 * transformar los nodos del árbol DOM procedente del fichero XML en objetos
	 * de para posteriormente pasarlos al Arraylist */
	
	private Alquiler elementToAlquiler(Element elemento) throws Exception {
		
		Alquiler alquiler = null; 
		
		try {
			
			if(elemento == null) {
			
				throw new NullPointerException("El fichero XML no puede ser nulo"); 
			}
		
			String dni = elemento.getAttribute(DNI_CLIENTE);
			String matricula = elemento.getAttribute(MATRICULA_VEHICULO); 
			String cadenaAlquiler = elemento.getElementsByTagName(FECHA_ALQUILER).item(0).getTextContent();
			String cadenaDevolucion = elemento.getElementsByTagName(FECHA_DEVOLUCION).item(0).getTextContent();
		
			Cliente clienteInicial = new Cliente("Nombre", dni, "900900900");
			Cliente cliente = Clientes.getInstancia().buscar(clienteInicial);  
		
			Vehiculo vehiculoInicial = new Turismo("Seat", "León", 2000, matricula); 
			Vehiculo vehiculo = Vehiculos.getInstancia().buscar(vehiculoInicial);  
		
			LocalDate fechaAlquiler = null; 
			LocalDate fechaDevolucion = null; 
		
			if(cadenaAlquiler != "") {
			
				fechaAlquiler = LocalDate.parse(cadenaAlquiler, FORMATO_FECHA);
			}
		
			if(cadenaDevolucion != "") {
			
				fechaDevolucion = LocalDate.parse(cadenaDevolucion, FORMATO_FECHA);
			}
	
			alquiler = new Alquiler(cliente, vehiculo, fechaAlquiler);
				
			if(fechaDevolucion != null) {
			
				alquiler.devolver(fechaDevolucion);
			} 
			
		} catch (Exception e) {
			
			throw new Exception("Error al procesar el elemento del alquiler: " + e.getMessage());
		}
		
		return alquiler; 
	}
	
	/** El método terminar llama al método privado escribirXml() */
	
	@Override
	public void terminar() {
		
		escribirXml();
	}
	
	/** El método privado escribirXml crea un árbol DOM procedente del 
	 * ArrayList y lo pasa al fichero XML haciendo uso del método privado
	 * alquilerToElement */
	
	private void escribirXml() {
		
		try {
			Document documento = UtilidadesXml.crearDomVacio(RAIZ);
		    
		    for (Alquiler alquiler : coleccionAlquileres) {
		        
		    	alquilerToElement(documento, alquiler); 
		    }
		    
		    UtilidadesXml.domToXml(documento, RUTA_FICHERO);
		    
		} catch (Exception e) {
			
			System.err.println("Error al escribir el archivo XML: " + e.getMessage());
		}
	}
	
	/** El método privado alquilerToElement recibe como parámetros un árbol DOM
	 *  y un objeto de tipo Alquiler y lo transforma en los nodos para escribir
	 *  el fichero XML*/
	
	private Element alquilerToElement(Document dom, Alquiler alquiler) {
		
		try {
			Element raiz = dom.getDocumentElement();
		    
			String dni = alquiler.getCliente().getDni(); 
			String matricula = alquiler.getVehiculo().getMatricula();
			String cadenaAlquiler = alquiler.getFechaAlquiler().format(FORMATO_FECHA); 
			String cadenaDevolucion = null; 
			
			if(alquiler.getFechaDevolucion() != null) {
				
				cadenaDevolucion = alquiler.getFechaDevolucion().format(FORMATO_FECHA); 
			}
			
			Element elementoAlquiler = dom.createElement(ALQUILER);
			elementoAlquiler.setAttribute(DNI_CLIENTE, dni);
			elementoAlquiler.setAttribute(MATRICULA_VEHICULO, matricula);
	
			Element fechaAlquilerElement = dom.createElement(FECHA_ALQUILER);
			fechaAlquilerElement.setAttribute(FORMATO, Consola.PATRON_FECHA);
			fechaAlquilerElement.setTextContent(cadenaAlquiler );
			elementoAlquiler.appendChild(fechaAlquilerElement);
	
			Element fechaDevolucionElement = dom.createElement(FECHA_DEVOLUCION);
			fechaDevolucionElement.setAttribute(TIPO_DATO, "String");
			fechaDevolucionElement.setTextContent(cadenaDevolucion);
			elementoAlquiler.appendChild(fechaDevolucionElement);
			raiz.appendChild(elementoAlquiler);
			
			return elementoAlquiler;
			
		} catch (Exception e) {
			
			System.err.println("Error al convertir Alquiler a Element: " + e.getMessage());
			return null;
		}
	}
	
	@Override
	public ArrayList<Alquiler> get() {
		
		//Ordenamos por orden de fecha de alquiler, por nombre de cliente y por DNI de cliente:  
		
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
			 * quiere decir que el vehículo sigue alquilado aún: */
			
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
		
		if(alquiler==null) {
			
			throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo."); 
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
}	
	
	
