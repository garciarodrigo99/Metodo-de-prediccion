package clasificacion.distancia;

import java.util.ArrayList;

import datos.Instancia;
import utilidades.*;

public class Chebychef implements Distancia{
	
	private String nombre = "Chebychef";
	
	@Override
	public Double calcularDistancia(Instancia inst_saved, Instancia ins_toknow,
			ArrayList<Double> ponderations) {
		return matematicas.Chebychef(inst_saved,ins_toknow);
	}
	
	@Override
	public String getNombre() {
		return nombre;
	}
}
