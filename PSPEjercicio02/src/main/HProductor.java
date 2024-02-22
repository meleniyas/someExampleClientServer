package main;

public class HProductor implements Runnable{
	
	public Buffer cola;
	
	public HProductor(Buffer cola) {
		this.cola=cola;
	};
	

	@Override
	public void run() {
		
		// Preparamos un array con las opciones
		String[] opciones = {"piedra","papel","tijera"};
		
		// Generamos un bucle infinito de produccion
		while(true) {
			//Generamos un numero random entre 0 y 9 y lo ponderamos de 0 a 2
			int randomIndex = (int)((Math.random()*10));
			int ponderado = randomIndex*3/10;
			
			//Seleccionamos la opcion
			String seleccion = opciones[ponderado];
			
			try {
				cola.incluir(seleccion);
				Thread.sleep(200);
			} catch (Exception e) {System.out.println(e);}
			
		}		
		
	}

}
