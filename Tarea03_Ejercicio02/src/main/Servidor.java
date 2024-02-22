package main;

import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	
	//Creamos un servidor con hilos para manejar varios clientes
	public static void main(String[] args) {
		int puertoEscucha = 1500;
		
		try(ServerSocket serverSocket = new ServerSocket(puertoEscucha)){
			
			System.out.println("Servidor esperando conexiones en el puerto " + puertoEscucha);
			while (true) {
				Socket clienteSocket = serverSocket.accept();
				System.out.println("Nuevo cliente conectado.");
                HCliente hiloCliente = new HCliente(clienteSocket);
                new Thread(hiloCliente).start();
			}
			
		} catch (Exception e) {e.printStackTrace();}

	}

}
