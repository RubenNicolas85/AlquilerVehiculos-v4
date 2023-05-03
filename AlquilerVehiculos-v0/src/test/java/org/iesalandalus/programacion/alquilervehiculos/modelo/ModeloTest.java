package org.iesalandalus.programacion.alquilervehiculos.modelo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria.Alquileres;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria.Clientes;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria.Vehiculos;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ModeloTest {
	
	private static final String MENSAJE_ERROR_INSERTAR_ALQUILER_NULO = "ERROR: No se puede realizar un alquiler nulo.";
	private static final String MENSAJE_ERROR_INSERTAR_ALQUILER_CLIENTE_NO_EXISTE = "ERROR: No existe el cliente del alquiler.";
	private static final String MENSAJE_ERROR_INSERTAR_ALQUILER_TURISMO_NO_EXISTE = "ERROR: No existe el turismo del alquiler.";
	private static final String MENSAJE_ERROR_DEVOLVER_ALQUILER_NO_EXISTE = "ERROR: No existe el alquiler a devolver.";

	@InjectMocks
	private ModeloCascada modeloCascada;

	@Mock
	private static Clientes clientes;
	@Mock
	private static Vehiculos vehiculos;
	@Mock
	private static Alquileres alquileres;

	private static Cliente cliente;
	private static Turismo turismo;
	private static Alquiler alquiler;

	@BeforeAll
	public static void setup() {
		cliente = mock();
		mockConstruction(Clientes.class);
		mockConstruction(Cliente.class);
		when(cliente.getNombre()).thenReturn("Bob Esponja");
		when(cliente.getDni()).thenReturn("11223344B");
		when(cliente.getTelefono()).thenReturn("950112233");
		turismo = mock();
		mockConstruction(Vehiculos.class);
		mockConstruction(Turismo.class);
		when(turismo.getMarca()).thenReturn("Seat");
		when(turismo.getModelo()).thenReturn("León");
		when(turismo.getMatricula()).thenReturn("1234BCD");
		when(turismo.getCilindrada()).thenReturn(90);
		LocalDate hoy = LocalDate.now();
		LocalDate ayer = hoy.minusDays(1);
		alquiler = mock();
		mockConstruction(Alquileres.class);
		mockConstruction(Alquiler.class);
		when(alquiler.getCliente()).thenReturn(cliente);
		when(alquiler.getVehiculo()).thenReturn(turismo);
		when(alquiler.getFechaAlquiler()).thenReturn(ayer);
	}

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void terminarNoHaceNada() {
		assertDoesNotThrow(() -> modeloCascada.terminar());
	}

	@Test
	void insertarClienteLlamaClientesInsertar() {
		assertDoesNotThrow(() -> modeloCascada.insertar(cliente));
		assertDoesNotThrow(() -> verify(clientes).insertar(any(Cliente.class)));
		assertNotSame(cliente, modeloCascada.buscar(cliente));
	}

	@Test
	void insertarTurismoLlamaTurismosInsertar() {
		assertDoesNotThrow(() -> modeloCascada.insertar(turismo));
		assertDoesNotThrow(() -> verify(vehiculos).insertar(any(Turismo.class)));
		assertNotSame(turismo, modeloCascada.buscar(turismo));
	}

	@Test
	void insertarAlquilerLlamaClientesBuscarTurismosBuscarAlquileresInsertar() {
		InOrder orden = inOrder(clientes, vehiculos, alquileres);
		when(clientes.buscar(cliente)).thenReturn(cliente);
		when(vehiculos.buscar(turismo)).thenReturn(turismo);
		assertDoesNotThrow(() -> modeloCascada.insertar(alquiler));
		orden.verify(clientes).buscar(cliente);
		orden.verify(vehiculos).buscar(turismo);
		assertDoesNotThrow(() -> orden.verify(alquileres).insertar(any(Alquiler.class)));
		assertNotSame(alquiler, modeloCascada.buscar(alquiler));
	}
	
	@Test
	void insertarAlquilerAlquilerNoValidoLanzaExcepcion() {
		Alquiler alquilerNulo = null;
		NullPointerException npe = assertThrows(NullPointerException.class, () -> modeloCascada.insertar(alquilerNulo));
		assertEquals(npe.getMessage(), MENSAJE_ERROR_INSERTAR_ALQUILER_NULO);
		OperationNotSupportedException onse = assertThrows(OperationNotSupportedException.class, () -> modeloCascada.insertar(alquiler));
		assertEquals(onse.getMessage(), MENSAJE_ERROR_INSERTAR_ALQUILER_CLIENTE_NO_EXISTE);
		when(clientes.buscar(cliente)).thenReturn(cliente);
		onse = assertThrows(OperationNotSupportedException.class, () -> modeloCascada.insertar(alquiler));
		assertEquals(onse.getMessage(), MENSAJE_ERROR_INSERTAR_ALQUILER_TURISMO_NO_EXISTE);
	}

	@Test
	void buscarClienteLlamaClientesBuscar() {
		assertDoesNotThrow(() -> modeloCascada.insertar(cliente));
		Cliente clienteBuscado = modeloCascada.buscar(cliente);
		verify(clientes).buscar(cliente);
		assertNotSame(cliente, clienteBuscado);
	}

	@Test
	void buscarTurismoLlamaTurismosBuscar() {
		assertDoesNotThrow(() -> modeloCascada.insertar(turismo));
		Vehiculo turismoBuscado = modeloCascada.buscar(turismo);
		verify(vehiculos).buscar(turismo);
		assertNotSame(turismo, turismoBuscado);
	}

	@Test
	void buscarAlquilerLlamaAlquileresBuscar() {
		assertDoesNotThrow(() -> modeloCascada.insertar(cliente));
		when(clientes.buscar(cliente)).thenReturn(cliente);
		when(vehiculos.buscar(turismo)).thenReturn(turismo);
		Alquiler alquilerBuscado = modeloCascada.buscar(alquiler);
		verify(alquileres).buscar(alquiler);
		assertNotSame(alquiler, alquilerBuscado);
	}

	@Test
	void modificarClienteLlamaClientesBuscarModificar() {
		assertDoesNotThrow(() -> modeloCascada.modificar(cliente, "Patricio Estrella", "950123456"));
		assertDoesNotThrow(() -> verify(clientes).modificar(cliente, "Patricio Estrella", "950123456"));
	}
	
	@Test
	void devolverAlquilerLlamaAlquileresBuscarAlquilerDevolver() {
		when(alquileres.buscar(alquiler)).thenReturn(alquiler);
		LocalDate hoy = LocalDate.now();
		assertDoesNotThrow(() -> modeloCascada.devolver(cliente, hoy));
		InOrder orden = inOrder(alquileres, alquiler);
		orden.verify(alquileres).buscar(alquiler);
		assertDoesNotThrow(() -> orden.verify(alquiler).devolver(hoy));
	}
	
	@Test
	void devolverAqluilerNoValidoLanzaExcepcion() {
		OperationNotSupportedException onse = assertThrows(OperationNotSupportedException.class, () -> modeloCascada.devolver(cliente, LocalDate.now()));
		assertEquals(onse.getMessage(), MENSAJE_ERROR_DEVOLVER_ALQUILER_NO_EXISTE);
	}

	@Test
	void borrarClienteLlamaAlquileresGetPrestamosBorrarClientesBorrar() {
		simularClienteConAlquileres();
		InOrder orden = inOrder(clientes, alquileres);
		assertDoesNotThrow(() -> modeloCascada.borrar(cliente));
		orden.verify(alquileres).get(cliente);
		for (Alquiler alquiler : alquileres.get(cliente)) {
			assertDoesNotThrow(() -> orden.verify(alquileres).borrar(alquiler));
		}
		assertDoesNotThrow(() -> orden.verify(clientes).borrar(cliente));
	}

	private void simularClienteConAlquileres() {
		ArrayList<Alquiler> alquileresCliente = new ArrayList<>();
		Alquiler alquiler1 = mock();
		Alquiler alquiler2 = mock();
		alquileresCliente.add(alquiler1);
		alquileresCliente.add(alquiler2);
		when(alquileres.get(cliente)).thenReturn(alquileresCliente);
	}

	@Test
	void borrarTurismoLlamaAlquileresGetPrestamosBorrarTurismosBorrar() {
		simularTurismoConAlquileres();
		InOrder orden = inOrder(vehiculos, alquileres);
		assertDoesNotThrow(() -> modeloCascada.borrar(turismo));
		orden.verify(alquileres).get(turismo);
		for (Alquiler alquiler : alquileres.get(turismo)) {
			assertDoesNotThrow(() -> orden.verify(alquileres).borrar(alquiler));
		}
		assertDoesNotThrow(() -> orden.verify(vehiculos).borrar(turismo));
	}

	private void simularTurismoConAlquileres() {
		ArrayList<Alquiler> alquileresTurismo = new ArrayList<>();
		Alquiler alquiler1 = mock();
		Alquiler alquiler2 = mock();
		alquileresTurismo.add(alquiler1);
		alquileresTurismo.add(alquiler2);
		when(alquileres.get(turismo)).thenReturn(alquileresTurismo);
	}
	
	@Test
	void borrarAlquilerLllamaAlquileresBorrar() {
		when(alquileres.buscar(alquiler)).thenReturn(alquiler);
		assertDoesNotThrow(() -> modeloCascada.borrar(alquiler));
		assertDoesNotThrow(() -> verify(alquileres).borrar(alquiler));
	}
	
	@Test
	void getClientesLlamaClientesGet() {
		List<Cliente> clientesDevueltos = new ArrayList<>();
		clientesDevueltos.add(cliente);
		when(clientes.get()).thenReturn(clientesDevueltos);
		List<Cliente> clientesExistentes = modeloCascada.getListaClientes();
		verify(clientes).get();
		assertNotSame(clientes, clientesExistentes.get(0));
	}
	
	@Test
	void getTurismosLlamaTurismosGet() {
		ArrayList<Vehiculo> turismosDevueltos = new ArrayList<>();
		turismosDevueltos.add(turismo);
		when(vehiculos.get()).thenReturn(turismosDevueltos);
		List<Vehiculo> turismosExistentes = modeloCascada.getListaVehiculos();
		verify(vehiculos).get();
		assertNotSame(vehiculos, turismosExistentes.get(0));
	}
	
	@Test
	void getAlquileresLlamaAlquileresGet() {
		ArrayList<Alquiler> alquileresDevueltos = new ArrayList<>();
		alquileresDevueltos.add(alquiler);
		when(alquileres.get()).thenReturn(alquileresDevueltos);
		List<Alquiler> alquileresExistentes = modeloCascada.getListaAlquileres();
		verify(alquileres).get();
		assertNotSame(alquileres, alquileresExistentes.get(0));
	}
	
	@Test
	void getAlquileresClienteLlamaAlquileresGetCliente() {
		ArrayList<Alquiler> alquileresDevueltos = new ArrayList<>();
		alquileresDevueltos.add(alquiler);
		when(alquileres.get(cliente)).thenReturn(alquileresDevueltos);
		List<Alquiler> alquileresCliente = modeloCascada.getListaAlquileres(cliente);
		verify(alquileres).get(cliente);
		assertNotSame(alquileres, alquileresCliente.get(0));
	}
	
	@Test
	void getAlquileresTurismoLlamaAlquileresGetTurismo() {
		ArrayList<Alquiler> alquileresDevueltos = new ArrayList<>();
		alquileresDevueltos.add(alquiler);
		when(alquileres.get(turismo)).thenReturn(alquileresDevueltos);
		List<Alquiler> alquileresTurismo = modeloCascada.getListaAlquileres(turismo);
		verify(alquileres).get(turismo);
		assertNotSame(alquileres, alquileresTurismo.get(0));
	}
}
