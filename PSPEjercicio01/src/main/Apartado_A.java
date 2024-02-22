package main;

public class Apartado_A {

	 /*****************************************************************
	  * Programa piedra, papel o tijeras. Aleatoriamente
	 * @return 
	  */
	public static void main(String[] args) {
		// Preparamos un array con las opciones
		String[] opciones = {"piedra","papel","tijera"};
		
		//Generamos un numero random entre 0 y 9 y lo ponderamos de 0 a 2
		int randomIndex = (int)((Math.random()*10));
		int ponderado = randomIndex*3/10;
		
		//Imprimimos por pantalla la seleccion
		String seleccion = opciones[ponderado];
		System.out.print(seleccion);
		
	}

}
