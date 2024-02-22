package main;

public class Filosofo extends Thread{
	
	private Mesa mesa;
	private int posFilosofo;
	
	public Filosofo(Mesa mesa, int posFilosofo) {
		this.mesa = mesa;
		this.posFilosofo = posFilosofo;
	}
	
	@Override
	public void run() {
		while(true) {
			this.pensar();
			this.comer();
		}	
	}
	
	public void pensar() {
		
		System.out.println("El filosofo " + posFilosofo + " está pensando...");
        try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {e.printStackTrace();}
		
	}
	
	public void comer() {
		this.mesa.cogerPalillo(this.posFilosofo);
        try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {e.printStackTrace();}
		this.mesa.dejarPalillo(this.posFilosofo);
		}
	
}
