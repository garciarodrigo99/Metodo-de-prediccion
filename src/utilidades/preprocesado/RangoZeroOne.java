package utilidades.preprocesado;

import java.util.ArrayList;

import datos.Instancia;
import datos.atributo.Atributo;
import utilidades.modificadores;

/**
 * Clase RangoZeroOne que implementa la interfaz PreprocesadoDatos que 
 * preprocesa un vector de atributos con la ecuación de rango cero a uno.
 * Como se puede querer preprocesar un vector de atributos con la fórmula rango
 * de cero a uno sin querer crear una instancia de la interfaz, cada método 
 * llama al método estático RangoZeroOneAtributo de la clase modificadores, en 
 * el paquete utilidades.
 */
public class RangoZeroOne implements PreprocesadoDatos {
	
	@Override
	public ArrayList<Atributo> getPreprocesado(ArrayList<Atributo> atributos){
		ArrayList<Atributo> atrib_aux = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(atributos));
		return modificadores.RangoZeroOneAtributo(atrib_aux);
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
		return "Rango 0-1";
	}
}
