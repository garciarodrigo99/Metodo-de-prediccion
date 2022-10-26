package clasificacion;

import java.util.ArrayList;

import clasificacion.pesadocasos.IgualdadVotos;
import clasificacion.pesadocasos.PesadoCasos;
import clasificacion.votacion.TipoVotacion;
import utilidades.Par;
import datos.*;
import datos.atributo.*;


public class Entorno {
	private KNN knn_ = new KNN();
	private PesadoCasos pcasos = new IgualdadVotos();

	public void getInfo() {
		knn_.getInfo();
		System.out.print("Pesado de casos: "+pcasos.getNombre()+"\n");
	}
	
	public void setVotacion(PesadoCasos pc) {
		pcasos = pc;
	}
	
	public KNN getKnn() {
		return knn_;
	}
	
	public ArrayList<Par<String,Double>> getVotos(ArrayList<Atributo> at, Instancia inst) {
		return pcasos.getVotos(knn_.getVecinos(at, inst));
	}
}