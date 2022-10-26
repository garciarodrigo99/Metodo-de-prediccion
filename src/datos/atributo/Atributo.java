package datos.atributo;

/**
 * Interfaz Atributo para los distintos tipos de atributos con los que
 * se trabajará en este entorno
 */
public interface Atributo /*extends Dataset*/{
	abstract int getSize();
	abstract void print();
	abstract void getInfo();
	/**
	 * Pasado un índice, elimina la índice posición del vector
	 * @param int Indice a eliminar
	 */
	abstract void eliminarElemento(int index);
	abstract String getNombre();
}
