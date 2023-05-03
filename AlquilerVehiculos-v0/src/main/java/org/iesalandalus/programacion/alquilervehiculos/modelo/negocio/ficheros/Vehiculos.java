package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.*;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.utilidades.UtilidadesXml;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Vehiculos implements IVehiculos {
	
	private static final String RUTA_FICHERO = "datos/vehiculos.xml";
	private static final String RAIZ = "Vehiculos"; 
	private static final String VEHICULO = "Vehiculo"; 
	private static final String MARCA = "Marca"; 
	private static final String MODELO = "Modelo"; 
	private static final String MATRICULA = "Matricula"; 
	private static final String CILINDRADA ="Cilindrada"; 
	private static final String PLAZAS = "Plazas"; 
	private static final String PMA = "Pma"; 
	private static final String TIPO = "Tipo"; 
	private static final String TURISMO = "Turismo"; 
	private static final String AUTOBUS = "Autobus"; 
	private static final String FURGONETA = "Furgoneta"; 
	private static final String TIPO_DATO = "TipoDato"; 
	
	private static Vehiculos instancia; 
	private static List<Vehiculo> coleccionVehiculos; 
	
	private Vehiculos() {
		
	}
	
	static Vehiculos getInstancia() {
		
		if(instancia == null) {
			
			instancia = new Vehiculos(); // Crear la instancia sólo si aún no se ha creado
        }
       
		return instancia;
	}
	
	@Override
	public void comenzar() {
		
		try {
			
			coleccionVehiculos = new ArrayList<>();
			instancia = new Vehiculos(); 
			
			leerXml();
		}catch(Exception e) {
			
			e.getMessage();
		}
	}
	
	private void leerXml() throws Exception {
		
	    try {
	    	
	        Document documento = UtilidadesXml.xmlToDom(RUTA_FICHERO);
	        if (documento == null) {
	        	
	            throw new NullPointerException("El fichero XML es nulo");
	            
	        } else {
	            NodeList listaNodos = documento.getDocumentElement().getChildNodes();
	            for (int i = 0; i < listaNodos.getLength(); i++) {
	                Node nodo = listaNodos.item(i);
	                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
	                    Element elemento = (Element) nodo;
	                    Vehiculo vehiculo = elementToVehiculo(elemento);
	                    insertar(vehiculo);
	                }
	            }
	        }
	        
	    } catch (Exception e) {
	    	
	        
	        e.printStackTrace();
	        throw e;
	    }
	}
	
	private Vehiculo elementToVehiculo(Element elemento) {
		
		Vehiculo vehiculo = null; 
		
		try {
			
			if(elemento.getAttribute(MATRICULA) == null) {
				
				throw new NullPointerException("La matrícula no puede ser nula"); 
			}
			
			String matricula = elemento.getAttribute(MATRICULA);
			String tipo = elemento.getAttribute(TIPO);
			String marca = elemento.getElementsByTagName(MARCA).item(0).getTextContent();
			String modelo = elemento.getElementsByTagName(MODELO).item(0).getTextContent();

			if (tipo.equals(TURISMO)) {
				
				int cilindrada = Integer.parseInt(elemento.getElementsByTagName(CILINDRADA).item(0).getTextContent());
				
				vehiculo = new Turismo(marca, modelo, cilindrada, matricula); 
						
			return vehiculo; 
				
			} else if (tipo.equals(AUTOBUS)) {
				
				int plazasAutobus = Integer.parseInt(elemento.getElementsByTagName(PLAZAS).item(0).getTextContent());
				
				vehiculo = new Autobus(marca, modelo, plazasAutobus, matricula); 
				
			return vehiculo; 
				
			} else if (tipo.equals(FURGONETA)) {
				int pma = Integer.parseInt(elemento.getElementsByTagName(PMA).item(0).getTextContent());
				int plazasFurgoneta = Integer.parseInt(elemento.getElementsByTagName(PLAZAS).item(0).getTextContent());
				
				vehiculo = new Furgoneta(marca, modelo, pma, plazasFurgoneta, matricula); 
				
			return vehiculo; 
			
			}
		} catch (NullPointerException e) {
			
			System.err.println(e.getMessage());
			
		} catch (NumberFormatException e) {
			
			System.err.println("Se ha producido un error en la conversión de datos. " + e.getMessage());
			
		} catch (Exception e) {
			
			System.err.println("Se ha producido un error inesperado. " + e.getMessage());
		}

		return null;
	}

	@Override
	public void terminar() {
		
		escribirXml();
	}
	
	private void escribirXml() {
		
		try {
			Document documento = UtilidadesXml.crearDomVacio(RAIZ);
		    
		    for (Vehiculo vehiculo : coleccionVehiculos) {
		        
		    	vehiculoToElement(documento, vehiculo); 
		    }
		    
		    UtilidadesXml.domToXml(documento, RUTA_FICHERO);
		    
		} catch (Exception e) {
			
			System.err.println("Se ha producido un error inesperado. " + e.getMessage());
		}
	}
	
	private Element vehiculoToElement(Document dom, Vehiculo vehiculo) {
		
		try {
			Element raiz = dom.getDocumentElement();
	    
			String matricula = vehiculo.getMatricula();
		String tipo = vehiculo.getClass().getSimpleName();
		String marca = vehiculo.getMarca();
		String modelo = vehiculo.getModelo();

		Element elementoVehiculo = dom.createElement(VEHICULO);
		elementoVehiculo.setAttribute(MATRICULA, matricula);
		elementoVehiculo.setAttribute(TIPO, tipo);

		Element marcaElement = dom.createElement(MARCA);
		marcaElement.setAttribute(TIPO_DATO, "String");
		marcaElement.setTextContent(marca);
		elementoVehiculo.appendChild(marcaElement);

		Element modeloElement = dom.createElement(MODELO);
		modeloElement.setAttribute(TIPO_DATO, "String");
		modeloElement.setTextContent(modelo);
		elementoVehiculo.appendChild(modeloElement);

		if (tipo.equals(TURISMO)) {

			Turismo turismo = (Turismo) vehiculo;
			int cilindrada = turismo.getCilindrada();
			Element turismoElement = dom.createElement(TURISMO);
			Element cilindradaElement = dom.createElement(CILINDRADA);
			cilindradaElement.setAttribute(TIPO_DATO, "Integer");
			cilindradaElement.setTextContent(String.valueOf(cilindrada));
			turismoElement.appendChild(cilindradaElement);
			elementoVehiculo.appendChild(turismoElement);
			raiz.appendChild(elementoVehiculo);
			
			return elementoVehiculo;

		} else if (tipo.equals(AUTOBUS)) {

			Autobus autobus = (Autobus) vehiculo;
			int plazas = autobus.getPlazas();
			Element autobusElement = dom.createElement(AUTOBUS);
			Element plazasBusElement = dom.createElement(PLAZAS);
			plazasBusElement.setAttribute(TIPO_DATO, "Integer");
			plazasBusElement.setTextContent(String.valueOf(plazas));
			autobusElement.appendChild(plazasBusElement);
			elementoVehiculo.appendChild(autobusElement);
			raiz.appendChild(elementoVehiculo);
			
			return elementoVehiculo;

		} else if (tipo.equals(FURGONETA)) {

			Furgoneta furgoneta = (Furgoneta) vehiculo;
			int pma = furgoneta.getPma();
			int plazas = furgoneta.getPlazas();
			Element furgonetaElement = dom.createElement(FURGONETA);
			Element pmaElement = dom.createElement(PMA);
			Element plazasFurgoElement = dom.createElement(PLAZAS);
			pmaElement.setAttribute(TIPO_DATO, "Integer");
			pmaElement.setTextContent(String.valueOf(pma));
			plazasFurgoElement.setAttribute(TIPO_DATO, "Integer");
			plazasFurgoElement.setTextContent(String.valueOf(plazas));
			furgonetaElement.appendChild(pmaElement);
			furgonetaElement.appendChild(plazasFurgoElement);
			elementoVehiculo.appendChild(furgonetaElement);
			raiz.appendChild(elementoVehiculo);
			
			return elementoVehiculo;
		}
		}catch(Exception e) {
			
			System.err.println("Se ha producido un error inesperado. " + e.getMessage());
		}
		return null;
	}
	
	@Override
	public List<Vehiculo> get() {
		
		ArrayList<Vehiculo> copiaVehiculos = new ArrayList<>(); 
		copiaVehiculos.addAll(coleccionVehiculos);
		
		
		
		return copiaVehiculos; 
	}
	
	@Override
	public void insertar(Vehiculo vehiculo) throws Exception {
		
		if(vehiculo==null) {
			
			throw new NullPointerException("ERROR: No se puede insertar un vehículo nulo."); 
			
		}else if((coleccionVehiculos.contains(vehiculo))){
			
			throw new OperationNotSupportedException("ERROR: Ya existe un vehículo con esa matrícula."); 
		}
		
		coleccionVehiculos.add(vehiculo);
	}
	
	@Override
	public Vehiculo buscar(Vehiculo vehiculo) {
		
		Vehiculo vehiculo2 = null; 
		
		Iterator <Vehiculo> iterador=coleccionVehiculos.iterator(); 
		
		while (iterador.hasNext()) { 
			
			vehiculo2=iterador.next(); 
			
			if(vehiculo2.getMatricula().equals(vehiculo.getMatricula())) {
				
				return vehiculo2; 
			} 
		}
		
		if(vehiculo==null) {
			
			throw new NullPointerException("ERROR: No se puede buscar un vehículo nulo."); 
		}
		
		return null;
	}
	
	@Override
	public void borrar(Vehiculo vehiculo) throws Exception {
		
		Vehiculo vehiculo2 = buscar(vehiculo); 
		
		if(vehiculo2==null) {
			
			throw new NullPointerException("ERROR: No se puede borrar un vehículo nulo."); 
			
		}else if(!coleccionVehiculos.contains(vehiculo2)) {
			
			throw new OperationNotSupportedException("ERROR: No existe ningún vehículo con esa matrícula."); 
			
		}else if(coleccionVehiculos.contains(vehiculo2)){
			
			coleccionVehiculos.remove(vehiculo2);
		}
	}
}