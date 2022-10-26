package utilidades.preprocesado;

import java.util.ArrayList;

import datos.Instancia;
import datos.atributo.Atributo;

/**
 * Interfaz que se encarga de Preprocesar un vector de atributos.
 */
public interface PreprocesadoDatos {
	// Opción para preprocesar ArrayList<Atributo> sin añadir nuevas instancias
	abstract ArrayList<Atributo> getPreprocesado(ArrayList<Atributo> atributos);
	// Opción para preprocesar ArrayList<Atributo> con solo una nueva instancia
	abstract ArrayList<Atributo> getPreprocesado(ArrayList<Atributo> atributos, Instancia inst);
	// Opción para preprocesar ArrayList<Atributo> con múltiples nuevas instancias
	abstract ArrayList<Atributo> getPreprocesado(ArrayList<Atributo> atributos, ArrayList<Instancia> instancias);
	abstract String getNombre();
}
