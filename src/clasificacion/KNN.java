package clasificacion;

import java.util.*;

import clasificacion.distancia.Distancia;
import clasificacion.distancia.Euclidea;
import datos.*;
import datos.atributo.Atributo;
import utilidades.modificadores;
import utilidades.preprocesado.PreprocesadoDatos;
import utilidades.preprocesado.RangoZeroOne;

public class KNN {
	static public Integer valork = 3;
	protected Distancia distancia_ = new Euclidea();
	protected PreprocesadoDatos preprocesado = new RangoZeroOne();
	//protected int momento_ejec = 0; //Antes o después procesar los datos
	
	public KNN() {}
	
//	public KNN(int size){
//		for (int i = 0; i < size; i++) {
//			pesoAtrib.add(CTE_PESO);
//		}
//	}
	
	public KNN(Distancia d){
		distancia_ = d;
	}
	
	public KNN(PreprocesadoDatos p){
		preprocesado = p;
	}
	
	public KNN(int n, Distancia d){
		this(d);
		valork = n;
	}
	
	public KNN(int n, PreprocesadoDatos p){
		this(p);
		valork = n;
	}
	
	public KNN(Distancia d, PreprocesadoDatos p){
		this(d);
		preprocesado = p;
	}
	
	public KNN(int n, Distancia d, PreprocesadoDatos p){
		valork = n;
		distancia_ = d;
		preprocesado = p;
	}

	public int getValorK() {
		return valork;
	}
	
//	public void setValorK(int i) {
//		valork = i;
//	}
	
	public Distancia getDistancia() {
		return distancia_;
	}
	
	public void setDistancia(Distancia d) {
		distancia_ = d;
	}
	
	public PreprocesadoDatos getPreprocesadoDatos() {
		return preprocesado;
	}
	
	public void setPreprocesadoDatos(PreprocesadoDatos p) {
		preprocesado = p;
	}
	
	/*
	 * Instancia a comparar introducida por el usuario.
	 */
	//- Cannot make a static reference to the non-static field distancia_
	// Intentar crear una clase static en modificadores y llamarla desde aquí
	public ArrayList<ParID> getVecinos(ArrayList<Atributo> param, int k, Instancia inst) {
		assert param.size()<=k;
		ArrayList<ParID> distancias = new ArrayList<ParID>();
		Instancia inst_aux = new Instancia(inst);
		ArrayList<Atributo> arr_atrib_aux = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(param));

		// 0 por poner un número (siempre debería haber al menos 1) para recorrer el bucle ya que no tenemos numero de filas aqui.
		for(int i=0;i<arr_atrib_aux.get(0).getSize();i++) {
			distancias.add(new ParID(Instancia.getInstancia(arr_atrib_aux, i), 
					distancia_.calcularDistancia(Instancia.getInstancia(arr_atrib_aux, i), inst_aux)));
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
		return vecinos_cercanos;
	}
	
	
	public ArrayList<ParID> getVecinos(ArrayList<Atributo> param,Instancia inst) {
		return getVecinos(modificadores.copiaCrudaArAtrib(param),valork,Instancia.copiaInstancia(inst));
	}
	
	public void getInfo() {
		System.out.print("Número de vecinos (valor k): "+valork);
		System.out.print("\tMétrica utilizada(distancia): "+distancia_.getNombre());
		System.out.print("\tPreprocesado de datos: "+preprocesado.getNombre()+"\n");
	}
	
}
