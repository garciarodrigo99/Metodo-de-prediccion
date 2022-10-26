package clasificacion.pesadocasos;

import java.util.*;

import utilidades.Par;
import clasificacion.ParID;

/*
 * Interfaz que se utiliza para establecer, una vez se recibe un vector 
 * ordenado por la distancia de cada clase, el número de votos que aporta
 * cada posición del vector recibido por parametro.
 */
public interface PesadoCasos {
	abstract ArrayList<Par<String,Double>> getVotos(ArrayList<ParID> parid);
	abstract String getNombre();
}