package clasificacion.distancia;
import datos.*;
import java.util.ArrayList;

public interface Distancia{
	abstract String getNombre();
	abstract Double calcularDistancia(Instancia inst_saved, Instancia ins_toknow,
			ArrayList<Double> ponderations);
}