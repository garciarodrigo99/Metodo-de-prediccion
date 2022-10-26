package utilidades;
import java.util.*;
import datos.Instancia;

public final class matematicas {
	
	public static double infinito = Double.POSITIVE_INFINITY;

	public static double min(ArrayList<Double> vector) {
		return Collections.min(vector);
	}

	public static double max(ArrayList<Double> vector) {
		return Collections.max(vector);
	}

	public static double sum(ArrayList<Double> vector) {
		double suma = 0.0;
		for(int i=0;i<vector.size();i++) {
			suma += vector.get(i);
		}
		return suma;
	}
	
	public static double avg(ArrayList<Double> vector) {
		return (sum(vector)/vector.size());
	}
	
	public static double varianza(ArrayList<Double> vector) {
		double dt = 0.0;
		double mean = avg(vector);
		for(int i=0;i<vector.size();i++) {
			dt += Math.pow((vector.get(i)) - mean, 2);
		}
		dt /= vector.size();
		return dt;
	}
	
	public static double oldvarianza(ArrayList<Double> vector) {
		double dt = 0.0;
		for(int i=0;i<vector.size();i++) {
			dt += Math.pow((vector.get(i)), 2);
		}
		return ((dt / vector.size()) - Math.pow(avg(vector), 2));
	}
	
	public static double desvTip(ArrayList<Double> vector) {
		return Math.sqrt(varianza(vector));
	}
	
	public static double olddesvTip(ArrayList<Double> vector) {
		return Math.sqrt(oldvarianza(vector));
	}
	
	public static double freqRelativa(ArrayList<String> vector, String clase) {
		return (Double.valueOf(freqAbsoluta(vector,clase))/vector.size());
	}
	
	public static int freqAbsoluta(ArrayList<String> vector, String clase) {
		assert vector.contains(clase);
		int cont = 0;
		for(int i=0;i<vector.size();i++) {
			if(clase.equals(vector.get(i)))
				cont++;
		}
		return cont;
	}
	
	/*
	 * Se ha implementado este método porque se podría querer obtener la
	 * distancia Euclidea entre dos puntosfuera de la clase Euclidea sin 
	 * instanciarla
	 */
	public static double Euclidea(Double x, Double y) {
		return Math.pow((x - y), 2);
	}
	
	/*
	 * Se ha implementado este método porque se podría querer obtener la
	 * distancia Euclidea entre dos vectores de puntos fuera de la clase 
	 * Euclidea sin instanciarla
	 */
	public static double Euclidea(ArrayList<Double> vector1, ArrayList<Double> vector2) {
		assert vector1.size() == vector2.size();
		double dist = 0.0;
		for(int i=0; i<vector1.size();i++) {
			dist += matematicas.Euclidea(vector1.get(i), vector2.get(i));
		}
		return dist;
	}
	
	
	/*
	 * Metodo sobrecargado para poder calcular la distancia Euclidea 
	 * directamente de dos instancias
	 */
	public static double Euclidea(Instancia inst1, Instancia inst2) {
		return matematicas.Euclidea(modificadores.copiaCruda(inst1.getAtNum().getArr()), modificadores.copiaCruda(inst2.getAtNum().getArr()));
	}
	
	/*
	 * Se ha implementado este método porque se podría querer obtener la
	 * distancia Manhattan entre dos vectores de puntos fuera de la clase 
	 * Manhattan sin instanciarla
	 */
	public static double Manhattan(ArrayList<Double> vector1, ArrayList<Double> vector2) {
		assert vector1.size() == vector2.size();
		double dist = 0.0;
		for(int i=0; i<vector1.size();i++) {
			dist += Math.abs(vector1.get(i) - vector2.get(i));
		}
		return dist;
	}
	
	/*
	 * Metodo sobrecargado para poder calcular la distancia Manhattan 
	 * directamente de dos instancias
	 */
	public static double Manhattan(Instancia inst1, Instancia inst2) {
		return matematicas.Manhattan(modificadores.copiaCruda(inst1.getAtNum().getArr()), modificadores.copiaCruda(inst2.getAtNum().getArr()));
	}
	
	public static double Chebychef(ArrayList<Double> vector1, ArrayList<Double> vector2) {
		assert vector1.size() == vector2.size();
		double dist = 0.0;
		for(int i=0; i<vector1.size();i++) {
			if(Math.abs(vector1.get(i) - vector2.get(i))>dist)
				dist = Math.abs(vector1.get(i) - vector2.get(i));
		}
		return dist;
	}
	
	/*
	 * Metodo sobrecargado para poder calcular la distancia Manhattan 
	 * directamente de dos instancias
	 */
	public static double Chebychef(Instancia inst1, Instancia inst2) {
		return matematicas.Chebychef(modificadores.copiaCruda(inst1.getAtNum().getArr()), modificadores.copiaCruda(inst2.getAtNum().getArr()));
	}
	
}
