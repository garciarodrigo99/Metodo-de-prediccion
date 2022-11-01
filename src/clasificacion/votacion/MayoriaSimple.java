package clasificacion.votacion;

import java.util.ArrayList;

import utilidades.Par;
import utilidades.modificadores;

public class MayoriaSimple implements TipoVotacion{
	
	private final double UMBRAL = 0.0;//Umbral de tipo doble constante 0.0 (definición de mayoría simple)
	
	@Override
	public String getTipo(ArrayList<Par<String,Double>> psd) {
		return modificadores.getTipo(psd, UMBRAL);
	}
	
	public String getNombre() {
		return "Mayoría simple";
	}
	
	public Double getLimit() {
		return UMBRAL;
	}
}
