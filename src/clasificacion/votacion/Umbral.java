package clasificacion.votacion;

import java.util.ArrayList;

import utilidades.Par;
import utilidades.modificadores;

/*
 * Clase Umbral que implementa la interafaz Tipovotacion
 * que se encarga de evaluar la clase con más votos y como queda clasificada
 */
public class Umbral implements TipoVotacion{
	
	public final static Double UMBRAL = 0.5;//Umbral por defecto (Mayoría absoluta(>50))
	private Double umbral_ = UMBRAL;

	/*
	 * Constructor que establece un umbral elegido por el usuario
	 */
	public Umbral(double d) {
		umbral_ = d;
	}

	/*
	 * Constructor por defecto
	 */
	public Umbral() {}
	
	@Override
	public String getTipo(ArrayList<Par<String,Double>> psd) {
		return modificadores.getTipo(psd, umbral_);
	}
	
	public String getNombre() {
		return ("Umbral: "+String.valueOf(umbral_));
	}
}
