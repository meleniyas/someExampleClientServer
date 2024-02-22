package main;

import java.io.FileWriter;
import java.io.BufferedWriter;

public class lenguaje {
	
	public static void main(String[] args) {
		
		
		// Comprobamos que recibimos dos argumentos
		if (args.length!=2) {
			System.out.println("La estructura no es correcta: java -jar lenguaje <numeroPalabras> <nombreFichero>");
			System.exit(1);
		}
		Integer numPalabras = null;

		// Comprobamos que ingresamos un numero correcto
		try {
			numPalabras = Integer.parseInt(args[0]);
		} catch (Exception e) {
			System.out.println("Introduzca un numero de palabras valido");
			System.exit(1);
		}

		String dir = args[1];
		//System.out.println(numPalabras);
		//System.out.println(dir);
		

		for (int i = 0; i < numPalabras; i++) {
			String palabra = creaPalabra();
			try {
				
				FileWriter fw = new FileWriter(dir,true);
				BufferedWriter bw = new BufferedWriter(fw);

	            bw.write(palabra+"\n");

				bw.close();
				fw.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			
		}

		
}
	
	private static String creaPalabra() {
		char[] vocales = {'a','e','i','o','u'};
		char[] consonantes = {'b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t','v','w','x','y','z'};
		String palabra = "";
		int letras = (int)(Math.random()*10)+2;
		//System.out.println(letras);
		for (int i = 0; i < letras; i++) {
			int selectorV = (int)(Math.random()*vocales.length);
			int selectorC = (int)(Math.random()*consonantes.length);
			if(i%2==0) {palabra=palabra+vocales[selectorV];}
			else {palabra=palabra+consonantes[selectorC];};
			
		}
		return palabra;	
	};
	

}

