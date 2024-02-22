package main;

import java.util.Random;

public class Principal {
	
	public static void main(String[] args) throws InterruptedException {
		
		Sala sala = new Sala();

		int contador = 1;
		while(true) {
			
			Persona p = new Persona(contador, sala);
			p.start();
			contador++;
			Thread.sleep(new Random().nextInt(2000) + 100);
		}
		
	}
	

}
