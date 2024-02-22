package main;

public class Principal {
	
	 /*****************************************************************
	  * Programa de Hilos
	  */

	public static void main(String[] args) {
		
        // Creamos una cola con capacidad para 7 palabras	
    	Buffer cola = new Buffer(7);

    	// Creamos los procesos pasandole el buffer
        Thread Hiloproductor = new Thread(new HProductor(cola));
        Thread Hiloconsumidor = new Thread(new HConsumidor(cola));

        // Los iniciamos
        Hiloproductor.start();
        Hiloconsumidor.start();

	}

}
