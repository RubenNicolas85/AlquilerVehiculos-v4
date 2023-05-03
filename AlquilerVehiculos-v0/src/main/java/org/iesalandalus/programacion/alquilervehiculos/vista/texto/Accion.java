package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

public enum Accion {
	SALIR("Salir") {
		@Override
		public void ejecutar() {
			
			vista.terminar();
		}
	},
	INSERTAR_CLIENTE("Insertar Cliente") {
		@Override
		public void ejecutar() throws Exception {

			vista.insertarCliente();
		}
	},
	INSERTAR_VEHICULO("Insertar Vehículo") {
		@Override
		public void ejecutar() throws Exception {

			vista.insertarVehiculo();
		}
	},
	INSERTAR_ALQUILER("Insertar Alquiler") {
		@Override
		public void ejecutar() throws Exception {

			vista.insertarAlquiler();
		}
	},
	BUSCAR_CLIENTE("Buscar Cliente") {
		@Override
		public void ejecutar() {

			vista.buscarCliente();
		}
	},
	BUSCAR_VEHICULO("Buscar Vehículo") {
		@Override
		public void ejecutar() {

			vista.buscarVehiculo();
		}
	},
	BUSCAR_ALQUILER("Buscar Alquiler") {
		@Override
		public void ejecutar() {

			vista.buscarAlquiler();
		}
	},
	MODIFICAR_CLIENTE("Modificar Cliente") {
		@Override
		public void ejecutar() throws Exception {

			vista.modificarCliente();
		}
	},
	DEVOLVER_ALQUILER_CLIENTE("Devolver Alquiler Cliente") {
		@Override
		public void ejecutar() throws Exception {

			vista.devolverAlquilerCliente();
		}
	},
	DEVOLVER_ALQUILER_VEHICULO("Devolver Alquiler Vehiculo") {
		@Override
		public void ejecutar() throws Exception {

			vista.devolverAlquilerVehiculo();
		}
	},
	BORRAR_CLIENTE("Borrar Cliente") {
		@Override
		public void ejecutar() throws Exception {

			vista.borrarCliente();
		}
	},
	BORRAR_VEHICULO("Borrar Vehículo") {
		@Override
		public void ejecutar() throws Exception {

			vista.borrarVehiculo();
		}
	},
	BORRAR_ALQUILER("Borrar Alquiler") {
		@Override
		public void ejecutar() throws Exception {

			vista.borrarAlquiler();
		}
	},
	LISTAR_CLIENTES("Listar Clientes") {
		@Override
		public void ejecutar() {

			vista.listarClientes();
		}
	},
	LISTAR_VEHICULOS("Listar Vehículos") {
		@Override
		public void ejecutar() {

			vista.listarVehiculos();
		}
	},
	LISTAR_ALQUILERES("Listar Alquileres") {
		@Override
		public void ejecutar() {

			vista.listarAlquileres();
		}
	},
	LISTAR_ALQUILERES_CLIENTE("Listar Alquileres Cliente") {
		@Override
		public void ejecutar() {

			vista.listarAlquileresCliente();
		}
	},
	LISTAR_ALQUILERES_VEHICULO("Listar Alquileres Vehículo") {
		@Override
		public void ejecutar() {

			vista.listarAlquileresVehiculo();
		}
	},
	
	MOSTRAR_ESTADISTICAS_MENSUALES("Mostrar Estadisticas Mensuales") {
		@Override
		public void ejecutar() {

			vista.mostrarEstadisticasMensualesTipoVehiculo(); 
		}
	};

	private static VistaTexto vista;

	private String texto;

	private Accion(String texto) {

		this.texto = texto;
	}

	public abstract void ejecutar() throws Exception;

	protected static void setVista(VistaTexto vista) {
		
		Accion.vista = vista;
	}

	protected static boolean esOrdinalValido(int ordinal) {
	
			return (ordinal >= 0 && ordinal <= Accion.values().length - 1);
	}
			
	public static Accion get(int ordinal) {
		
		if (esOrdinalValido(ordinal)) {
			
			return values()[ordinal];
			
		}else {
			
			throw new IllegalArgumentException("Ordinal de la opción no válido");
		}
	}

	public String toString() {

		return String.format("%d.- %s", ordinal(), texto);
	}
}