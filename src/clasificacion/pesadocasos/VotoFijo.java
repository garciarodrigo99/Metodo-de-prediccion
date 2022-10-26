package clasificacion.pesadocasos;

import java.util.ArrayList;
import utilidades.Par;
import clasificacion.ParID;

public class VotoFijo implements PesadoCasos{
	
	ArrayList<Double> pesos = new ArrayList<Double>();
	
	public VotoFijo(ArrayList<Double> arrd) {
		pesos = arrd;
	}
	
	//Tamaño del vector
	public VotoFijo(int n) {
		for(int i=n; i>0;i--) {
			pesos.add(Double.valueOf(i));
		}
	}
	
	@Override
	public ArrayList<Par<String,Double>> getVotos(ArrayList<ParID> parid){
		assert parid.size() == pesos.size();
		ArrayList<Par<String,Double>> parsd = new ArrayList<Par<String,Double>> ();
		for(int i=0;i<parid.size();i++) {
			parsd.add(new Par<String,Double>(parid.get(i).getInstancia().getAtCat().at(0),pesos.get(i)));
		}
		return parsd;
	}
	
	public void setPesos(ArrayList<Double> arrd) {
		pesos = arrd;
	}
	
	public ArrayList<Double> getPesos() {
		return pesos;
	}
	
	@Override
	public String getNombre() {
		return "Voto fijo";
	}
}
