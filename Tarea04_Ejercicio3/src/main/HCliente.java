package main;

import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class HCliente extends Thread {
	private InputStream inputStream;
	private OutputStream outputStream;
	
	private String token = "";
	private String user;
	
    private Map<Integer, String> estados = new HashMap<>();
    private String estadoActual = "pausdo";


	private boolean salir = false;
    
	//Constructor para la conexion de clientes	
	public HCliente(Socket clienteSocket) {
		
		
		try {
			this.inputStream = clienteSocket.getInputStream();
			this.outputStream = clienteSocket.getOutputStream();
		} catch (IOException e) {
			System.out.println("ERROR: No se ha podido establecer la conexión con el cliente");
			
		} 
		this.estados.put(0,"pausado");
		this.estados.put(1,"recibiendo");
		this.estados.put(2,"enviando");
	}
	
	@Override
	public void run() {
		
				

		while( checkSalir() )
		{	
			enrutador();
		}

		System.out.println("El cliente se ha desconectado");
		
		
		

	}
	
	public boolean checkSalir() {
		return !salir;
	}

	public void salir() {
		Servidor.closeSession(this.user, this);
		this.salir = true;
	}

	private void enrutador() {

		String datos = recibirDatos();
		int status = checkearEstado(datos);
		
		switch (status) {
			// Loggear
			case 0: {
				autenticar();
				break;
			}
			// Servicio
			case 1: {
				servicio();
				break;
				
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + status);
		}
		
	}
	
	private void servicio() {
		// Solicitar usuario
		estadoActual = "enviando";
		enviarDatos("recibiendo", "Bienvenido al servicio. Seleccione carpeta:");
        try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
	
		try {
			String directorioOrigen =  "./src/directorioFicheros/";
			
			@SuppressWarnings("resource")
			Stream<Path> miStream = Files.walk(Paths.get(directorioOrigen));
			List<Object> listaPaths = miStream.collect(Collectors.toList());
			
			String seleccion = "-1";
			while(Integer.valueOf(seleccion) != 0) {			
				//Recorremos la lista de archivo
				for (int i = 0; i < listaPaths.size(); i++) {
				    
					String rutaFichero = listaPaths.get(i).toString();
				    File fichero = new File(rutaFichero);
				    if (fichero.isFile()) {
				        enviarDatos("recibiendo", ">>> " + i + " " + fichero.getName());
                        try {
							Thread.sleep(100);
						} catch (InterruptedException e) {}
				    }
				}
                
				do {
					estadoActual = "enviando";
					enviarDatos("enviando", " -----------------------------");
					
					estadoActual = "recibiendo";
					seleccion = recibirDatos().split(";")[1];

					
				} while (Integer.valueOf(seleccion) > listaPaths.size() || Integer.valueOf(seleccion) < 0);

				String path = listaPaths.get(Integer.valueOf(seleccion)).toString();
				File archivo = new File(path);
				
				if(Integer.valueOf(seleccion) != 0) {

			
			   		//Comprobamos que el archivo exista
		    		if (archivo.exists()) {
		    			estadoActual = "enviando";
		    			enviarDatos("recibiendo", " CONTENIDO");
		    			
		                try {
							Thread.sleep(100);
						} catch (InterruptedException e) {}
		    			
		        		//Si el archivo existe lo mandamos con este bucle
		    		    try (FileInputStream fis = new FileInputStream(archivo)) {
		    		        byte[] buffer = new byte[4096];
		    		        int bytesRead;
		    		        
		    		        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		    		        
		    		        while ((bytesRead = fis.read(buffer)) != -1) {
		    	
		    		        	byteArrayOutputStream.write(buffer, 0, bytesRead);
		    		        }
		    		        String contenido = byteArrayOutputStream.toString();
		        			estadoActual = "enviando";
		        			enviarDatos("recibiendo", contenido); 
		    		        
		    		    }
						
					} else {
						//Si el archivo No Existe mandamos una notificacion y salimos
		    			estadoActual = "enviando";
		    			enviarDatos("recibiendo", " el archivo no exite");
		                try {
							Thread.sleep(100);
						} catch (InterruptedException e) {}
		    			
					}
		            try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {}
					estadoActual = "enviando";
					enviarDatos("enviando", "-----------Pulse 0 para salir o ENTER para ver mas archivos ------------");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {}
					estadoActual = "recibiendo";
					seleccion = recibirDatos().split(";")[1];
	    			
				} else {salir();}
				
			}
			salir();
			
    	
			
		} catch (IOException e) {System.err.println("No se ha podido enviar el archivo");}
	}

	private void autenticar() {
		
		boolean exit = false;
		while (!exit ) {
			
			// Solicitar usuario
			estadoActual = "enviando";
			enviarDatos("enviando", "Ingrese usuario:");
			
			estadoActual = "recibiendo";
			String user = recibirDatos();
			
			//Solicitar contraseña
			estadoActual = "enviando";
			enviarDatos("enviando", "Ingrese contraseña:");
			
			estadoActual = "recibiendo";
			String pw = recibirDatos();
			
			System.out.println("comprobado:"+user+" y contraseña: "+pw);
			
			//Comprobar datos recibidos
			int auth = Servidor.checkAuth(user, pw, this);
			
			switch (auth) {
				// Usuario no logueado o token invalido. Pasamos a autenticar
				case 0: {
					
					estadoActual = "enviando";
					enviarDatos("recibiendo", "Usuario o contraseña incorrectos");

	                break;
	                
				}
				// Usuario ya logueado. Cerramos conexión
				case 1: {
					estadoActual = "enviando";
					enviarDatos("recibiendo", "Solo puede tener una sesión abierta");

	                exit = true;
	                salir();
	                break;
						
				}
				case 2: {

					estadoActual = "enviando";
					enviarDatos("recibiendo", "Acceso permitido");
					
					enviarDatos("token",token.toString());

					servicio();
					exit = true;
					break;
					
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + auth);
			}

		}

	}
	
	
	private int checkearEstado(String datos) {
		int status = 0;
		
		String token = datos.split(";")[0];
		
		if(token == null) status = 0;
		
		else if (this.token != null && !token.equals(this.token.toString())) {
			
			status = 0;}
		
		else if (this.token != null && token.equals(this.token.toString())) {
			System.out.println(">>TOKEN ACEPTADO");
			status = 1;}
		
		return status;
	}

	private String recibirDatos() {
		String datosCliente = null;
		
		if(estadoActual.equals("enviando")) {
			System.out.println("ERROR: Interrupcion recibiendo datos.");
		} else {
			estadoActual = estados.get(1);

			try {
				System.out.println(">>Esperando peticion del cliente...");
                while (true) {
                    
                	if (inputStream.available() > 0) {
 
                        byte[] buffer = new byte[1024];
                        int bytesRead = inputStream.read(buffer);
                        String respuesta = new String(buffer, 0, bytesRead);
        				datosCliente = respuesta;
        				
                        break;
                    } else {
                        
                    	// Dormir durante un breve período antes de volver a verificar
                        try {
							Thread.sleep(100);
						} catch (InterruptedException e) {}
                    }
                }
		         
			} catch (IOException e) {
				System.out.println("ERROR: No se ha podido conectar con el cliente.");
			}
			if(datosCliente.split(";").length < 2) {
				datosCliente = datosCliente.split(";")[0] + ";-1";
			}
			System.out.println(">>Peticion: "+datosCliente);
		}
		estadoActual = estados.get(0);
        try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
		return datosCliente;
	}
	
	
	private void enviarDatos(String modoCliente ,String mensaje) {
		if(estadoActual.equals("recibiendo")) {
			System.out.println("ERROR: Interrupcion enviando datos.");
		} else {
			
			try {
				
				estadoActual = estados.get(2);
				String datos = modoCliente+";"+mensaje;

				outputStream.write(datos.getBytes());
				
			} catch (IOException e) {
				System.out.println("ERROR: No se ha podido conectar con el cliente.");
				e.printStackTrace();
			}
		}
		estadoActual = estados.get(0);
        try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}

	}
	
	public void setToken(String token) {

		this.token = token;
	}

	public void setUser(String user) {
		this.user = user;
		
	}

	public String getUser() {

		return this.user;
	}
	
}
