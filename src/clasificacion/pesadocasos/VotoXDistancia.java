package clasificacion.pesadocasos;

import java.util.ArrayList;
import utilidades.Par;
import utilidades.modificadores;
import clasificacion.ParID;

/*
 * Esta clase es una mezcla entre la clase cercanía y la clase voto fijo.
 * Se ha creado porque puede haber casos en los que haya poca distancia entre
 * el primero y los demás. Multiplica la distancia inversamente proporcional 
 * por un peso asignado a cada elemento ordenado.
 */

public class VotoXDistancia implements PesadoCasos{

	ArrayList<Double> pesos = new ArrayList<Double>();
	
	public VotoXDistancia(ArrayList<Double> arrd) {
		pesos = modificadores.copiaCruda(arrd);
	}
	
	//Tamaño del vector
	public VotoXDistancia(int n) {
		for(int i=n; i>0;i--) {
			pesos.add(Double.valueOf(i));
		}
	}
	
	@Override
	public ArrayList<Par<String,Double>> getVotos(ArrayList<ParID> parid){
		assert parid.size() == pesos.size();
		ArrayList<Par<String,Double>> parsd = new ArrayList<Par<String,Double>> ();
		for(int i=0;i<parid.size();i++) {
			parsd.set(i, new Par<String,Double>(parid.get(i).getInstancia().getAtCat().at(0),
					pesos.get(i) * (1/parid.get(i).getDistancia())));
		}
		return parsd;
	}
	
	public void setPesos(ArrayList<Double> arrd) {
		pesos = modificadores.copiaCruda(arrd);
	}
	
	public ArrayList<Double> getPesos() {
		return pesos;
	}
	
	@Override
	public String getNombre() {
		return "Voto fijo por distancia de cercania";
	}
}
