package main;

public class aleatorios {

	public static void main(String[] args) {
		
		int rounds = (int)(Math.random()*100);
		for (int i = 0; i <= rounds; i++) {
			int numExt = (int)(Math.random()*10)+1;
			int ext = (int) Math.pow(10, numExt);
			int num = (int)(Math.random()*ext);
		System.out.print(num+" ");
		}

	}

}

