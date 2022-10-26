package clasificacion.pesadocasos;

import java.util.*;
import utilidades.*;
import clasificacion.ParID;

public class Cercania implements PesadoCasos{

	@Override
	public ArrayList<Par<String,Double>> getVotos(ArrayList<ParID> parid){
		ArrayList<Par<String,Double>> parsd = new ArrayList<Par<String,Double>> ();
		for(int i=0;i<parid.size();i++) {
			if(parid.get(i).getDistancia() == 0.0) {
				parsd.add(new Par<String,Double>(parid.get(i).getInstancia().getAtCat().at(0),matematicas.infinito));
			} else
				parsd.add(new Par<String,Double>(parid.get(i).getInstancia().getAtCat().at(0),(1/parid.get(i).getDistancia())));
		}
		return parsd;
	}
	
	@Override
	public String getNombre() {
		return "Cercania";
	}
}
