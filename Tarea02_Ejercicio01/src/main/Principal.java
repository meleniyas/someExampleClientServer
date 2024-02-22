package main;

public class Principal {

    public static void main(String[] args) {
        // Creamos una cola con capacidad para 6 caracteres	
    	Buffer cola = new Buffer(6);

        Thread Hiloproductor = new Thread(new HProductor(cola));
        Thread Hiloconsumidor = new Thread(new HConsumidor(cola));
        Thread Hiloproductor2 = new Thread(new HProductor(cola));
        Thread Hiloconsumidor2 = new Thread(new HConsumidor(cola));

        Hiloconsumidor.start();

        Hiloconsumidor2.start();
        Hiloproductor.start();
        Hiloproductor2.start();

    }

}