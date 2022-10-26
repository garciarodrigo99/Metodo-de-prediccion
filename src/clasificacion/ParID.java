package clasificacion;

import datos.*;

/*
 * Clase Par Instancia Instancia - Distancia
 * Se usará en la clase KNN para que Instancia y Distancia a otra instancia
 * estén juntas en una clase y no en dos vectores separados.
 * No se ha hecho genérica ya que podría haber problemas a la hora de igualar
 * una instancia como atributo, ya que las igualaciones en Java son con 
 * referencia.
 */
public class ParID {
    private Instancia instancia_;
    private Double distancia;
    
    public ParID(Instancia i, Double d){
        instancia_ = new Instancia(Instancia.copiaInstancia(i));
        this.distancia = d;
    }
    
    public Instancia getInstancia(){ return Instancia.copiaInstancia(instancia_); }
    public Double getDistancia(){ return distancia; }
    public void setInstancia(Instancia i){ instancia_ = Instancia.copiaInstancia(i); }
    public void setDistancia(Double d){ this.distancia = d; }
    
    public static ParID copiaCruda(ParID pid) {
    	ParID aux = new ParID(Instancia.copiaInstancia(pid.getInstancia()),pid.getDistancia());
    	return aux;
    }
    
    public void print() {
    	System.out.print("ParID: Instancia: ");
    	instancia_.print();
    	System.out.print("\tDistancia:"+distancia);
    }
}
