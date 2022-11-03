package clasificacion;

import java.util.ArrayList;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

import clasificacion.distancia.Distancia;
import clasificacion.distancia.Euclidea;
import utilidades.matematicas;
import utilidades.modificadores;
import datos.*;
import datos.atributo.*;


public class Entorno {
	private ArrayList<Double> pesoAtrib = new ArrayList<>();
	private final double defaultPesoAtrib = 1.0;
	private Distancia distancia_ = new Euclidea();

	public Entorno() {}

	public String getInfo() {
		return (" Pesos atributos: "+pesoAtrib+" Métrica distancia: "+distancia_.getNombre());
	}
	
	public static String getLabels() {
		return ("P_Atrib"+","+"Distance");
	}
	
	public String getCsvValues() {
		return (pesoAtrib+","+distancia_.getNombre());
	}

	public void BuildPesoAtrib (int nAtrib) {
		for (int i  = 0; i < nAtrib; i++) {
			pesoAtrib.add(defaultPesoAtrib);
		}
	}

	public void ModifyPesoAtrib (int paramPosition, Double paramValue) {
		pesoAtrib.set(paramPosition,paramValue);
	}

	public void setPesoAtrib (ArrayList<Double> paramArrList) {
		pesoAtrib = paramArrList;
	}

	public ArrayList<Double> GetPesoAtrib () {
		return modificadores.copiaCruda(pesoAtrib);
	}

	public void SetDistanceMetric (Distancia paramDistance) {
		distancia_ = paramDistance;
	}
	
	public String getDistanceMetricName() {
		return distancia_.getNombre();
	}

	public ArrayList<ParID> getKNearestNeighbours(ArrayList<Atributo> param, int k, Instancia inst) {
		assert param.size()<=k;
		ArrayList<ParID> distancias = new ArrayList<ParID>();
		Instancia inst_aux = new Instancia(inst);
		ArrayList<Atributo> arr_atrib_aux = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(param));
	
		// 0 por poner un número (siempre debería haber al menos 1) para recorrer el bucle ya que no tenemos numero de filas aqui.
		for(int i=0;i<arr_atrib_aux.get(0).getSize();i++) {
			distancias.add(new ParID(Instancia.getInstancia(arr_atrib_aux, i), 
					distancia_.calcularDistancia(Instancia.getInstancia(arr_atrib_aux, i),
					inst_aux,pesoAtrib)));
		}
//		for(int i=0;i<distancias.size();i++) {
//			System.out.print("("+(i+1)+")");
//			distancias.get(i).print();
//			System.out.print("\n");
//		}
		//Ordenar el vector de par por la distancia
		Collections.sort(distancias, new Comparator<ParID>() {
            public int compare(ParID p1, ParID p2) {
                    return p1.getDistancia().compareTo(p2.getDistancia());
             }
		});
		//Descomentar para imprimir todos las instancias por orden
//		for(int i=0;i<distancias.size();i++) {
//			distancias.get(i).print();
//			System.out.print("\n");
//		}
		ArrayList<ParID> vecinos_cercanos = new ArrayList<ParID>();
		for(int i=0;i<k;i++) {
			vecinos_cercanos.add(ParID.copiaCruda(distancias.get(i)));
		}
//		for(int i=0;i<vecinos_cercanos.size();i++) {
//			vecinos_cercanos.get(i).print();
//			System.out.print("\n");
//		}
		return vecinos_cercanos;
	}
	
	public ArrayList<ParID> getKNN2(ArrayList<Atributo> param, int k, Instancia inst) {
		assert param.size()<=k;
		ArrayList<ParID> auxID = new ArrayList<ParID>();
		Instancia inst_aux = new Instancia(inst);
		ArrayList<Atributo> arr_atrib_aux = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(param));
		
		// Inicializacion vector
		for(int i=0;i<k;i++) {
			auxID.add(new ParID(Instancia.getInstancia(arr_atrib_aux, i), 
					distancia_.calcularDistancia(Instancia.getInstancia(arr_atrib_aux, i),
					inst_aux,pesoAtrib)));
		}

		// Swap
		int maxIndex = modificadores.maxValueID(auxID);
		for(int i=k;i<arr_atrib_aux.get(0).getSize();i++) {
			Double auxDistance = distancia_.calcularDistancia(Instancia.getInstancia(arr_atrib_aux, i), inst_aux,pesoAtrib);
			if (auxDistance < auxID.get(maxIndex).getDistancia()) {
				auxID.set(maxIndex, 
					new ParID(Instancia.getInstancia(arr_atrib_aux, i),
							auxDistance));
				maxIndex = modificadores.maxValueID(auxID); 
			}
		}

		// Ordenar vector knn
		Collections.sort(auxID, new Comparator<ParID>() {
            public int compare(ParID p1, ParID p2) {
                    return p1.getDistancia().compareTo(p2.getDistancia());
             }
		});

		// Imprimir
//		for(int i=0;i<auxID.size();i++) {
//			auxID.get(i).print();
//			System.out.print("\n");
//		}

		return auxID;
	}
}