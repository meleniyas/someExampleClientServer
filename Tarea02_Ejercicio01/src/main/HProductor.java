package main;

public class HProductor implements Runnable {
	
	public Buffer cola;
	
	public HProductor(Buffer cola) {
		this.cola=cola;
	};
	

	@Override
	public void run() {
		String[] opciones = {"piedra","papel","tijera"};
		
		//Generamos un numero random entre 0 y 9 y lo ponderamos de 0 a 2
		int randomIndex = (int)((Math.random()*10));
		int ponderado = randomIndex*3/10;
		
		//Imprimimos por pantalla la seleccion
		String seleccion = opciones[ponderado];
    		try {
    			cola.incluir(seleccion);
			} catch (Exception e) {System.out.println(e);}
		
	}

}
