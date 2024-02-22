package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	public static void main(String[] args) {
		
		int puertoEscucha = 2000;
		int numSecreto = (int) (Math.random()*100+1);
		
        try (ServerSocket serverSocket = new ServerSocket(puertoEscucha)) {
            System.out.println("Servidor esperando conexiones en el puerto " + puertoEscucha);

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Nuevo cliente conectado.");

                HCliente hiloCliente = new HCliente(clienteSocket, numSecreto);
                new Thread(hiloCliente).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
