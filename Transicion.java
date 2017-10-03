
package estructuras;
import java.util.*;

public class Transicion {
    /*Atributos*/
    String simbolo;
    Estado destino;
    
    /*Constructor*/
    public Transicion(){};
    
    public Transicion(String simbolo, Estado destino){
        this.simbolo = simbolo;
        this.destino = destino;
    }
    
    /*Getters & Setters*/

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public Estado getDestino() {
        return destino;
    }

    public void setDestino(Estado destino) {
        this.destino = destino;
    }

    //@Override
    public String toString(Estado eOrigen) {
        String toStringTransiciones = "";
        ArrayList<Transicion> transiciones = eOrigen.getTransiciones();
        for(Transicion deEstOrigen: transiciones){
            //toStringTransiciones += "(" + Integer.toString(eOrigen.getIdEstado()) + ", " + deEstOrigen.getSimbolo() + ", " + Integer.toString(deEstOrigen.getDestino().getIdEstado()) + ") - ";
            toStringTransiciones += "(" + eOrigen.getEtiqueta() + ", " + deEstOrigen.getSimbolo() + ", " + deEstOrigen.getDestino().getEtiqueta() + ") - ";
        }
        return toStringTransiciones;
    }

    @Override
    public String toString() {
        return "[" +  simbolo + ", " + destino + ']';
    }
    
    
}