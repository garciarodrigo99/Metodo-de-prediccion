package clasificacion;

import java.util.ArrayList;
import datos.atributo.Atributo;
import datos.Instancia;

public interface Clasificacion {
	abstract String clasificar(ArrayList<Atributo> ala, Instancia inst);
	abstract String getInfo();
	abstract String getCsvValues();
}
