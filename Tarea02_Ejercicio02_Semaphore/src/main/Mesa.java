package main;

import java.util.concurrent.Semaphore;

public class Mesa {
	
	private Semaphore[] palillos;
	
	//Decimos cuantos palillos hay en la mesa
	public Mesa(int numPalillos){
		this.palillos = new Semaphore [numPalillos];
        for (int i = 0; i < numPalillos; i++) {
            this.palillos[i] = new Semaphore(1);
        }
	}
	
	public int palilloIzq(int posFilosofo) {
		if(posFilosofo == 0) {
			return this.palillos.length-1;
		} else {
			return posFilosofo-1;
		}
	}
	
	public int palilloDrch(int posFilosofo) {
		return posFilosofo;

	}

	public synchronized void cogerPalillo(int posFilosofo) {
		while(palillos[palilloIzq(posFilosofo)].availablePermits() == 0|| palillos[palilloDrch(posFilosofo)].availablePermits() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		System.out.println("El filosofo " + posFilosofo + " está comiendo...");
		try {
			palillos[palilloIzq(posFilosofo)].acquire();
			palillos[palilloDrch(posFilosofo)].acquire();
		} catch (InterruptedException e) {e.printStackTrace();}

	}
	
	public synchronized void dejarPalillo(int posFilosofo) {
		System.out.println("El filósofo " + posFilosofo + " ha terminado de comer.");
		System.out.println("Los palillos " + this.palilloDrch(posFilosofo) + " y " + this.palilloIzq(posFilosofo) + " están libres.");

		palillos[palilloIzq(posFilosofo)].release();
		palillos[palilloDrch(posFilosofo)].release();
		notifyAll();
	}

	
	
}
