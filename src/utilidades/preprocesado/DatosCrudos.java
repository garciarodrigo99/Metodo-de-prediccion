package utilidades.preprocesado;

import java.util.ArrayList;

import datos.Instancia;
import datos.atributo.Atributo;
import utilidades.*;

/*
 * Esta clase podría no hacer falta ya que realmente no hace nada en su cuerpo,
 * pero he decidido crearla para que la interfaz PreprocesadoDatos tenga la 
 * opción datos crudos que no haga nada pero que pueda ser llamada a través
 * de la interfaz a través de los métodos abstractos
 */
public class DatosCrudos implements PreprocesadoDatos {
	
	@Override
	public ArrayList<Atributo> getPreprocesado(ArrayList<Atributo> atributos){
		return modificadores.copiaCrudaArAtrib(atributos);
	}
	
	@Override
	public ArrayList<Atributo> getPreprocesado(ArrayList<Atributo> atributos, Instancia inst){
		return getPreprocesado(atributos);
	}
	
	@Override
	public ArrayList<Atributo> getPreprocesado(ArrayList<Atributo> atributos, ArrayList<Instancia> instancias){
		return getPreprocesado(atributos);
	}
	
	@Override
	public String getNombre() {
		return getNombreStatic();
	}

	public static String getNombreStatic() {
		return "Datos Crudos";
	}
}
