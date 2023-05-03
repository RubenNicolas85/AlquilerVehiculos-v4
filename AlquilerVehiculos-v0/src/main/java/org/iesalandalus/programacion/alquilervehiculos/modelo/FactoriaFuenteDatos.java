package org.iesalandalus.programacion.alquilervehiculos.modelo;

import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros.FuenteDatosFicheros;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.memoria.FuenteDatosMemoria; 

public enum FactoriaFuenteDatos {
	
	/** La factoría fuente de datos la tenemos en una clase de tipo Enum, donde de momento tenemos
	 * dos tipos: Memoria y Ficheros. La factoría fuente de datos permite, mediante el uso del
	 * patrón factoría abstracta, crear la parte lógica de nuestro programa simplemente cambiando
	 * en el método Main de nuestra clase principal el objeto de tipo FactoriaFuenteDatos. Una vez
	 * que el modelo ya está creado, se llamada al método crear, que en función de si es MEMORIA
	 * o FICHEROS retornará un nuevo objeto de tipo IFuenteDatos que será la clase 
	 * FuenteDatosMemoria ó FuenteDatosFicheros.
	 * 
	 * Para obligar a que los tipos enumerados MEMORIA y FICHEROS desarrollen el método crear, 
	 * dentro del propio enumerado se define el método abstracto abstract IFuenteDatos crear().	 
	 * 
	 * Por ejemplo, si al crear el modelo en el método Main le pasamos como parámetro 
	 * FactoriaFuenteDatos.MEMORIA, la parte lógica de nuestro programa seguirá igual que en las 
	 * anteriores versiones. Por el contrario, si le pasamos como parámetro 
	 * FactoriaFuenteDatos.FICHEROS implementaremos la parte nueva del negocio de nuestro modelo,
	 * en la que al iniciar nuestro programa leeremos los ficheros XML de clientes, vehículos y 
	 * alquileres y volcaremos toda la información a memoria, es decir, a los ArrayList para poder
	 * seguir trabajando con los objetos como hasta ahora.
	 * 
	 * Cuando cerremos el programa, todos los objetos almacenados en los diferentes ArrayList 
	 * (alquileres, clientes, vehículos) se volcarán de nuevo en los diferentes ficheros XML. */
	
	MEMORIA {
		@Override
		IFuenteDatos crear() {
			
			IFuenteDatos fuenteDatosMemoria = new FuenteDatosMemoria(); 
			
			return fuenteDatosMemoria;
		}
		
	}, FICHEROS {
		@Override
		IFuenteDatos crear() {
			
			IFuenteDatos fuenteDatosFicheros = new FuenteDatosFicheros(); 
			
			return fuenteDatosFicheros;
		}
	};  
	
	abstract IFuenteDatos crear(); 
}

	
	

