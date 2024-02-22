package main;
import java.util.ArrayList;
import java.util.Scanner;


public class ordenarNumeros {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ArrayList<Integer> numList = new ArrayList<>();

		String data = sc.nextLine();
		String[] dataSplited = data.split(" ");

		for (String number : dataSplited) {
			try {
				int num = Integer.parseInt(number);
				numList.add(num);
			} catch (Exception e) {}
		}
		numList.sort(null);
		System.out.println(numList);
		sc.close();
	}
}
