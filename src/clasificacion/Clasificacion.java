package clasificacion;

import clasificacion.votacion.MayoriaSimple;
import clasificacion.votacion.TipoVotacion;
import datos.atributo.*;
import utilidades.modificadores;
import datos.*;
import java.util.*;

public class Clasificacion {
	private TipoVotacion tvotacion = new MayoriaSimple();
	private Entorno entorno_;
	protected int momento_ejec; //Antes o después procesar los datos
	
	public Clasificacion() {
		entorno_ = new Entorno();
		momento_ejec = 0;
	}
	
	public void setVotacion(TipoVotacion tv) {
		tvotacion = tv;
	}
	
	public Entorno getEntorno() {
		return entorno_;
	}
	
	public void setMomento(int i) {
		momento_ejec = i;
	}
	
	public String getGanadorVotacion(ArrayList<Atributo> ala, Instancia inst) {
		ArrayList<Atributo> arr_atrib;
		if(momento_ejec==0) {	//Preprocesa los datos con el valor de la instancia
			//Preproceso el array de atributos con la instancia como parte de él (instancia ocupa la última posición)
			arr_atrib = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(entorno_.getKnn().getPreprocesadoDatos().getPreprocesado(ala,inst))); //Array Atrib
			
		} else{	//Preprocesa los datos del array y luego vuelve a procesar con la instancia
			arr_atrib = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(entorno_.getKnn().getPreprocesadoDatos().getPreprocesado(ala))); //Array Atrib
			arr_atrib = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(entorno_.getKnn().getPreprocesadoDatos().getPreprocesado(arr_atrib,inst)));
		}
		//Guardo la instancia preprocesada
		inst = new Instancia(Instancia.copiaInstancia(Instancia.getInstancia(arr_atrib, arr_atrib.get(0).getSize()-1))); //Inst
//		System.out.print("Instancia: ");inst.print();	//Descomentar para ver instancia preprocesada
//		System.out.println();
		//Se elimina la instancia añadida recientemente del objeto array de atributos para que no se incluya como una instancia propia del dataset
		arr_atrib = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(modificadores.EliminarInstancia(arr_atrib, (arr_atrib.get(0).getSize()-1))));
		return tvotacion.getTipo(entorno_.getVotos(arr_atrib, inst));
	}
	
	public void getInfo() {
		entorno_.getInfo();
		System.out.print("Tipo de votación: "+tvotacion.getNombreClasificación()+"\n");
	}
}
