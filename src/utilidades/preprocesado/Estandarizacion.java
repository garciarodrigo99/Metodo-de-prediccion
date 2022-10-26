package utilidades.preprocesado;

import java.util.ArrayList;

import datos.Instancia;
import datos.atributo.Atributo;
import utilidades.modificadores;

/**
 * Clase Estandarizacion que implementa la interfaz PreprocesadoDatos que 
 * preprocesa un vector de atributos con la ecuación de estandarización.
 * Como se puede querer estandarizar un vector de atributos sin querer crear 
 * una instancia de la interfaz, cada método llama al método estático 
 * EstandarizarAtributo de la clase modificadores, en el paquete utilidades.
 */
public class Estandarizacion implements PreprocesadoDatos {
	
	@Override
	public ArrayList<Atributo> getPreprocesado(ArrayList<Atributo> atributos){
		ArrayList<Atributo> atrib_aux = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(atributos));
		return modificadores.EstandarizarAtributo(atrib_aux);
	}
	
	@Override
	public ArrayList<Atributo> getPreprocesado(ArrayList<Atributo> atributos, Instancia inst){
		ArrayList<Atributo> atrib_aux = new ArrayList<Atributo>(modificadores.addInstancia(atributos, inst));
		return getPreprocesado(atrib_aux);
	}
	
	@Override
	public ArrayList<Atributo> getPreprocesado(ArrayList<Atributo> atributos, ArrayList<Instancia> instancias){
		ArrayList<Atributo> atrib_aux = new ArrayList<Atributo>(modificadores.addInstancia(atributos, instancias));
		return getPreprocesado(atrib_aux);
	}
	
	@Override
	public String getNombre() {
		return "Estandarizacion";
	}
}
