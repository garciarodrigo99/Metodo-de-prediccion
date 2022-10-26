package datos.atributo;

//import java.text.DecimalFormat;
import java.util.*;
import java.text.DecimalFormat;
import utilidades.matematicas;
import utilidades.modificadores;

public class A_categorico implements Atributo{
	protected ArrayList<String> lista;
	protected String nombre = "";
	
	/**
	 * Constructor por defecto(vacío/a 0)
	 */
	public A_categorico () {
		lista = new ArrayList<String>();
	}
	
	/**
	 * Constructor por defecto que solo indica el nombre del vector.
	 * @param String Nombre del vector
	 */
	public A_categorico (String nombre_param) {
		this();
		nombre = nombre_param;
	}
	
	/**
	 * Constructor de copia que recibe otro atributo categórico por parámetro
	 * @param A_categorico A_categorico de copia
	 */
	public A_categorico(A_categorico copia) {
		copiarAtributo(copia);
	}
	
	/**
	 * Constructor que recibe un vector de cadenas y lo copia al atributo 
	 * privado lista (copia cruda) y una cadena que se establecerá como el 
	 * nombre.
	 * @param ArrayListString Vector de cadenas
	 * @param String Nombre del vector
	 */
	public A_categorico(ArrayList<String> str, String name) {
		lista = modificadores.copiaCruda(str);
	}
	
	/*
	 * La llamada a este método iguala los valores de la referencia a los propios
	 */
	public void copiarAtributo(A_categorico copia) {
		lista = modificadores.copiaCruda(copia.lista);
		nombre = copia.nombre;
	}
	
	/*
	 * Getter(copia cruda) del atributo categorico
	 */
	public A_categorico getCopiaCruda() {
		return (new A_categorico(modificadores.copiaCruda(lista),nombre));
	}
	
	/**
	 * Insertar una nueva cadena al vector de cadenas
	 * @param String String a insertar
	 */
	public void insert(String to_insert) {
		lista.add(to_insert);
	}
	
	@Override
	public void eliminarElemento(int index) {
		lista.remove(index);
	}
	
	/**
	 * Getter de una posición pasado un índice
	 * @param int Indice
	 * @return String Cadena a devolver
	 */
	public String at(int index) {
		assert index < lista.size();
		return lista.get(index);
	}
	
	@Override
	public int getSize() {
		return lista.size();
	}
	
	@Override
	public void print() {
		System.out.print(lista+"\n");
	}
	
	/**
	 * Método que devuelve un vector de cadenas con las cadenas (tipos) únicas
	 * @return ArrayListString Vector de cadenas únicas
	 */
	public ArrayList<String> getNombresUnicos(){
		return modificadores.getNombresUnicos(modificadores.copiaCruda(lista));
	}
	
	/**
	 * @return int Número de cadenas únicas
	 */
	public int getNValUnique() {
		return getNombresUnicos().size();
	}
	
	/**
	 * Método que devuelve la frencuencia relativa de una cadena del vector de cadenas.
	 * Se externaliza llamando a un método estático que recibe un vector de strings
	 * @param String Cadena(tipo) a obtener su frecuencia relativa
	 * @return Double Frecuencia relativa de la cadena(tipo) pasada por parámetro
	 */
	public double getFRValor(String clase) {
		return matematicas.freqRelativa(modificadores.copiaCruda(lista), clase);
	}
	
	/*
	 * Método que imprime todas la frecuencia relativa de todos las cadenas(tipos).
	 * Solo se utiliza llamandolo desde el método getInfo
	 */
	private void printFRTotal() {
		DecimalFormat df = new DecimalFormat("0.00"); //Formato solo dos decimales(solo al mostrar por pantalla, no se modifica valor original)
		for(int i=0;i<getNombresUnicos().size();i++){
			System.out.print("Frecuencia relativa de "+getNombresUnicos().get(i)+": "+df.format(getFRValor(getNombresUnicos().get(i)))+"\n");
		}
		System.out.print("\n");
	}
	
	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public void getInfo() {
		System.out.print("-----Información Atributo Categórico-----"+"\n");
		System.out.print("Nombre: "+nombre+"\n");
		System.out.print("Número de clases distintas: "+getNValUnique()+"\n");
		System.out.print("Valores distintos de la muestra: "+getNombresUnicos()+"\n");
		System.out.print("Frecuencias relativas: "+"\n");
		printFRTotal();
	}
}
