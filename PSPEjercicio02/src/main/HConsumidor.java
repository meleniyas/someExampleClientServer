package main;

import java.util.Random;

public class HConsumidor implements Runnable{

	public Buffer cola;
	
	public HConsumidor(Buffer cola) {
		this.cola=cola;
	};

	@Override
	public void run() {
		
		// Generamos un bucle infinito de extraccion
		while(true) {
	        try {
	            cola.extraer();
	            Thread.sleep(new Random().nextInt(300) + 1);
	        } catch (InterruptedException e) {e.printStackTrace();}
		}
	}

}
