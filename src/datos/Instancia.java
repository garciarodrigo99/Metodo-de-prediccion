package datos;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import datos.atributo.A_categorico;
import datos.atributo.A_numerico;
import datos.atributo.Atributo;
import utilidades.modificadores;
import utilidades.IO.Opcion;


public class Instancia{
	
	protected A_numerico array_num;
	/*
	 * Se aprovecha la clase atributo categorico para una posible modificacion de instancia
	 * en este programa siempre tendrá tamaño uno
	 */
	protected A_categorico atrib_cat;

	/*
	 * Constructor por defecto, arrays tamaño 0
	 */
	public Instancia() {
		array_num = new A_numerico();
		atrib_cat = new A_categorico();
	}
	
	/**
	 * Constructor que recibe un vector de dobles para ser asignados
	 * como los miembros de atributo numerico y un string indicando el tipo
	 * que se guardará en la posición 0 del array categorico
	 * @param ArrayListDouble Vector de dobles
	 * @param String Cadena indicando el tipo
	 */
	public Instancia(ArrayList<Double> num_param, String cat) {
		array_num = new A_numerico(modificadores.copiaCruda(num_param));
		atrib_cat = new A_categorico();
		atrib_cat.insert(cat);
	}
	
	/**
	 * Constructor que recibe un array de dobles y establece un tipo por defecto "" (nada)
	 * @param ArrayListDouble vector de dobles
	 */
	public Instancia(ArrayList<Double> num_param) {
		this(modificadores.copiaCruda(num_param),"");
	}
	
	/**
	 * Constructor que recibe un objeto A_numerico y otro objeto A_categorico
	 * que se igualarán a los atributos
	 * @param A_numerico Atributo numerico a igualar atributo privado array_num
	 * @param A_categorico Atributo categorico a igualar atributo privado atrib_cat
	 */
	public Instancia(A_numerico numericos, A_categorico categorico) {
		this();
		array_num.copiarAtributo(numericos);
		atrib_cat.copiarAtributo(categorico);
	}
	
	/**
	 * Constructor que recibe una instancia como parámetro a la cuál se igualará
	 * @param Instancia Instancia a igualar
	 */
	public Instancia(Instancia inst) {
		this(inst.getAtNum().getCopiaCruda(),inst.getAtCat().getCopiaCruda());
	}
	
	/**
	 * Método estático que devuelve la copia cruda de una instancia.
	 * Se creó para trabajar con instancias que cuando se pasen cómo parámetros
	 * no actuen como instancias por referencia si no como copias crudas.
	 * @param Instancia Instancia a igualar
	 * @return Instancia Copia cruda de la instancia pasada por parámetro
	 */
	public static Instancia copiaInstancia(Instancia inst){
		Instancia aux = new Instancia(inst);
		return aux;
	}
	
	/**
	 * Método estático que recibe un vector de atributos y un índice y devuelve la instancia
	 * @param ArrayListAtributo Vector de atributos(matriz)
	 * @param int Instanciaa(fila de la matriz) a obtener
	 * @return Instancia Instancia devuelta de la matriz
	 */
	public static Instancia getInstancia(ArrayList<Atributo> arr_atrib,int index) {
		ArrayList<Atributo> aux = modificadores.copiaCrudaArAtrib(arr_atrib);
		Instancia inst = new Instancia();
		for(int i=0; i<(aux.size()-1);i++) {
			A_numerico atn = (A_numerico) aux.get(i);
			inst.getAtNumRef().insert(atn.at(index));
		}
		A_categorico atc = (A_categorico) aux.get(aux.size() - 1);
		inst.getAtCatRef().insert(atc.at(index));
		return inst;
	}
	
	/**
	 * Como en el apartado anterior estaba llamando a getAtNum no lo pasaba
	 * por referencia, lo pasaba por copia cruda por lo que no podía modificar
	 * los valores en la instancia y saltaban varias problemas.
	 * Getter de atributo numerico por referencia
	 * @param void
	 * @return A_numerico Atributo privado A_numerico
	 */
	private A_numerico getAtNumRef(){
		return array_num;
	}
	
	/**
	 * Como en el apartado anterior estaba llamando a getAtNum no lo pasaba
	 * por referencia, lo pasaba por copia cruda por lo que no podía modificar
	 * los valores en la instancia y saltaban varias problemas.
	 * Getter de atributo categorico por referencia
	 * @param void
	 * @return A_numerico Atributo privado A_categorico
	 */
	private A_categorico getAtCatRef(){
		return atrib_cat;
	}
	
	/**
	 * Getter de atributo privado A_numerico array_num que devuelve la copia cruda de dicho atributo privado
	 * @return A_numerico Atributo privado A_numerico array_num
	 */
	public A_numerico getAtNum(){
		return array_num.getCopiaCruda();
	}
	
	/**
	 * Getter de atributo privado A_categorico atrib_cat que devuelve la copia cruda de dicho atributo privado
	 * @return A_numerico Atributo privado A_categorico atrib_cat
	 */
	public A_categorico getAtCat(){
		return atrib_cat.getCopiaCruda();
	}
	
	public int getSize() {
		return (array_num.getSize() + atrib_cat.getSize());
	}
	
	public void print() {
		DecimalFormat df = new DecimalFormat("0.000");
		for(int i=0;i<array_num.getSize();i++){
			System.out.print(df.format(array_num.at(i))+"\t");
		}
		System.out.print(atrib_cat.at(0)+"");
	}
	
	/**
	 * Método estático que lee una instancia introducida por el usuario
	 * a través de teclado 
	 * @param n 	numero de atributos númericos de la instancia 
	 * @return Instancia	instancia leída por teclado
	 */
	public static Instancia leerInstancia(int n) {//Número atributos
		ArrayList<Double> ald = new ArrayList<Double>();
		for(int j = 0; j < (n - 1); j++) {
			Double opt = -1.0;
			while(opt<0.0) {
				opt=Opcion.getOpcionDouble("Introduzca atributo númerico ["+(j+1)+"]: ");
			}
			ald.add(opt);
		}
		return Instancia.copiaInstancia(new Instancia(ald,""));
	}
	
	/**
	 * Método estático que lee una instancia introducida por el usuario
	 * a través de teclado 
	 * @param n 	numero de atributos númericos de la instancia 
	 * @param file_name Nombre de archivo CSV donde está guardada la instancia
	 * @return Instancia	instancia leída por teclado
	 */
	public static Instancia leerInstancia(int n, String file_name) {//Número atributos
		String separador = ",";
		BufferedReader br = null;
		String line = "";
		ArrayList<Double> ald = new ArrayList<Double>();
		String tipo = new String("");
		try {
			br = new BufferedReader(new FileReader(file_name));
			System.out.println("Nombre fichero: "+file_name);
			if((line = br.readLine()) != null) {
				String[] fila = line.split(separador);
				System.out.println("Tamaño fila: "+fila.length);
				for(int j=0; j<n; j++) {
					ald.add(Double.parseDouble(fila[j]));
				}
				// Para hacerlo generico, se incluye este if por si se quiere incluir una instancia ya reconocida con su tipo.
				// En caso de que no, se quedará como tipo por defecto ""
				if (fila.length==(n+1)) {
					System.out.println("Entra");
					tipo = fila[fila.length-1];
				}
			}
			
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    if (br != null) {
		        try {
		            br.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
		return Instancia.copiaInstancia(new Instancia(ald,tipo));
	}
	
}