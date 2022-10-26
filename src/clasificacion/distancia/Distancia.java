package clasificacion.distancia;
import datos.*;
import java.util.ArrayList;
import java.util.Arrays;
//import java.lang.Math;

public interface Distancia{
	abstract String getNombre();
	abstract double calcularDistancia(Instancia inst_saved, Instancia ins_toknow,
			ArrayList<Double> ponderations);
	public ArrayList<Double> pesoAtrib = new ArrayList<>();
	static double CTE_PESO = 1.0;
}