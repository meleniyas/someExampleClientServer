package main;

import java.util.concurrent.Semaphore;
import java.util.Random;

/*
Control de acceso a una sala de conferencias
Supongamos que estás desarrollando un sistema de gestión para una conferencia. La conferencia tiene una sala con un número limitado de asientos
 y solo permite que un cierto número de personas ingresen a la sala al mismo tiempo.
Implementa un programa en Java que utilice la clase Semaphore para controlar el acceso a la sala de conferencias.
El programa debe tener las siguientes características:

1-La sala de conferencias tiene un límite máximo de asientos, por ejemplo, 10 asientos.
2-Solo se permite que un número específico de personas ingresen a la sala al mismo tiempo, por ejemplo,
		solo 3 personas pueden ingresar simultáneamente.
3-Los participantes de la conferencia deben solicitar permiso antes de ingresar a la sala. Si la sala está llena, 
		deben esperar hasta que haya espacio disponible.
4-Cada participante debe imprimir un mensaje indicando que está ingresando a la sala y otro mensaje indicando que está saliendo de la sala.
5-Asegúrate de sincronizar adecuadamente el acceso a la sala utilizando la clase 
		Semaphore para evitar condiciones de carrera y garantizar que el número máximo de personas en la sala no se exceda.
 */

public class Sala {
	
	private Persona[] asientos;
	private Semaphore sAsientos;
	private Semaphore vestibulo;
	
	public Sala() {
		this.asientos = new Persona[10];
		this.sAsientos = new Semaphore(10);
		this.vestibulo = new Semaphore(3);
		for (int i = 0; i < this.asientos.length; i++) {
			asientos[i] = null;		
		}
	}
	
	public void ingresar(Persona p) throws InterruptedException {
		vestibulo.acquire();
		seeVestibulo(p);
	}

	public void sentarse(Persona p) throws InterruptedException {
		
		sAsientos.acquire();
		System.out.println("La persona "+ p.getId() + " ha tomado asiento.");
		vestibulo.release();
		Thread.sleep(new Random().nextInt(20000) + 10000);

	}

	public void levantarse(Persona p) throws InterruptedException {
		sAsientos.release();
		System.out.println("La persona "+ p.getId() + " ha dejado el asiento.");
		vestibulo.acquire();
		seeVestibulo(p);
	}	
	

	public void salir(Persona p) throws InterruptedException {
		vestibulo.release();
		System.out.println("La persona "+ p.getId() + " se ha marchado.");

	}
	

	

	
	private void seeVestibulo(Persona p) {
		System.out.println("La persona " + p.getId() + " esta en el vestibulo");
		if(this.vestibulo.availablePermits()==1) {
			System.out.print("[X][X][ ]");}
		else if(this.vestibulo.availablePermits()==2) {
			System.out.print("[X][ ][ ]");}
		else if(this.vestibulo.availablePermits()==0) {
			System.out.print("[X][X][X]");}
		else {
			System.out.print("[ ][ ][ ]");}
		System.out.print("\n");
	}

}
