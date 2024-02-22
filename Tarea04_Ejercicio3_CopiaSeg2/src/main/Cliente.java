package main;

import java.util.Scanner;
import java.io.IOException;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;

public class Cliente {
	
	private static String token = null;
	private static int puertoServidor = 1500;
	private static String status = "0";
	
    public static void main(String[] args) {
    	
    	try {
    		
    		Socket socket = new Socket("localhost", puertoServidor);
    		
    		accesoServidor(token,socket);
    		
    		/*
    		while(true) {
        		if(status.equals("0")) {
        			
        		}
        		else if(status.equals("1")) {
        			System.out.println(status);
        		}
        		else if(status.equals("2")) {
        			System.out.println(status);
        		}
        		System.out.println(status);
    		}
    		*/

    		
		} catch (Exception e) {System.out.println("No se ha podido conectar con el Servidor");}
    	
    	
    	
    	/*
    	//Cuando lanzamos el proceso cliente intentamos conectarnos al servidor
        try (
    		Socket socket = new Socket("localhost", puertoServidor);
        	
        	PrintWriter datosEnviados = new PrintWriter(socket.getOutputStream(), true);
        	BufferedReader datosRecibidos = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        		
        ) {
        	while (true) {
        		@SuppressWarnings("resource")
				Scanner datosUsuario = new Scanner(System.in);
            	//Recibimos instrucciones del servidor
        		
        		//Instrucción
        		String instruccion = datosRecibidos.readLine();
        		System.out.println(instruccion);

            	//Respuesta
            	datosEnviados.println(datosUsuario.nextLine());	
            	datosEnviados.flush();
            	
        		//Resultado
        		String r = datosRecibidos.readLine();
        		System.out.println(instruccion);
        	}
        	
		} catch (IOException e) {System.out.println("No se ha podido conectar con el servidor");}
    */
    }

	private static void accesoServidor(String token, Socket socket) throws IOException {
    	enviarDatos("hola",socket);
    	String datos[] = recibirDatos(socket).split(";");
    	status= datos[0];
    	System.out.println(status);
    	if(status == "0") {
    		datos = recibirDatos(socket).split(";");
    		System.out.println(datos[0]);
    		Scanner sc = new Scanner(System.in);
    		String usuario = sc.nextLine();

    	}
	}

	private static String recibirDatos(Socket socket) throws IOException {
		InputStream inputStream = socket.getInputStream();
		String datosCliente = null;
		
		byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        String respuesta = new String(buffer, 0, bytesRead);
        
		datosCliente = respuesta;
		
		return datosCliente;
		
	}

	private static void enviarDatos(String datos, Socket socket) throws IOException {
		PrintWriter enviarDatos = new PrintWriter(socket.getOutputStream(), true);
		enviarDatos.println(token+";"+datos);
	}
    
}