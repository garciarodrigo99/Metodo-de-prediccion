package utilidades.IO;
import java.util.Scanner;

public class Fichero {
	public static String nombreFich() {
		System.out.print("Introduzca el nombre del fichero: ");
		Scanner read = new Scanner(System.in);
		String file_name = read.next();
		//read.close();
		return file_name;
	}
}
