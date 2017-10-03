
package estructuras;
import java.util.*;

public class Estado {
    /*Atributos*/
    int idEstado; /*etiqueta con que se identificara el estado*/
    boolean esFinal; /*true si es un estado de aceptacion, false de lo contrario*/
    boolean esInicial;
    ArrayList<Transicion> transiciones; /*almacenara todas las transiciones que partan de este estado*/
    boolean visitado;
    String etiqueta;
    
    /*Constructores*/
    public Estado(int idEstado, boolean esFinal){
        this.idEstado = idEstado;
        this.esFinal = esFinal;
        transiciones = new ArrayList<>();
        this.etiqueta = Integer.toString(idEstado);
        if(idEstado ==0)
            this.esInicial = true;
        else
            this.esInicial = false;
    }
    
    public Estado(int idEstado){
        this(idEstado, false);
    }
    
    public Estado(){};
    
    /*Getters & Setters*/

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public boolean getEsFinal() {
        return esFinal;
    }

    public void setEsFinal(boolean esFinal) {
        this.esFinal = esFinal;
    }
    
//    public boolean getEsInicial(){
//        return idEstado == 0;
//    }

    public boolean getEsInicial() {
        return esInicial;
    }
    
    

    public ArrayList<Transicion> getTransiciones() {
        return transiciones;
    }

    public boolean getVisitado() {
        return visitado;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
    
    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }
    
    public boolean getEsIdentidad(){
        for(Transicion trans: getTransiciones())
            if(!this.equals(trans.getDestino()))
                return false;
        return true;
    }

    public void setEsInicial(boolean esInicial) {
        this.esInicial = esInicial;
    }
    
    public HashMap<String, Estado> getTransicionesConAlfabeto(Vector<String> alfabeto){
        HashMap<String, Estado> trans = new HashMap<String, Estado>();
        
        //iniciamos todo con null
        for(String s: alfabeto)
            trans.put(s, null);
        
        //Coloco las transiciones existentes
        for(Transicion t: getTransiciones())
            trans.put(t.getSimbolo(), t.getDestino());
        
        return trans;
    }

    @Override
    public String toString() {
        return "{" + etiqueta + '}';
    }
    
    public Vector<String> toString(ArrayList<Estado> conjEstados){
        Vector<String> idEstados = new Vector<>();
        //String toStringEstados = "{ ";
        String idE = "";
        for(Estado actual: conjEstados){
          //  toStringEstados += Integer.toString(actual.getIdEstado()) + ", ";
            //idE = Integer.toString(actual.getIdEstado());
            //idEstados.add(idE);
            idEstados.add(actual.getEtiqueta());
        }
        //toStringEstados += "}";
        //return toStringEstados;
        return idEstados;
    }
}