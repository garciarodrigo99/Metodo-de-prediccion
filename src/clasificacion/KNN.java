package clasificacion;

import java.util.*;

import datos.*;
import datos.atributo.Atributo;
import utilidades.modificadores;
import clasificacion.votacion.*;
import clasificacion.pesadocasos.*;

public class KNN implements Clasificacion{
	/*static*/ public Integer valork = 3;
	private TipoVotacion tvotacion = new MayoriaSimple();
	private PesadoCasos pcasos = new IgualdadVotos();
	private Entorno entorno = new Entorno();
	private int momento_ejec;

	public KNN() {}

	public String getInfo() {
		return (" Número de vecinos (valor k): "+valork+" Tipo de votacion: "+
		tvotacion.getNombre()+" Pesado de casos: "+pcasos.getNombre()+
		entorno.getInfo());
	}
	
	public static String getLabels() {
		return ("N_Vecinos"+","+"Tipo_vot"+","+"P_casos"+","+Entorno.getLabels());
	}
	
	@Override
	public String getCsvValues() {
		return (valork+","+modificadores.decform.format(tvotacion.getLimit())+","+pcasos.getNombre()+","+
				entorno.getCsvValues());
	}
	
	public void setVotacion(TipoVotacion paramVotacion) {
		tvotacion = paramVotacion;
	}

	public String getVotacion() {
		return tvotacion.getNombre();
	}

	public void setPesadoCasos(PesadoCasos paramPCasos) {
		pcasos = paramPCasos;
	}

	public String getPesadoCasos(PesadoCasos paramPCasos) {
		return pcasos.getNombre();
	}

	public Entorno getEntorno () {
		return entorno;
	}

	public void setMomento(int paramMomento) {
		momento_ejec = paramMomento;
	}

	public int getMomento(){
		return momento_ejec;
	}
	
	public void setKNeighbours(int paramK) {
		valork = paramK;
	}

	public String clasificar(ArrayList<Atributo> ala, Instancia inst, int option) {
		System.out.println("¿Instancia?");
		inst.print();
		System.out.println("[KNN.Clasificar]");
		//ArrayList<Atributo> arr_atrib; ¡Descomentar!
		ArrayList<Atributo> arr_atrib = new ArrayList<Atributo>(ala);// Aux, borrar
		/* Descomentar
		if(momento_ejec==0) {	//Preprocesa los datos con el valor de la instancia
			//Preproceso el array de atributos con la instancia como parte de él (instancia ocupa la última posición)
			arr_atrib = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(entorno_.getKnn().getPreprocesadoDatos().getPreprocesado(ala,inst))); //Array Atrib
			
		} else{	//Preprocesa los datos del array y luego vuelve a procesar con la instancia
			arr_atrib = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(entorno_.getKnn().getPreprocesadoDatos().getPreprocesado(ala))); //Array Atrib
			arr_atrib = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(entorno_.getKnn().getPreprocesadoDatos().getPreprocesado(arr_atrib,inst)));
		}*/
		//Guardo la instancia preprocesada
		Instancia newInst = new Instancia(Instancia.copiaInstancia(Instancia.getInstancia(arr_atrib, arr_atrib.get(0).getSize()-1))); //Inst
//		System.out.print("Instancia: ");inst.print();	//Descomentar para ver instancia preprocesada
//		System.out.println();
		//Se elimina la instancia añadida recientemente del objeto array de atributos para que no se incluya como una instancia propia del dataset
		arr_atrib = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(modificadores.EliminarInstancia(arr_atrib, (arr_atrib.get(0).getSize()-1))));
		return tvotacion.getTipo(pcasos.getVotos(entorno.getKNearestNeighbours(arr_atrib,valork, newInst)));
	}
	
	@Override
	public String clasificar(ArrayList<Atributo> ala, Instancia inst) {
//		System.out.println("¿Instancia?");
//		inst.print();
//		System.out.println("[KNN.Clasificar]");
		//ArrayList<Atributo> arr_atrib; ¡Descomentar!
		ArrayList<Atributo> arr_atrib = new ArrayList<Atributo>(ala);// Aux, borrar
		/* Descomentar
		if(momento_ejec==0) {	//Preprocesa los datos con el valor de la instancia
			//Preproceso el array de atributos con la instancia como parte de él (instancia ocupa la última posición)
			arr_atrib = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(entorno_.getKnn().getPreprocesadoDatos().getPreprocesado(ala,inst))); //Array Atrib
			
		} else{	//Preprocesa los datos del array y luego vuelve a procesar con la instancia
			arr_atrib = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(entorno_.getKnn().getPreprocesadoDatos().getPreprocesado(ala))); //Array Atrib
			arr_atrib = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(entorno_.getKnn().getPreprocesadoDatos().getPreprocesado(arr_atrib,inst)));
		}*/
		//Guardo la instancia preprocesada
//		Instancia newInst = new Instancia(Instancia.copiaInstancia(Instancia.getInstancia(arr_atrib, arr_atrib.get(0).getSize()-1))); //Inst
//		System.out.print("Nueva Instancia: ");newInst.print();	//Descomentar para ver instancia preprocesada
//		System.out.println();
		//Se elimina la instancia añadida recientemente del objeto array de atributos para que no se incluya como una instancia propia del dataset
		arr_atrib = new ArrayList<Atributo>(modificadores.copiaCrudaArAtrib(modificadores.EliminarInstancia(arr_atrib, (arr_atrib.get(0).getSize()-1))));
		return tvotacion.getTipo(pcasos.getVotos(entorno.getKNearestNeighbours(arr_atrib,valork, inst)));
	}

}