package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.utilidades;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;

public class UtilidadesXml {

	/** Método estático al que se le pasa como parámetro la ruta de nuestro fichero
	 * XML y nos devulve el árbol DOM correspondiente */
	
	public static Document xmlToDom(String rutaXML) {
		
		Document documento = null; 
		try {
            // 1º Creamos una nueva instancia de un fábrica de constructores de documentos.
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // 2º A partir de la instancia anterior, fabricamos un constructor de documentos, que procesará el XML.
            DocumentBuilder db = dbf.newDocumentBuilder();
            // 3º Procesamos el documento (almacenado en un archivo) y lo convertimos en un árbol DOM.
            
            documento=db.parse(rutaXML);
            
		} catch (Exception ex) {
			
            System.out.println("¡Error! No se ha podido cargar el documento XML.");
		}
		
		return documento;  
	}

	/** Método estático al que se le pasa como parámetro un árbol DOM y la ruta
	 * de nuestro fichero para transformarlo al formato del fichero XML */
	
	public static void domToXml(Document dom, String rutaXml) {

		try {
            // 1º Creamos una instancia de la clase File para acceder al archivo donde guardaremos el XML.
            File f=new File(rutaXml);
            
            //2º Creamos una nueva instancia del transformador a través de la fábrica de transformadores.
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            
            //3º Establecemos algunas opciones de salida, como por ejemplo, la codificación de salida.
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            
            //4º Creamos el StreamResult, que intermediará entre el transformador y el archivo de destino.
            StreamResult result = new StreamResult(f);     
            
            //5º Creamos el DOMSource, que intermediará entre el transformador y el árbol DOM.
            DOMSource source = new DOMSource(dom);
            
            //6º Realizamos la transformación.
            transformer.transform(source, result);   
            
		} catch (TransformerException ex) {
			
			System.out.println("¡Error! No se ha podido llevar a cabo la transformación.");            
		}
	}

	/** Método estático al que se le pasa como parámetro el elemento raíz que
	 * tendrá nuestro fichero XML y nos devuelve un árbol DOM vacío con el 
	 * elemento raíz */
	
	public static Document crearDomVacio(String raiz) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        Document doc = null;
        
        try {
            db = dbf.newDocumentBuilder();
            doc = db.newDocument();
            doc.appendChild(doc.createElement(raiz));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        
        return doc;
    }
}
