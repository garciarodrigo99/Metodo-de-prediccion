package clasificacion.pesadocasos;

import java.util.ArrayList;
import utilidades.Par;
import clasificacion.ParID;

public class IgualdadVotos implements PesadoCasos{

	@Override
	public ArrayList<Par<String,Double>> getVotos(ArrayList<ParID> parid){
		ArrayList<Par<String,Double>> parsd = new ArrayList<Par<String,Double>> ();
		for(int i=0;i<parid.size();i++) {
			parsd.add(new Par<String,Double>(parid.get(i).getInstancia().getAtCat().at(0),1.0));
		}
		return parsd;
	}
	
	@Override
	public String getNombre() {
		return "Igual_votos";
	}
}
