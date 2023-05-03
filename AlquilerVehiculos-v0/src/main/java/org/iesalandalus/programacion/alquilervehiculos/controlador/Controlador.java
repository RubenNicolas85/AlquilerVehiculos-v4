package org.iesalandalus.programacion.alquilervehiculos.controlador;

import java.time.LocalDate;
import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.Modelo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.ModeloCascada;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.*;

public class Controlador {

	private Modelo modelo;
	private Vista vista;
	
	private static Controlador instancia; 

	/** En este caso (debido al patrón singleton del controlador para que sea compatible con el método 
	 *  el método start de JavaFX para la vista gráfica) hemos establecido el patrón factoría del modelo
	 *  Vista - Controlador (MVC) en el constructor privado del controlador. En nuestro caso, se ha 
	 *  modificado el constructor del modelo, ahora hay que pasarle un parámetro de tipo 
	 *  FactoriaFuenteDatos, que puede ser memoria o ficheros. Simplemente cambiando este 
	 *  dato del enumerado, FICHEROS ó MEMORIA usaremos la lógica del programa de la parte
	 *  del negocio-ficheros o negocio-memoria. Al igual que con el modelo, hemos creado una 
	 *  factoría fuente de Vistas donde podremos elegir entre Vista de texto o vista Gráfica. 
	 *  Al constructor del controlador le pasaremos el objeto de la factoria fuente de vistas de 
	 *  esta forma: FactoriaVistas.GRAFICOS ó .TEXTO.crear()  */
	
	private Controlador() {
		
		FactoriaFuenteDatos fuenteDatos = FactoriaFuenteDatos.FICHEROS; 
		FactoriaVistas vistas = FactoriaVistas.GRAFICOS;
		
		Modelo modeloCascada = new ModeloCascada(fuenteDatos);
		
		this.modelo = modeloCascada; 
		this.vista = vistas.crear();
	}
	
	public static Controlador getInstancia() {
		
		if(instancia == null) {
			
			instancia = new Controlador(); 
        }

		return instancia;
	}

	/**
	 * El método comenzar del controlador, se encarga de invocar al método comenzar
	 * del modelo y de la vista, primero del modelo para crear toda la lógica del
	 * programa y después la vista para poder interactuar con el usuario (nos pedirá
	 * los datos por teclado y mostrará los resultados por pantalla)
	 */

	public void comenzar() throws Exception {

		modelo.comenzar();
		vista.comenzar();
	}

	public void terminar() {

		modelo.terminar();
	}

	public void insertar(Cliente cliente) throws Exception {

		modelo.insertar(cliente);
	}

	public void insertar(Vehiculo vehiculo) throws Exception {

		modelo.insertar(vehiculo);
	}

	public void insertar(Alquiler alquiler) throws Exception {

		modelo.insertar(alquiler);
	}

	public Cliente buscar(Cliente cliente) {

		return modelo.buscar(cliente);
	}

	public Vehiculo buscar(Vehiculo vehiculo) {

		return modelo.buscar(vehiculo);
	}

	public Alquiler buscar(Alquiler alquiler) {

		return modelo.buscar(alquiler);
	}

	public void modificar(Cliente cliente, String nombre, String telefono) throws Exception {

		modelo.modificar(cliente, nombre, telefono);
	}

	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws Exception {

		modelo.devolver(cliente, fechaDevolucion);
	}

	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws Exception {

		modelo.devolver(vehiculo, fechaDevolucion);
	}

	public void borrar(Cliente cliente) throws Exception {

		modelo.borrar(cliente);
	}

	public void borrar(Vehiculo vehiculo) throws Exception {

		modelo.borrar(vehiculo);
	}

	public void borrar(Alquiler alquiler) throws Exception {

		modelo.borrar(alquiler);
	}

	public List<Cliente> getClientes() {

		return modelo.getListaClientes();
	}

	public List<Vehiculo> getVehiculos() {

		return modelo.getListaVehiculos();
	}

	public List<Alquiler> getAlquileres() {

		return modelo.getListaAlquileres();
	}

	public List<Alquiler> getAlquileres(Cliente cliente) {

		return modelo.getListaAlquileres(cliente);
	}

	public List<Alquiler> getAlquileres(Vehiculo vehiculo) {

		return modelo.getListaAlquileres(vehiculo);
	}
}
