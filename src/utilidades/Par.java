package utilidades;

public class Par <K,V>{

	private K element0;
	private V element1;
	
	public static <K, V> Par<K, V> createPair(K element0, V element1) {
	    return new Par<K, V>(element0, element1);
	}
	
	public Par(K element0, V element1) {
	    this.element0 = element0;
	    this.element1 = element1;
	}
	
	public Par(K element0) {
	    this.element0 = element0;
	}
	
	public K getElement0() {
	    return element0;
	}
	
	public V getElement1() {
	    return element1;
	}
	
	public void set(K element0, V element1) {
	    this.element0 = element0;
	    this.element1 = element1;
	}

	public void set(V element1) {
	    this.element1 = element1;
	}
}
