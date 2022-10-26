package datos.atributo;
import java.util.*;

import algorithm_statics.algorithmspeedtester;
import utilidades.matematicas;
import utilidades.modificadores;

/**
 * Clase atributo numérico que implementa la interfaz Atributo.
 * Tiene un vector de dobles y una cadena nombre como atributos privados.
 */
public class A_numerico implements Atributo{
	protected ArrayList<Double> lista = new ArrayList<Double>(); //= new ArrayList<Double>();
	protected String nombre = "";
	
	/*
	 * Constructor por defecto(vacío/a 0)
	 */
	public A_numerico() {}
	
	/**
	 * Constructor que recibe un vector de dobles y lo copia al atributo 
	 * privado lista (copia cruda).
	 * @param arrn_param Vector de dobles
	 */
	public A_numerico(ArrayList<Double> arrn_param) {
		lista = modificadores.copiaCruda(arrn_param);
	}
	
	/**
	 * Constructor que recibe un vector de dobles y lo copia al atributo 
	 * privado lista (copia cruda) y una cadena que se establecerá como el 
	 * nombre.
	 * @param ArrayListDouble Vector de dobles
	 * @param String Nombre del vector
	 */
	public A_numerico(ArrayList<Double> arrn_param, String nombre_param) {
		this(modificadores.copiaCruda(arrn_param));
		nombre = nombre_param;
	}
	
	/**
	 * Constructor por defecto que solo indica el nombre del vector.
	 * @param String Nombre del vector
	 */
	public A_numerico(String nombre_param) {
		nombre = nombre_param;
	}
	
	/**
	 * Constructor de copia que recibe otro atributo numérico por parámetro
	 * @param A_numerico A_numerico de copia
	 */
	public A_numerico(A_numerico copia) {
		copiarAtributo(copia);
	}
	
	/**
	 * Insertar un nuevo doble al vector de dobles
	 * @param Double Doble a insertar
	 */
	public void insert(Double to_insert) {
		lista.add(to_insert);
	}
	
	@Override
	public void eliminarElemento(int index) {
		lista.remove(index);
	}
	
	/**
	 * @return ArrayListDouble Copia cruda del vector de dobles
	 */
	public ArrayList<Double> getArr(){
		//return modificadores.copiaCruda(lista);
		return lista;
	}
	
	/**
	 * Getter de una posición pasado un índice
	 * @param int Indice
	 * @return Double Doble a devolver
	 */
	public Double at(int index) {
		assert index < lista.size();
		return lista.get(index);
	}
	
	 /**
	  * Establece como nuevo vector de dobles el pasado por parámetro
	  * @param ArrayListDouble ArrayList<Double> a copiar
	  */
	public void modify(ArrayList<Double> copia) {
		lista = modificadores.copiaCruda(copia);
	}
	
	/**
	 * La instancia que llama a este método iguala los valores de la referencia a los propios
	 * @param A_numerico Atributo numerico a igualar
	 */
	public void copiarAtributo(A_numerico copia) {
		lista = modificadores.copiaCruda(copia.lista);
		nombre = copia.nombre;
	}
	
	/*
	 * Getter(copia cruda) del atributo numerico
	 */
	public A_numerico getCopiaCruda() {
		return (new A_numerico(modificadores.copiaCruda(lista),nombre));
	}
	
	@Override
	public int getSize() {
		return lista.size();
	}
	
	@Override
	public void print() {
		System.out.print(lista+"\n");
	}
	
	@Override
	public String getNombre() {
		return nombre;
	}
	
	@Override
	public void getInfo() {
		System.out.print("-----Información Atributo Numérico-----"+"\n");
		System.out.print("Nombre: "+nombre+"\n");
		System.out.print("Valor minimo: "+matematicas.min(lista)+"\n");
		System.out.print("Valor máximo: "+matematicas.max(lista)+"\n");
		System.out.print("Media de los valores: "+matematicas.avg(lista)+"\n\n");
		System.out.print("Varianza de la muestra: "+matematicas.varianza(lista)+"\n");
		System.out.print("Desviación típica de la muestra: "+matematicas.desvTip(lista)+"\n\n");
		System.out.print("Varianza(old) de la muestra: "+matematicas.oldvarianza(lista)+"\n");
		System.out.print("Desviación típica de la muestra(old): "+matematicas.olddesvTip(lista)+"\n\n");
	}
}
