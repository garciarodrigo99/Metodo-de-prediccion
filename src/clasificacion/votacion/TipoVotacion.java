package clasificacion.votacion;

import java.util.ArrayList;

import utilidades.Par;

/*
 * Aunque en este proyecto los tipos de clasificaciones se podrían desarrollar
 * en una sola clase, se ha creado la interfaz Clasificación para que en un futuro
 * se pudiera añadir otros sistemas de clasificación y para que pueda ser
 * reutilizada en otro código sin tener que tener que hacer uso de la clase 
 * de casos. Ej: Aceptar un tipo solo si el primer caso es el doble que el primero
 * y el tercero. 
 */
public interface TipoVotacion {
	/*
	 * Método que recibe un vector de pares string-double, que se corresponde
	 * con la clase(tipo) de una instancia y el número de votos que obtiene,
	 * halla la clase con más votos y depende del umbral devuelve la clase
	 * con mayores votos o clase no clasificada.
	 * Se externaliza el método llamando a un método estático de la clase
	 * modificadores, ya que puede servir para otro vector de pares
	 * string-double.
	 */
	abstract String getTipo(ArrayList<Par<String,Double>> psd);
	abstract String getNombreClasificación();
}
