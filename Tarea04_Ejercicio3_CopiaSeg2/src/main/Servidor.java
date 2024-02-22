package main;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Servidor {
	
    static Map<String, String> usuarios = new HashMap<>();
    static Map<String, Integer> token = new HashMap<>();

	//Creamos un servidor con hilos para manejar varios clientes
	public static void main(String[] args) {
		int puertoEscucha = 1500;
		
	    usuarios.put("arturo", "secreta");
	    token.put("arturo", null);
	    
	    usuarios.put("armando", "bronca");
	    token.put("armando", null);
	    
	    usuarios.put("aitor", "menta");
	    token.put("aitor", null);
	    
		int hash = Math.abs((int) ("arturo".hashCode()*Math.random()));
	    System.out.println(hash);
		
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
	
	public static boolean checkAuth(String user, String pw) {
		boolean checkAuth = false;
		String credenciales = usuarios.get(user);
		if(credenciales != null) {
			if(credenciales.equals(pw)) {
				checkAuth = true;
			}
		}
		return checkAuth;
	}
	private static boolean checkToken(String user) {
		boolean checklogged = false;
		Integer log = token.get(user);
		if( log != null ) {
			checklogged = true;
			openSession(user);
		}
		return checklogged;
	}
	
	private static void openSession(String user) {
		int hash = Math.abs((int) (user.hashCode()*Math.random()));
		token.put(user, hash);
	};
	private static void closeSession(String user) {
		token.put(user, null);
	};

}