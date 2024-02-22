package main;

public class Persona extends Thread{
	
	private long id;
	private int asiento;
	private Sala sala;
	
	public Persona(int i, Sala sala) {
		setId(i);
		setSala(sala);

	}
	
	@Override
	public void run() {
		try {
			System.out.println("La persona "+ getId() + " llega a la cola.");
			getSala().ingresar(this);
			getSala().sentarse(this);
			getSala().levantarse(this);
			getSala().salir(this);
		} catch (InterruptedException e) {}
	}
	
	public void asistir() throws InterruptedException {
		getSala().ingresar(this);
	}
	
	public void marcharse() throws InterruptedException {
		getSala().salir(this);
	}
	
	public long getId() {return id;}
	public void setId(int id) {this.id = id;}

	public int getAsiento() {return asiento;}
	public void setAsiento(int asiento) {this.asiento = asiento;}

	public Sala getSala() {return sala;}
	public void setSala(Sala sala) {this.sala = sala;}
	
	
}
