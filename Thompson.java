
package algoritmos;
import estructuras.*;
import java.util.*;

public class Thompson {
    public static final String VACIO = "%";
    
    public Automata fundamental(String simbolo){
        Automata afn = new Automata();
        Estado inicial = new Estado(0, false);
        Estado destino = new Estado(1, true);
        Transicion trans = new Transicion(simbolo, destino);
        inicial.getTransiciones().add(trans);
        afn.agregarEstado(inicial);
        afn.agregarEstado(destino);
        return afn;
    }
    
    public Automata kleene(Automata afn){
        Automata afnKleene = new Automata();
        /*nuevo estado inicial*/
        Estado inicial = new Estado(afnKleene.getCantidadEstados());
        afnKleene.agregarEstado(inicial);
        /*copia los estados del automata anterior para el automata Kleene*/
        afnKleene.copiarEstados(afn, afnKleene, afnKleene.getCantidadEstados());
        
        Estado destino = new Estado(afnKleene.getCantidadEstados(), true);
        afnKleene.agregarEstado(destino);
        
        /*Transiciones*/
        Transicion nTrans;
        /*Del inicial de afnKleene hacia el inicial del afn anterior*/
        nTrans = new Transicion(VACIO, afnKleene.getEstado(1));
        inicial.getTransiciones().add(nTrans);
        /*Del inicial de afnKleene hacia el final de aKleene*/
        nTrans = new Transicion(VACIO, destino);
        inicial.getTransiciones().add(nTrans);
        /*Del final del afn anterior hacia el inicial del afn anterior*/
        Estado anterior = afnKleene.getEstado(afnKleene.getCantidadEstados()-2);
        nTrans = new Transicion(VACIO, afnKleene.getEstado(1));
        anterior.getTransiciones().add(nTrans);
        /*Del final del afn anterior hacia el final de afnKleene*/
        nTrans = new Transicion(VACIO, destino);
        anterior.getTransiciones().add(nTrans);
        
        return afnKleene;
    }
    
    public Automata union(Automata afn1, Automata afn2){
        Automata afnUnion = new Automata();
        
        /*Creo los estados inicial y aceptacion del nuevo automata*/
        Estado inicial = new Estado(afnUnion.getCantidadEstados());
        afnUnion.agregarEstado(inicial);
        afnUnion.copiarEstados(afn1, afnUnion, afnUnion.getCantidadEstados());
        afnUnion.copiarEstados(afn2, afnUnion, afnUnion.getCantidadEstados());
        
        Estado destino = new Estado(afnUnion.getCantidadEstados(), true);
        afnUnion.agregarEstado(destino);
        
        /*Transiciones*/
        Transicion trans;
        /*Desde el inicial de afnUnion hacia el inicial de afn1*/
        trans = new Transicion(VACIO, afnUnion.getEstado(1));
        inicial.getTransiciones().add(trans);
        /*Desde el inicial de afnUnion hacia el inicial de afn2*/
        trans = new Transicion(VACIO, afnUnion.getEstado(afn1.getCantidadEstados()+1));
        inicial.getTransiciones().add(trans);
        /*Desde el final de afn1 hacia el final de afnUnion*/
        trans = new Transicion(VACIO, afnUnion.getEstado(afnUnion.getCantidadEstados()-1));
        afnUnion.getEstado(afn1.getCantidadEstados()).getTransiciones().add(trans);
        /*Desde el final de afn2 hacia el final de afnUnion*/
        trans = new Transicion(VACIO, afnUnion.getEstado(afnUnion.getCantidadEstados()-1));
        afnUnion.getEstado(afnUnion.getCantidadEstados()-2).getTransiciones().add(trans);
        
        return afnUnion;
    }
    
    public Automata concatenacion(Automata afn1, Automata afn2){
        Automata afnConc = new Automata();
        afnConc.copiarEstados(afn1, afnConc, afnConc.getCantidadEstados());
        afnConc.copiarEstados(afn2, afnConc, afnConc.getCantidadEstados());
        Estado inicial = afnConc.getEstado(0);
        Estado destino = afnConc.getEstado(afnConc.getCantidadEstados()-1);
        destino.setEsFinal(true);
        Transicion vacia = new Transicion(VACIO, afnConc.getEstado(afn1.getCantidadEstados()));
        afnConc.getEstado(afn1.getCantidadEstados()-1).getTransiciones().add(vacia);
        
        return afnConc;
    }
    
    public Automata cerraduraPositiva(Automata afn){
        Automata afnPositivo = new Automata();
        
        /*nuevo estado inicial del afnPositivo*/
        Estado inicial = new Estado(afnPositivo.getCantidadEstados());
        afnPositivo.agregarEstado(inicial);
        /*copia los estados del automata anterior para el automata Kleene*/
        afnPositivo.copiarEstados(afn, afnPositivo, afnPositivo.getCantidadEstados());
        /*nuevo estado final del afnPositivo*/
        Estado destino = new Estado(afnPositivo.getCantidadEstados(), true);
        afnPositivo.agregarEstado(destino);
        
        /*Transiciones*/
        Transicion nTrans;
        /*Del inicial de afnPositivo hacia el inicial del afn anterior*/
        nTrans = new Transicion(VACIO, afnPositivo.getEstado(1));
        inicial.getTransiciones().add(nTrans);
        /*Del final del afn anterior hacia el inicial del afn anterior*/
        Estado anterior = afnPositivo.getEstado(afnPositivo.getCantidadEstados()-2);
        nTrans = new Transicion(VACIO, afnPositivo.getEstado(1));
        anterior.getTransiciones().add(nTrans);
        /*Del final del afn anterior hacia el final de afnKleene*/
        nTrans = new Transicion(VACIO, destino);
        anterior.getTransiciones().add(nTrans);
        /*Del final del afnPositivo hacia el inicial de afnPositivo*/
        nTrans = new Transicion(VACIO, inicial);
        destino.getTransiciones().add(nTrans);
        
        return afnPositivo;
    }
    
    public Automata opcion(Automata afn){
        Automata afnOpcion = new Automata();
        /*nuevo estado inicial*/
        Estado inicial = new Estado(afnOpcion.getCantidadEstados());
        afnOpcion.agregarEstado(inicial);
        /*copia los estados del automata anterior para el afnOpcion*/
        afnOpcion.copiarEstados(afn, afnOpcion, afnOpcion.getCantidadEstados());
        /*nuevo estado destino*/
        Estado destino = new Estado(afnOpcion.getCantidadEstados(), true);
        afnOpcion.agregarEstado(destino);
        
        /*Transiciones*/
        Transicion trans;
        /*Del inicial de afnPositivo hacia el inicial del afn anterior*/
        trans = new Transicion(VACIO, afnOpcion.getEstado(1));
        inicial.getTransiciones().add(trans);
        /*Del inicial de afnPositivo hacia el final de afnPositivo*/
        trans = new Transicion(VACIO, destino);
        inicial.getTransiciones().add(trans);
        /*Del final del afn anterior hacia el final de afnPositivo*/
        trans = new Transicion(VACIO, destino);
        afnOpcion.getEstado(afnOpcion.getCantidadEstados()-2).getTransiciones().add(trans);
        
        return afnOpcion;
    }
}