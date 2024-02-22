package main;

public class Principal {

	public static void main(String[] args) {
        int numFilosofos = 5;
        int numPalillos = numFilosofos;

		Mesa mesa = new Mesa(numPalillos);

        for (int i = 0; i < numFilosofos; i++) {
        	Filosofo f = new Filosofo(mesa, i);
        	f.start();
        }

	}

}
