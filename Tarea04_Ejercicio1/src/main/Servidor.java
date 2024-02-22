package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	
	private static int maxConexiones = 10;
	private static int conexionesActuales = 0;

	public static void main(String[] args) {
		
		int puertoEscucha = 2000;;
		
        try (ServerSocket serverSocket = new ServerSocket(puertoEscucha)) {
            System.out.println("Servidor esperando conexiones en el puerto " + puertoEscucha);

            while (true) {
            	while(check()) {
                    Socket clienteSocket = serverSocket.accept();
                    System.out.println("Nuevo cliente conectado.");

                    HCliente hiloCliente = new HCliente(clienteSocket);
                    new Thread(hiloCliente).start();
                    
                    conexionesActuales++;
                    System.out.println(conexionesActuales);
            	}
            	while(!check()) {
            		try {
						Thread.sleep(1000);
						check();
					} catch (InterruptedException e) {}
            	}

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	private static boolean check() {
		return conexionesActuales < maxConexiones;
	}

	public static void clienteDesconectado() {
		conexionesActuales--;
		System.out.println(conexionesActuales);

		
	}
}
