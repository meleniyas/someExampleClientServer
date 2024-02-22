package main;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.BufferedReader;

public class Apartado_B {

	 /*****************************************************************
	  * Programa Juego 2 personas
	  */
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("¿Preparados?");
		
		String respuesta = sc.nextLine();
		
		if(respuesta.equals("Si")||respuesta.equals("si")) {
			System.out.println("Jugador 1:");
			jugar();
			System.out.println("Jugador 2:");
			jugar();

		}
		System.out.println("Fin del juego");
		sc.close();
	}
		
	private static void jugar() {
		String[] cmd = {"java", "-jar", "Apartado_A.jar"};
		try {
			Runtime rt  =Runtime.getRuntime();
			Process proc = rt.exec(cmd);
			
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			System.out.print(stdInput.readLine()+"\n");
		} catch (IOException e) {System.out.println(e);}
		
	}

}
