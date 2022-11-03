package datos;
import java.util.*;

import datos.atributo.A_categorico;
import datos.atributo.A_numerico;
import datos.atributo.Atributo;
import utilidades.*;
import utilidades.preprocesado.PreprocesadoDatos;
import utilidades.preprocesado.RangoZeroOne;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Dataset {
	protected int numFil;
	protected int numCol;
	protected PreprocesadoDatos preproc = new RangoZeroOne();
	protected ArrayList<Atributo> matriz = new ArrayList<>();
	
	public Dataset(String file_name) {
		String separador = ",";
		BufferedReader br = null;
		String line = "";
		
		try {
			br = new BufferedReader(new FileReader(file_name));
			/*Lee la primera fila y guarda el nombre de los atributos numericos
			y categorico. Formato n-1 columnas se corresponden con atributos 
			numericos y la columna numero n se corresponde con el atributo 
			categorico*/
			if ((line = br.readLine()) != null) {
				String[] fila = line.split(separador);
				for(int i = 0; i < (fila.length - 1); i++) {
					matriz.add(new A_numerico(fila[i]));
				}
				matriz.add(new A_categorico(fila[fila.length - 1]));
			}
			numCol = matriz.size();//Una vez inicializada la matriz, se asigna el numero su tamaño con el numero de columnas
			while ((line = br.readLine()) != null) {
				String[] fila = line.split(separador);
				for(int j = 0; j < (fila.length - 1); j++) {
					A_numerico atn = (A_numerico) matriz.get(j);	// Cast necesario para poder modificar la matriz de atributos
					atn.insert(Double.parseDouble(fila[j]));		//
				}
				A_categorico atc = (A_categorico) matriz.get(matriz.size() - 1);
				atc.insert(fila[fila.length - 1]);
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
		/* Se presume que la matriz es cuadrada, por lo que el tamaño de
		cualquier atributo(columna) será el número de filas de la matriz*/
		numFil = matriz.get(0).getSize();
		
	}

	/*
	 * Constructor vacío por defecto
	 */
	public Dataset(){}
	
	/**
	 * Constructor de copia a partir de otro dataset(copia cruda)
	 * @param ds_param Dataset por parametro
	 */
	public Dataset(Dataset ds_param) {
		this.matriz = modificadores.copiaCrudaArAtrib(ds_param.getMatriz());
		this.numCol = ds_param.getNumCol();
		this.numFil = ds_param.getNumFil();
		this.preproc = ds_param.getDataPreprocessingMetric();
	}
	
	public int getNumFil() {
		return numFil;
	}
	
	public int getNumCol() {
		return numCol;
	}

	public PreprocesadoDatos getDataPreprocessingMetric() {
		return preproc;
	}

	public void setDataPreprocessingMetric(PreprocesadoDatos paramPreProc) {
		preproc = paramPreProc;
	}

	/**
	 * Getter de la matriz por referencia
	 * @return ArrayListAtributos Matriz de atributos
	 */
	public ArrayList<Atributo> getMatriz(){
		return matriz;
	}
	
	/**
	 * Getter de un atributo (columna) de la matriz. (Referencia)
	 * @param int Índice
	 * @return Atributo Atributo de la matriz
	 */
	public Atributo at(int index){
		return matriz.get(index);
	}
	
	/**
	 * Getter de una instancia de la matriz.
	 * Llama a un método estático de la clase Instancia al que se le pasa la
	 * matriz y el índice y devuelve una copia cruda de la instancia, que a su
	 * vez es lo que devuelve el método de Dataset
	 * @param int Indice
	 * @return Instancia Instancia del dataset
	 */
	public Instancia getInstancia(int index) {
		return Instancia.getInstancia(matriz, index);
	}
	
	public ArrayList<String> getUniqueTypes(){
		return ((A_categorico)matriz.get(matriz.size()-1)).getNombresUnicos();
	}

	/**
	 * Copia cruda de la matriz de atributos teniendo en cuenta los pesos
	 * @return ArrayListAtributo Matriz de atributos
	 */
	public ArrayList<Atributo> copia() {
		/*
		 * Tengo que hacerlo de esta manera porque si se iguala un 
		 * ArrayList<Atributo> = ArrayList<Atributo> hace una copia por
		 * referencia y uno una copia cruda. Se peude comprobar descomentando la linea anterior y sustituyendolo por la parte de abajo
		 */
		ArrayList<Atributo> aux = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(matriz));
		return aux;
	}
	
	public void addInstancia(Instancia paramInstance) {
		assert(matriz.size()==paramInstance.getSize());
		for(int i=0; i<matriz.size()-1;i++){
			((A_numerico)matriz.get(i)).insert(paramInstance.getAtNum().at(i));
		}
		((A_categorico)matriz.get(matriz.size()-1)).insert(paramInstance.getAtCat().at(0));
		numFil++;
	}
//	
	public void removeInstancia(int index) {
		assert((index >= 0) && (index < numFil));
		for(int i=0; i<matriz.size()-1;i++){
			((A_numerico)matriz.get(i)).eliminarElemento(index);
		}
		((A_categorico)matriz.get(matriz.size()-1)).eliminarElemento(index);
	}
//	
//	public void modifyInstancia() {}

	public void addAttribute(Atributo paramAttribute) {
		assert(numFil==paramAttribute.getSize());
		matriz.add(paramAttribute);
	}
//	
	public void removeAttribute(int n) {
		matriz.remove(n);
	}
//	
//	public void modifyAttribute() {}
	
}