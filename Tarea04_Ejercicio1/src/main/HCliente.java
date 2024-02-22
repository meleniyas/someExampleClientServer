package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class HCliente extends Thread {

    private Socket clienteSocket;
    private int numeroSecreto;

    public HCliente(Socket clienteSocket) {
		this.clienteSocket = clienteSocket;
		this.numeroSecreto = (int) (Math.random()*100+1);
    }

    @Override
    public void run() {

    	try (
			Scanner datosRecibidos = new Scanner(clienteSocket.getInputStream());
			PrintWriter datosEnviados = new PrintWriter(clienteSocket.getOutputStream(), true)
    	)
    	{	
    		datosEnviados.println("Adivine el numero entre 0 y 100 (fin para salir)");
    		boolean exit = false;

    		while (!exit) {
				String entradaCliente = datosRecibidos.nextLine();
				if (entradaCliente.equalsIgnoreCase("fin")) {
				    exit = true;
				}
				
				
				try {
					int numeroCliente = Integer.parseInt(entradaCliente);
					if (numeroCliente < numeroSecreto) {datosEnviados.println("El numero secreto es mayor.");}
					else if (numeroCliente > numeroSecreto) {datosEnviados.println("El numero secreto es menor.");}
					else {
						datosEnviados.println("Enhorabuena!!! Ha acertado el numero.");

			            clienteSocket.close();
						exit = true;
	                }
				} catch (Exception e) {
					datosEnviados.println("Introduzca un numero valido.");
				}
	
    		}
               
		} catch (Exception e) {
	    } finally {
	        try {
	            clienteSocket.close();
	            System.out.println("El cliente se ha desconectado.");
	            Servidor.clienteDesconectado();
	        } catch (IOException e) {;e.printStackTrace();}
	    }
    }
}