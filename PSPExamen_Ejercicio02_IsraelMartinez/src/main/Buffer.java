package main;

import java.util.ArrayList;

public class Buffer {
	
    private ArrayList<String> cola;
    private int capacidad;

    public Buffer(int capacidad) {
        this.cola = new ArrayList<String>();
        this.capacidad = capacidad;
    }

    public synchronized void incluir(String s) throws InterruptedException {
        // Controlamos el llenado de la cola. Si esta llena esperamos
    	while (cola.size() == capacidad) {
            wait();
        }
    	// Cuando tenemos sitio añadimos la palabra
        cola.add(s);
        // Una vez añadido notificamos que "tenemos datos disponibles"
        notify(); 
    }

    public synchronized String extraer() throws InterruptedException {
        // Controlamos el vaciado de la cola. Si esta vacia esperamos
        while (cola.isEmpty()) {
        	System.out.println("Me aburro...");
            wait();
        }
        // Cuando tenemos datos los retiramos (consumimos)
        String c = cola.remove(0);
        System.out.println("Se consume: " + c);
        // Una vez retirado notificamos que "el bufer tiene espacio"
        notify();
        return c;
    }

}
