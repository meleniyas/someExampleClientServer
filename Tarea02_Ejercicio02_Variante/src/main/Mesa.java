package main;

public class Mesa {
	
	private boolean[] palillos;
	
	//Decimos cuantos palillos hay en la mesa
	public Mesa(int numPalillos){
		this.palillos = new boolean [numPalillos];
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
		while(palillos[palilloIzq(posFilosofo)] || palillos[palilloDrch(posFilosofo)]) {
			try {
				wait();
			} catch (InterruptedException e) {e.printStackTrace();}
		}
		System.out.println("El filosofo " + posFilosofo + " está comiendo...");
		palillos[palilloIzq(posFilosofo)] = true;
		palillos[palilloDrch(posFilosofo)] = true;
	}
	
	public synchronized void dejarPalillo(int posFilosofo) {
		System.out.println("El filósofo " + posFilosofo + " ha terminado de comer.");
		System.out.println("Los palillos " + this.palilloDrch(posFilosofo) + " y " + this.palilloIzq(posFilosofo) + " están libres.");

		palillos[palilloIzq(posFilosofo)] = false;
		palillos[palilloDrch(posFilosofo)] = false;
		notifyAll();
	}

	
	
}
