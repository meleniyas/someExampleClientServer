package main;

public class HConsumidor implements Runnable {
	
	public Buffer cola;
	
	public HConsumidor(Buffer cola) {
		this.cola=cola;
	};

	@Override
	public void run() {
        for (int i = 0; i < 15; i++) {
            try {
                cola.extraer(); 
            } catch (InterruptedException e) {e.printStackTrace();}
        }
		
	}

}
