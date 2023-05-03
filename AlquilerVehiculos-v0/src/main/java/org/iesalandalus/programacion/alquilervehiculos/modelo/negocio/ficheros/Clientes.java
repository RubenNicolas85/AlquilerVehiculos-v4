package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.*;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.utilidades.UtilidadesXml;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Clientes implements IClientes{
	
	private static final String RUTA_FICHERO = "datos/clientes.xml";  
	private static final String RAIZ = "Clientes"; 
	private static final String CLIENTE = "Cliente"; 
	private static final String NOMBRE = "Nombre"; 
	private static final String DNI = "Dni"; 
	private static final String TELEFONO = "Telefono"; 
	private static final String TIPO_DATO = "TipoDato"; 
	
	private static Clientes instancia; 
	private static List<Cliente> coleccionClientes;
	
	private Clientes() {
		
	}
	
	static Clientes getInstancia() {
		
		if(instancia == null) {
			
			instancia = new Clientes(); // Crear la instancia sólo si aún no se ha creado
        }
       
		return instancia;
	}
	
	@Override
	public void comenzar() throws Exception {
		
		coleccionClientes = new ArrayList<>();
		instancia = new Clientes(); 
		
		leerXml(); 
	}

	private void leerXml() throws Exception {
		
	    try {
	    	
	        Document documento = UtilidadesXml.xmlToDom(RUTA_FICHERO);

	        if(documento == null) {
	            throw new NullPointerException("El fichero XML es nulo"); 
	        }

	        NodeList listaNodos=documento.getDocumentElement().getChildNodes();

	        for (int i=0; i<listaNodos.getLength();i++){
	            Node nodo=listaNodos.item(i);

	            if(nodo.getNodeType() == Node.ELEMENT_NODE){
	                Element elemento = (Element) nodo;
	                Cliente cliente = elementToCliente(elemento); 
	                insertar(cliente);
	            }
	        }
	        
	    } catch (Exception e) {
	    	
	        throw new Exception("Error al leer el archivo XML: " + e.getMessage());
	    }
	}
    
	private Cliente elementToCliente(Element elemento) {
		
	    Cliente cliente = null;
	    
	    try {
	    	
	        String dni = elemento.getAttribute(DNI);
	        Element nombre = (Element) elemento.getElementsByTagName(NOMBRE).item(0);
	        Element telefono = (Element) elemento.getElementsByTagName(TELEFONO).item(0);

	        cliente = new Cliente(nombre.getTextContent(), dni, telefono.getTextContent());
	    } catch (Exception e) {
	        System.err.println("Error al parsear el elemento a cliente: " + e.getMessage());
	    }
	    return cliente;
	}

	@Override
	public void terminar() {
		
		escribirXml();
	}

	private void escribirXml() {
		
	    try {
	    	
	        Document documento = UtilidadesXml.crearDomVacio(RAIZ);
	        
	        for (Cliente cliente : coleccionClientes) {
	            clienteToElement(documento, cliente); 
	        }
	        
	        UtilidadesXml.domToXml(documento, RUTA_FICHERO);
	        
	    } catch (Exception e) {
	    	
	        System.err.println("Error al escribir en el fichero XML: " + e.getMessage());
	    }
	}

	private Element clienteToElement(Document dom, Cliente cliente) {
		
		Element elementoCliente = dom.createElement(CLIENTE);
	    elementoCliente.setAttribute(DNI, cliente.getDni());

	    Element elementoNombre = dom.createElement(NOMBRE);
	    elementoNombre.setAttribute(TIPO_DATO, "String");
	    elementoNombre.appendChild(dom.createTextNode(cliente.getNombre()));
	    elementoCliente.appendChild(elementoNombre);

	    Element elementoTelefono = dom.createElement(TELEFONO);
	    elementoTelefono.setAttribute(TIPO_DATO, "String");
	    elementoTelefono.appendChild(dom.createTextNode(cliente.getTelefono()));
	    elementoCliente.appendChild(elementoTelefono);

	    try {
	    	Element raiz = dom.getDocumentElement();
	    	raiz.appendChild(elementoCliente);
	    	
	    } catch (Exception e) {
	    	
	    	System.out.println("Error al añadir el elemento al documento XML: " + e.getMessage());
	    }
	    
	    return elementoCliente;
	}
	
	@Override
	public List<Cliente> get(){
		
		ArrayList<Cliente> copiaClientes = new ArrayList<>(); 
		copiaClientes.addAll(coleccionClientes);
		
		return copiaClientes; 
	}
	
	/* Se crea el método insertar que añadirá un cliente a la lista si éste no es nulo y no existe aún 
	 * en la lista */
	
	@Override
	public void insertar(Cliente cliente) throws Exception{
		
		if(cliente==null) {
			
			throw new NullPointerException("ERROR: No se puede insertar un cliente nulo."); 
			
		}else if(coleccionClientes.contains(cliente)){
				
			throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese DNI."); 
		}
		
		coleccionClientes.add(cliente);
	}
	
	/* se crea el método modificar que permitirá cambiar el nombre o el teléfono (si estos parámetros no 
	 * son nulos) de un cliente existente y si no lanzará la correspondiente excepción */
	
	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws Exception {
		
		Cliente cliente2 = buscar(cliente); 
		
		if(cliente2==null) {
			
			throw new NullPointerException("ERROR: No se puede modificar un cliente nulo.");
		}
		
		if(!coleccionClientes.contains(cliente2)) {
			
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI."); 
		}
		
		if(nombre == null && telefono!=null) {
			
			cliente2.setTelefono(telefono);	
		}
		
		if(telefono==null && nombre!=null) {
			
			System.out.println("El teléfono introducido es nulo, no se puede modificar"); 
			cliente2.setNombre(nombre);
		}
		
		if(nombre!=null && telefono!=null) {
			
			cliente2.setNombre(nombre);
			cliente2.setTelefono(telefono);	
		}
	}
	
	/* Se crea el método buscar que devolverá el cliente si éste se encuentra en la lista y null en caso 
	 * contrario */
	
	@Override
	public Cliente buscar(Cliente cliente) {
		
		if(cliente==null) {
			
			throw new NullPointerException("ERROR: No se puede buscar un cliente nulo."); 
		}
		
		//Se cambia el bucle for:each por un Iterador para recorrer la lista 
		
		Cliente cliente2 = null; 
		
		Iterator <Cliente> iterador=coleccionClientes.iterator(); 
		
		while (iterador.hasNext()) { // Mientras que haya un siguiente elemento, seguiremos en el bucle
			
			cliente2=iterador.next(); // Escogemos el siguiente elemento
			
			if(cliente2.getDni().equals(cliente.getDni())) {
				
				return cliente2; 
			} 
		}
		
		return null; 
	}
	
	/* Se crea el método borrar que borrará el cliente si éste existe en la lista o lanzará una excepción 
	 * en caso contrario */
	
	@Override
	public void borrar(Cliente cliente) throws Exception {
		
		Cliente cliente2 = buscar(cliente); 
		
		if(cliente2==null) {
			
			throw new NullPointerException("ERROR: No se puede borrar un cliente nulo."); 
		}
		
		int indice = coleccionClientes.indexOf(cliente2);
		
		if (indice == -1) {
			
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}else {
			
			coleccionClientes.remove(cliente2);
		}
	}
}
