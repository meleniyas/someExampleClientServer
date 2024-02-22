package main;

import java.io.IOException;

public class colaborar {

	public static void main(String[] args) {
		
		if(args.length==1) {			
		
			for (int i = 1; i <= 10; i++) {
				System.out.println("Arrancando proceso "+i);
				int lineas = (10*i);
				String[] cmd = {"java", "-jar", "lenguaje.jar", String.valueOf(lineas), args[0]};
				try {
					Runtime.getRuntime().exec(cmd);
				} catch (IOException e) {
					System.out.println(e);
				}
			}

		}else {System.out.println("Debe introducir un nombre de archivo");};
		System.out.println(">>>> Se ha completado la ejecucion");
	}
}
