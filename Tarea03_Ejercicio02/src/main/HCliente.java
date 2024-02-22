package main;

import java.net.Socket;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

import java.util.Scanner;


public class HCliente extends Thread {
	private Socket clienteSocket;
	
	//Constructor para la conexion de clientes	
	public HCliente(Socket clienteSocket) {
		this.clienteSocket = clienteSocket;
	}
	
	@Override
	public void run() {
    	try (
			Scanner datosRecibidos = new Scanner(clienteSocket.getInputStream());
			PrintWriter datosEnviados = new PrintWriter(clienteSocket.getOutputStream(), true)
    	){
    		//Solicitamos un archivo
    		datosEnviados.println("Solicite un archivo:");
    		String archivoSolicitado = datosRecibidos.nextLine();
    		String archivoRuta= "./src/ficherosEnviar/"+archivoSolicitado;

    		File archivo = new File(archivoRuta);
    		
    		//Comprobamos que el archivo exista
    		if (archivo.exists()) {
    			datosEnviados.println("El archivo Existe");
    			
        		//Si el archivo existe lo mandamos con este bucle
    		    try (FileInputStream fis = new FileInputStream(archivo)) {
    		        byte[] buffer = new byte[4096];
    		        int bytesRead;
    		        while ((bytesRead = fis.read(buffer)) != -1) {
    		        	clienteSocket.getOutputStream().write(buffer, 0, bytesRead);
    		        }
    		    }
				
			} else {
				//Si el archivo No Existe mandamos una notificacion y salimos
    			datosEnviados.println("El archivo No Existe");

			}
    		
    		
		} catch (Exception e) {e.printStackTrace();
	    
		} finally {
	        try {
				Thread.sleep(2000);
	            clienteSocket.close();
	            System.out.println("El cliente se ha desconectado.");
	        } catch (IOException | InterruptedException e) {e.printStackTrace();}
	    }
		
	}
}
