package main;
import java.util.ArrayList;

public class Buffer {
	
    private ArrayList<Character> cola;
    private int capacidad;

    public Buffer(int capacidad) {
        this.cola = new ArrayList<Character>();
        this.capacidad = capacidad;
    }

    public synchronized void incluir(char c) throws InterruptedException {
        // Controlamos el llenado de la cola. Si esta llena esperamos
    	while (cola.size() == capacidad) {
            wait();
        }
    	// Cuando tenemos sitio añadimos el caracter
        cola.add(c);
        System.out.println("Se produce: " + c);
        // Una vez añadido notificamos que "tenemos datos disponibles"
        notify(); 
    }

    public synchronized char extraer() throws InterruptedException {
        // Controlamos el vaciado de la cola. Si esta vacia esperamos
        while (cola.isEmpty()) {
            wait();
        }
        // Cuando tenemos datos los retiramos (consumimos)
        char c = cola.remove(0);
        System.out.println("Se consume: " + c);
        // Una vez retirado notificamos que "el bufer tiene espacio"
        notify();
        return c;
    }
}
