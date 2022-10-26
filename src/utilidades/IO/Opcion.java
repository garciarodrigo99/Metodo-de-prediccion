package utilidades.IO;

import java.util.Scanner;

public class Opcion {
	
	public static int getOpcionInt(){
		System.out.print("Introduzca una opción: ");
		Scanner in = new Scanner(System.in);
		int option = in.nextInt();
		System.out.print("\n");
		return option;
	}
	
	public static int getOpcionInt(String texto){
		System.out.print(texto);
		Scanner in = new Scanner(System.in);
		int option = in.nextInt();
		System.out.print("\n");
		return option;
	}
	
	public static Double getOpcionDouble(){
		System.out.print("Introduzca una opción: ");
		Scanner in = new Scanner(System.in);
		Double option = in.nextDouble();
		System.out.print("\n");
		return option;
	}
	
	public static Double getOpcionDouble(String str){
		System.out.print(str);
		Scanner in = new Scanner(System.in);
		Double option = in.nextDouble();
		System.out.print("\n");
		return option;
	}
	
	public static String getOpcionString(){
		System.out.print("Introduzca umbral de aceptación (0-1,dos decimales): ");
		Scanner read = new Scanner(System.in);
		String enterkey = read.nextLine();
		System.out.print("\n");
		return enterkey;
	}
	
	public static String getOpcionString(String str){
		System.out.print(str);
		Scanner read = new Scanner(System.in);
		String enterkey = read.nextLine();
		System.out.print("\n");
		return enterkey;
	}
	
	public static void optNoValid() {
		System.out.print("Opción no vaĺida\n");
	}
}
