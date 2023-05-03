package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VehiculoTest {
	
	private static final String MENSAJE_ERROR_MARCA_NULA = "ERROR: La marca no puede ser nula.";
	private static final String MENSAJE_ERROR_FORMATO_MARCA_NO_VALIDA = "ERROR: La marca no tiene un formato válido.";
	private static final String MENSAJE_ERROR_MODELO_NULO = "ERROR: El modelo no puede ser nulo.";
	private static final String MENSAJE_ERROR_MODELO_BLANCO = "ERROR: El modelo no puede estar en blanco.";
	private static final String MENSAJE_ERROR_CILINDRADA_NO_VALIDA = "ERROR: La cilindrada no es correcta.";
	private static final String MENSAJE_ERROR_MATRICULA_NULA = "ERROR: La matrícula no puede ser nula.";
	private static final String MENSAJE_ERROR_FORMATO_MATRICULA_NO_VALIDA = "ERROR: La matrícula no tiene un formato válido.";
	private static final String MENSAJE_ERROR_VEHICULO_NULO = "ERROR: No es posible copiar un vehículo nulo.";
	
	private static final String MARCA_VALIDA = "Seat";
	private static final String MODELO_VALIDO = "León";
	private static final int CILINDRADA_VALIDA = 90;
	private static final String MATRICULA_VALIDA = "1234BCD";
	
	private Vehiculo vehiculo;
	private Turismo turismo;
	
	@BeforeEach
	void init() {
		vehiculo = new Turismo(MARCA_VALIDA, MODELO_VALIDO, CILINDRADA_VALIDA, MATRICULA_VALIDA);
		turismo = new Turismo(MARCA_VALIDA, MODELO_VALIDO, CILINDRADA_VALIDA, MATRICULA_VALIDA);
	}

	@Test
	void constructorMarcaValidaModeloValidoCilindradaValidaMatrivaValidaCreaTurismoCorrectamente() {
		assertEquals(MARCA_VALIDA, vehiculo.getMarca());
		assertEquals(MODELO_VALIDO, vehiculo.getModelo());
		assertEquals(CILINDRADA_VALIDA, turismo.getCilindrada());
		assertEquals(MATRICULA_VALIDA, vehiculo.getMatricula());
		vehiculo = new Turismo("Land Rover", MODELO_VALIDO, CILINDRADA_VALIDA, MATRICULA_VALIDA);
		vehiculo = new Turismo("KIA", MODELO_VALIDO, CILINDRADA_VALIDA, MATRICULA_VALIDA);
		vehiculo = new Turismo("Rolls-Royce", MODELO_VALIDO, CILINDRADA_VALIDA, MATRICULA_VALIDA);
		vehiculo = new Turismo("SsangYong", MODELO_VALIDO, CILINDRADA_VALIDA, MATRICULA_VALIDA);
	}
	
	@Test
	void constructorMarcaNoValidaModeloValidoCilindradaValidaMatrivaValidaLanzaExcepcion() {
		NullPointerException npe = assertThrows(NullPointerException.class, () -> new Turismo(null, MODELO_VALIDO, CILINDRADA_VALIDA, MATRICULA_VALIDA));
		assertEquals(MENSAJE_ERROR_MARCA_NULA, npe.getMessage());
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> new Turismo("", MODELO_VALIDO, CILINDRADA_VALIDA, MATRICULA_VALIDA));
		assertEquals(MENSAJE_ERROR_FORMATO_MARCA_NO_VALIDA, iae.getMessage());
		iae = assertThrows(IllegalArgumentException.class, () -> new Turismo("", MODELO_VALIDO, CILINDRADA_VALIDA, MATRICULA_VALIDA));
		assertEquals(MENSAJE_ERROR_FORMATO_MARCA_NO_VALIDA, iae.getMessage());
		iae = assertThrows(IllegalArgumentException.class, () -> new Turismo("  ", MODELO_VALIDO, CILINDRADA_VALIDA, MATRICULA_VALIDA));
		assertEquals(MENSAJE_ERROR_FORMATO_MARCA_NO_VALIDA, iae.getMessage());
		iae = assertThrows(IllegalArgumentException.class, () -> new Turismo("AA-BB", MODELO_VALIDO, CILINDRADA_VALIDA, MATRICULA_VALIDA));
		assertEquals(MENSAJE_ERROR_FORMATO_MARCA_NO_VALIDA, iae.getMessage());
		iae = assertThrows(IllegalArgumentException.class, () -> new Turismo("aa", MODELO_VALIDO, CILINDRADA_VALIDA, MATRICULA_VALIDA));
		assertEquals(MENSAJE_ERROR_FORMATO_MARCA_NO_VALIDA, iae.getMessage());
		iae = assertThrows(IllegalArgumentException.class, () -> new Turismo("aa bb", MODELO_VALIDO, CILINDRADA_VALIDA, MATRICULA_VALIDA));
		assertEquals(MENSAJE_ERROR_FORMATO_MARCA_NO_VALIDA, iae.getMessage());
	}
	
	@Test
	void constructorMarcaValidaModeloNoValidoCilindradaValidaMatrivaValidaLanzaExcepcion() {
		NullPointerException npe = assertThrows(NullPointerException.class, () -> new Turismo(MARCA_VALIDA, null, CILINDRADA_VALIDA, MATRICULA_VALIDA));
		assertEquals(MENSAJE_ERROR_MODELO_NULO, npe.getMessage());
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> new Turismo(MARCA_VALIDA, "", CILINDRADA_VALIDA, MATRICULA_VALIDA));
		assertEquals(MENSAJE_ERROR_MODELO_BLANCO, iae.getMessage());
		iae = assertThrows(IllegalArgumentException.class, () -> new Turismo(MARCA_VALIDA, " ", CILINDRADA_VALIDA, MATRICULA_VALIDA));
		assertEquals(MENSAJE_ERROR_MODELO_BLANCO, iae.getMessage());
		iae = assertThrows(IllegalArgumentException.class, () -> new Turismo(MARCA_VALIDA, "	", CILINDRADA_VALIDA, MATRICULA_VALIDA));
		assertEquals(MENSAJE_ERROR_MODELO_BLANCO, iae.getMessage());
	}
	
	@Test
	void constructorMarcaValidaModeloValidoCilindradaNoValidaMatrivaValidaLanzaExcepcion() {
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> new Turismo(MARCA_VALIDA, MODELO_VALIDO, 0, MATRICULA_VALIDA));
		assertEquals(MENSAJE_ERROR_CILINDRADA_NO_VALIDA, iae.getMessage());
		iae = assertThrows(IllegalArgumentException.class, () -> new Turismo(MARCA_VALIDA, MODELO_VALIDO, 5001, MATRICULA_VALIDA));
		assertEquals(MENSAJE_ERROR_CILINDRADA_NO_VALIDA, iae.getMessage());
	}
	
	@Test
	void constructorMarcaValidaModeloValidoCilindradaValidaMatrivaNoValidaLanzaExcepcion() {
		NullPointerException npe = assertThrows(NullPointerException.class, () -> new Turismo(MARCA_VALIDA, MODELO_VALIDO, CILINDRADA_VALIDA, null));
		assertEquals(MENSAJE_ERROR_MATRICULA_NULA, npe.getMessage());
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> new Turismo(MARCA_VALIDA, MODELO_VALIDO, CILINDRADA_VALIDA, "1234bcd"));
		assertEquals(MENSAJE_ERROR_FORMATO_MATRICULA_NO_VALIDA, iae.getMessage());
		iae = assertThrows(IllegalArgumentException.class, () -> new Turismo(MARCA_VALIDA, MODELO_VALIDO, CILINDRADA_VALIDA, "1234ABC"));
		assertEquals(MENSAJE_ERROR_FORMATO_MATRICULA_NO_VALIDA, iae.getMessage());
		iae = assertThrows(IllegalArgumentException.class, () -> new Turismo(MARCA_VALIDA, MODELO_VALIDO, CILINDRADA_VALIDA, "1234BC"));
		assertEquals(MENSAJE_ERROR_FORMATO_MATRICULA_NO_VALIDA, iae.getMessage());
		iae = assertThrows(IllegalArgumentException.class, () -> new Turismo(MARCA_VALIDA, MODELO_VALIDO, CILINDRADA_VALIDA, "234BCD"));
		assertEquals(MENSAJE_ERROR_FORMATO_MATRICULA_NO_VALIDA, iae.getMessage());
	}
	
	@Test
	void constructorVehiculoValidoCopiaVehiculoCorrectamente() {
		Vehiculo vehiculoCopia = new Turismo(turismo);
				
		assertEquals(vehiculo, vehiculoCopia);
		assertNotSame(vehiculo, vehiculoCopia);
		assertEquals(MARCA_VALIDA, vehiculoCopia.getMarca());
		assertEquals(MODELO_VALIDO, vehiculoCopia.getModelo());
		assertEquals(MATRICULA_VALIDA, vehiculoCopia.getMatricula());
	}
	
	@Test
	void constructoVehiculoNuloLanzaExcepcion() {
		NullPointerException npe = assertThrows(NullPointerException.class, () -> new Turismo(null));
		assertEquals(MENSAJE_ERROR_VEHICULO_NULO, npe.getMessage());
	}
	
	@Test
	void getVehiculoConMatriculaValidaDevuelveVehiculoConDichaMatricula() {
		@SuppressWarnings("unused")
		Vehiculo vehiculo = Vehiculo.getVehiculoConMatricula(MATRICULA_VALIDA);
		assertEquals(MATRICULA_VALIDA, turismo.getMatricula());
	}
	
	@Test
	void getVehiculoConMatriculaNoValidaLanzaExcepcion() {
		NullPointerException npe = assertThrows(NullPointerException.class, () -> Vehiculo.getVehiculoConMatricula(null));
		assertEquals(MENSAJE_ERROR_MATRICULA_NULA, npe.getMessage());
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> Vehiculo.getVehiculoConMatricula("1234bcd"));
		assertEquals(MENSAJE_ERROR_FORMATO_MATRICULA_NO_VALIDA, iae.getMessage());
		iae = assertThrows(IllegalArgumentException.class, () -> Vehiculo.getVehiculoConMatricula("1234ABC"));
		assertEquals(MENSAJE_ERROR_FORMATO_MATRICULA_NO_VALIDA, iae.getMessage());
		iae = assertThrows(IllegalArgumentException.class, () -> Vehiculo.getVehiculoConMatricula("1234BC"));
		assertEquals(MENSAJE_ERROR_FORMATO_MATRICULA_NO_VALIDA, iae.getMessage());
		iae = assertThrows(IllegalArgumentException.class, () -> Vehiculo.getVehiculoConMatricula("234BCD"));
		assertEquals(MENSAJE_ERROR_FORMATO_MATRICULA_NO_VALIDA, iae.getMessage());
	}
	
	@Test
	void equalsYHasCodeConsistentes() {
		Vehiculo turismoIgual = Vehiculo.getVehiculoConMatricula(MATRICULA_VALIDA);
		assertEquals(vehiculo, vehiculo);
		assertEquals(vehiculo, turismoIgual);
		assertEquals(turismoIgual, vehiculo);
		assertEquals(vehiculo.hashCode(), turismoIgual.hashCode());
		Vehiculo turismoDiferente = new Turismo(MARCA_VALIDA, MODELO_VALIDO, CILINDRADA_VALIDA, "1111BBB");
		assertNotEquals(vehiculo, "");
		assertNotEquals(vehiculo, turismoDiferente);
		assertNotEquals(vehiculo.hashCode(), turismoDiferente.hashCode());
		assertNotEquals(vehiculo, null);
	}
	
	@Test
	void toStringDevuelveLaCadenaEsperada() {
		assertEquals(String.format("%s %s - %s", MARCA_VALIDA, MODELO_VALIDO, MATRICULA_VALIDA, "disponible"), vehiculo.toString());
	}
}

