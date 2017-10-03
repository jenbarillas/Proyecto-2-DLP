
package algoritmos;
import java.util.*;
import estructuras.*;

public class Simulacion {
    
    public static final String VACIO = "%";
    
    public ArrayList<Estado> move(ArrayList<Estado> estados, String simbolo){
        ArrayList<Estado> destinos = new ArrayList<>();
        for(Estado temp: estados){
            ArrayList<Transicion> transTemp = temp.getTransiciones();
            for(Transicion transT: transTemp){
                if(transT.getSimbolo().equals(simbolo)){
                    if(!destinos.contains(transT.getDestino())){
                        destinos.add(transT.getDestino());
                    }
                }
            }
        }
        return destinos;
    }
    
    public ArrayList<Estado> eClosure(ArrayList<Estado> conjEstados){
        ArrayList<Estado> eFinales = new ArrayList<>();
        Stack<Estado> pila = new Stack();
        pila.addAll(conjEstados);
        eFinales.addAll(conjEstados);
        while(!pila.isEmpty()){
            Estado temp = pila.pop();
            for(Transicion tTemp: temp.getTransiciones()){
                if(tTemp.getSimbolo().equals(VACIO)){
                    if(!eFinales.contains(tTemp.getDestino())){
                        eFinales.add(tTemp.getDestino());
                        pila.push(tTemp.getDestino());
                    }
                }
            }
        }
        return eFinales;
    }
    
    public boolean AFN(ArrayList<Estado> estadosAFN, String cadena){
        long startTime = System.currentTimeMillis();
        ArrayList<Estado> inicial = new ArrayList<>();
        inicial.add(estadosAFN.get(0));
        ArrayList<Estado> S = eClosure(inicial);
        for(int i=0; i< cadena.length(); i++){
            Character c = cadena.charAt(i);
            S = eClosure(move(S, ""+c));
        }
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        //System.out.println("\nTiempo de la simulacion del AFN:\t" + elapsedTime + " milisegundos.");
        for(Estado finales: S){
            if(finales.getEsFinal()){
                return true;
            }
        }
        return false;
        
    }
    
    public boolean AFD(ArrayList<Estado> estadosAFD, String cadena){
        long startTime = System.currentTimeMillis();
        //System.out.println(startTime);
        ArrayList<Estado> S = new ArrayList<>();
        S.add(estadosAFD.get(0));
        for(int i=0; i< cadena.length(); i++){
            Character c = cadena.charAt(i);
            S = move(S, ""+c);
        }
        
        long stopTime = System.currentTimeMillis();
        //System.out.println(stopTime);
        long elapsedTime = stopTime - startTime;
        System.out.println("\nTiempo de la simulacion del AFD:\t" + elapsedTime + " milisegundos.");
        for(Estado finales: S){
            if(finales.getEsFinal()){
                return true;
            }
        }
        return false;
    }
    
}
