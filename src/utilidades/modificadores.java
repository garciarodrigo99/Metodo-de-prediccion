package utilidades;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import datos.*;
import datos.atributo.A_categorico;
import datos.atributo.A_numerico;
import datos.atributo.Atributo;

public class modificadores {
	
	public static final DecimalFormat decform = new DecimalFormat("0.00");
	
	public static ArrayList<Double> multiplyAllMembers(ArrayList<Double> vector, double multiplicador){
		ArrayList<Double> aux = new ArrayList<Double>(copiaCruda(vector));
		for(int i=0; i<aux.size();i++) {
			aux.set(i, aux.get(i)*multiplicador);
		}
		return aux;
	}
	
	public static ArrayList<Double> divideAllMembers(ArrayList<Double> vector, double multiplicador){
		assert multiplicador!=0.0;
		return multiplyAllMembers(vector,(1.0/multiplicador));
	}
	
	public static ArrayList<Double> lowestValues(ArrayList<Double> vector, int number){
		ArrayList<Double> lw_val = new ArrayList<Double>();
		ArrayList<Double> vector_copia = new ArrayList<Double>(copiaCruda(vector));
		Collections.sort(vector_copia);
		for(int i=0;i<number;i++) {
			lw_val.add(vector_copia.get(i));
		}
		return lw_val;
	}
	
	public static <E> ArrayList<E> copiaCruda(ArrayList<E> vector){
		ArrayList<E> aux = new ArrayList<E>();
		for(int i=0; i<vector.size();i++) {
			aux.add(vector.get(i));
		}
		return aux;
	}
	
	/*
	 * Como no se puede sobreescribir copiaCruda<E> y especificar que cuando 
	 * sea ArrayList<Atributo> se escoge este método, he creado este método
	 * para poder hacer copias de ArrayList<Atributo>.
	 */
	public static ArrayList<Atributo> copiaCrudaArAtrib(ArrayList<Atributo> vector){
		ArrayList<Atributo> aux = new ArrayList<Atributo>();
		for(int i=0; i<vector.size()-1;i++) {
			aux.add(((A_numerico)vector.get(i)).getCopiaCruda());
		}
		aux.add(((A_categorico)vector.get(vector.size()-1)).getCopiaCruda());
		return aux;
	}
	
	/*
	 * Devuelve un vector de Atributos que se le pasa por parametro con la nueva instancia ya insertada
	 */
	public static ArrayList<Atributo> addInstancia(ArrayList<Atributo> atributos, Instancia inst){
		ArrayList<Atributo> atrib_aux = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(atributos));
		Instancia inst_aux = new Instancia(inst); // Copia cruda en el constructor
		for(int i=0; i<atrib_aux.size()-1;i++) {
			((A_numerico)atrib_aux.get(i)).insert(inst_aux.getAtNum().at(i));
		}
		((A_categorico)atrib_aux.get(atrib_aux.size()-1)).insert(inst_aux.getAtCat().at(0));
		return atrib_aux;
	}
	
	/*
	 * Devuelve un vector de Atributos que se le pasa por parametro con una serie de instancias ya insertadas
	 */
	public static ArrayList<Atributo> addInstancia(ArrayList<Atributo> atributos, ArrayList<Instancia> inst){
		ArrayList<Atributo> atrib_aux = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(atributos));
		for(int i=0; i<inst.size();i++) {
			atrib_aux = addInstancia(atrib_aux,inst.get(i));//Codigo reutilizado
		}
		return atrib_aux;
	}
	
	public static ArrayList<Atributo> EliminarInstancia(ArrayList<Atributo> vector, int index){
		assert vector.get(0).getSize()<index;
		ArrayList<Atributo> aux = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(vector));
		for(int i=0; i<aux.size()-1;i++) {
			((A_numerico)aux.get(i)).eliminarElemento(index);
		}
		((A_categorico)aux.get(aux.size()-1)).eliminarElemento(index);
		return aux;
		
	}
	
	/*
	 * Querría haberlo hecho general pero sale el siguiente mensaje:
	 * //Double min = Collections.min(aux); The method min(Collection<? extends T>) in the type Collections is not applicable for the arguments (ArrayList<E>
	 * Por tanto para "optimizarlo" he hecho que la función sea con vector de dobles.
	 */
	//Erasure of method RangoZeroOne(ArrayList<Double>) is the same as another method in type modificadores
	public static ArrayList<Double> RangoZeroOneDouble(ArrayList<Double> vector){
		ArrayList<Double> aux = new ArrayList<Double>(copiaCruda(vector));
		Double min = Collections.min(aux);
		Double max = Collections.max(aux);
		for(int i=0; i<aux.size();i++) {
			aux.set(i, ((aux.get(i) - min)/(max - min)));
		}
		return aux;
	}
	
	public static ArrayList<Atributo> RangoZeroOneAtributo(ArrayList<Atributo> vector){
		ArrayList<Atributo> aux = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(vector));
		for(int i=0; i<aux.size()-1;i++) {
			((A_numerico)aux.get(i)).modify(RangoZeroOneDouble(((A_numerico)aux.get(i)).getArr()));
		}
		return aux;
	}
	
	public static ArrayList<Double> EstandarizarDouble(ArrayList<Double> vector){
		ArrayList<Double> aux = new ArrayList<Double>(copiaCruda(vector));
		Double media = matematicas.avg(aux);
		Double devt = matematicas.desvTip(aux);
		for(int i=0; i<aux.size();i++) {
			System.out.print("Media: "+media+", desv tip: "+devt+"\n");	
		}
		return aux;
	}
	
	public static ArrayList<Atributo> EstandarizarAtributo(ArrayList<Atributo> vector){
		ArrayList<Atributo> aux = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(vector));
		for(int i=0; i<aux.size()-1;i++) {
			((A_numerico)aux.get(i)).modify(EstandarizarDouble(((A_numerico)aux.get(i)).getArr()));
		}
		return aux;
	}
	

	
	public static Par<String,Double> getVotes(ArrayList<Par<String,Double>> pse, String str) {
		Par<String,Double> tipo = new Par<String,Double>(str,0.0);
		for(int i=0;i<pse.size();i++) {
			if(str.equals(pse.get(i).getElement0())) {
				tipo.set(tipo.getElement1()+pse.get(i).getElement1());
			}
		}
		return tipo;
	}
	
	/*
	 * Hallar el tipo que tiene más votos en un vector de String y un tipo
	 * de dato numérico.
	 * Problema nullpointer si lo hago con plantillas
	 */
	public static Par<String,Double> getMaxVotes(ArrayList<Par<String,Double>> pse) {
		Par<String,Double> max = new Par<String,Double>(",",0.0);
		ArrayList<String> tipos = new ArrayList<String>(modificadores.copiaCruda(getNombresUnicos(getVectorString(pse))));//Clases unicas
		for(int i=0;i<tipos.size();i++) {
			if((getVotes(pse,tipos.get(i))).getElement1()>=max.getElement1()) {
				max.set((getVotes(pse,tipos.get(i))).getElement0(), (getVotes(pse,tipos.get(i))).getElement1());
			}
		}
		return max;
	}
	
	/*
	 * Función que teniendo un par tipo y un double, y un umbral de aceptacion
	 * devuelve el tipo ganador o no clasificado.
	 * Problema nullpointer si lo hago con plantillas
	 */
	public static String getTipo(ArrayList<Par<String,Double>> psd, Double umbral) {
		Par<String,Double> tipo = new Par<String,Double>("",0.0);
		tipo.set(getMaxVotes(psd).getElement0(), getMaxVotes(psd).getElement1());//Evitar paso referencia
		Double total_votos = 0.0;
		for(int i=0;i<psd.size();i++) {
			total_votos += psd.get(i).getElement1();
		}
		if(((total_votos>0) &&
			(tipo.getElement1()/total_votos)>umbral)) {
			return tipo.getElement0();
		} else
			return "no-clasificada";
	}
	
	public static ArrayList<String> getNombresUnicos(ArrayList<String> arrs){
		ArrayList<String> unique = new ArrayList<String>();
		for(int i=0; i<arrs.size();i++) {
			if (!unique.contains(arrs.get(i)))
				unique.add(arrs.get(i));
		}
		return unique;
	}
	
	/*
	 * Obtiene un vector de strings de un vector de par string double
	 */
	public static <E> ArrayList<String> getVectorString(ArrayList<Par<String,E>> arrs){
		ArrayList<String> vector = new ArrayList<String>();
		for(int i=0; i<arrs.size();i++) {
			vector.add(arrs.get(i).getElement0());
		}
		return vector;
	}	
	
}
