
package algoritmos;
import estructuras.*;
import java.util.*;

public class Minimizacion {
    
    /* Calcula la nueva etiqueta para un estado del AFD
    minimizado segun los estados agrupados */
    private String obtenerEtiqueta(ArrayList<Estado> grupo){
        String etiqueta = "";
                
        for(Estado e: grupo){
            etiqueta += Integer.toString(e.getIdEstado());
        }
//        if(etiqueta.endsWith(","))
//            etiqueta = etiqueta.substring(0, etiqueta.length()-1);
        return etiqueta;
    }
    
    /*Determina si un grupo de estados tiene un estado de aceptacion*/
    private boolean tieneEstadoAceptacion(ArrayList<Estado> grupo){
        for(Estado e: grupo)
            if(e.getEsFinal())
                return true;
        return false;
    }
    
    /*Determina si un grupo de estados tiene el estado inicial*/
    private boolean tieneEstadoInicial(ArrayList<Estado> grupo){
        for(Estado e: grupo)
            if(e.getEsInicial())
                return true;
        return false;
    }
    
    
    private ArrayList<Integer> getGruposAlcanzados(Estado origen, ArrayList<ArrayList<Estado>> particion, Vector<String> alfabeto){
        //Grupos alcanzados por el estado
        ArrayList<Integer> gruposAlcanzados = new ArrayList<>();
        
        /*Para cada simbolo del alfabeto obtenemos los estados alcanzados.
        Si para un simbolo dado el estado no tiene transicion, le ponemos null*/
        HashMap<String, Estado> transiciones = origen.getTransicionesConAlfabeto(alfabeto);
        
        /*Para cada simbolo del alfabeto obtengo el estado alcanzado 
        a partir del estado origen y busco en cual grupo de la
        particion está*/
        for(String s: alfabeto){
            Estado destino = transiciones.get(s);
            
            /*si el estado destino es null, entonces no pertenece
            a ningun grupo, entonces lo ponemos en el grupo ficticio
            con inidice -1*/
            if(destino==null){
                gruposAlcanzados.add(-1);
            }
            else{
                for(int pos=0; pos < particion.size(); pos++){
                    ArrayList<Estado> grupo = particion.get(pos);
                    if(grupo.contains(destino)){
                        gruposAlcanzados.add(pos);
                        break; //el estado va a estar en un solo grupo
                    }
                }
            }
        }        
        return gruposAlcanzados;
    }
    
    /*Minimizacion de AFD por el metodo de Particiones*/
    public Automata minimizar(Automata afd){
        Hashtable<Estado, ArrayList<Integer>> tabla1;
        Hashtable<ArrayList<Integer>, ArrayList<Estado>> tabla2;
        
        //Conjunto de las particiones del AFD
        ArrayList<ArrayList<Estado>> particion = new ArrayList<>();
        
        /*SepararAFD en 2 grupo: estados de aceptacion y 
        estados de no aceptacion*/
        particion.add(afd.getConjuntoNoAceptacion());
        particion.add(afd.getConjuntoAceptacion());
        
        //Construccion de nuevas particiones
        ArrayList<ArrayList<Estado>> nuevaParticion;
        
        while(true){
            //Conjunto de nuevas particiones en cada pasada
            nuevaParticion = new ArrayList<>();
            
            for(ArrayList<Estado> grupo: particion){
                /*pueden haber prupos vacios que no se van a
                tomar en cuenta.
                Por ejemplo, que todos los estados del AFD sean
                de aceptacion, entonces del grupo de no aceptacion
                va a estar vacio*/
                if(grupo.size()==0)
                    continue;
                /*Si hay un grupo con un solo estado dentro,
                entonces ese ya no se puede minimizar mas*/
                else if(grupo.size()==1)
                    nuevaParticion.add(grupo);
                else{
                    /*hallo los grupos alcanzados por cada estado del 
                    grupo actual*/
                    tabla1 = new Hashtable<>();
                    for(Estado e: grupo)
                        tabla1.put(e, getGruposAlcanzados(e, particion, afd.getAlfabeto()));
                    
                    //Calculo las nuevas particiones
                    tabla2 = new Hashtable<>();
                    for(Estado e: grupo){
                        ArrayList<Integer> alcanzados = tabla1.get(e);
                        if(tabla2.containsKey(alcanzados))
                            tabla2.get(alcanzados).add(e);
                        else{
                            ArrayList<Estado> temp = new ArrayList<>();
                            temp.add(e);
                            tabla2.put(alcanzados, temp);
                        }
                    }
                    
                    /*Copio las nuevas particiones al conjunto
                    de nuevas particiones*/
                    for(ArrayList<Estado> c: tabla2.values())
                        nuevaParticion.add(c);
                }
            }
            //Se ordena la nueva particion
//            nuevaParticion.sort(null);
            
            /*
            Si las particiones son iguales
                significa que no hubo cambios entonces hay que terminar, 
            else
                seguir haciendo particiones*/
            if(nuevaParticion.equals(particion))
                break;
            else
                particion = nuevaParticion;
        }
        
        /*Armar el nuevo AFD con los nuevos estados obtenidos
        de las particiones*/
        Automata afdMinimizado = new Automata(afd.getAlfabeto(), afd.getRegex());
        /*Agrego los estados al nuevo AFD
        Para los estados agrupados, coloca una nueva etiqueta que indique
        cuales estados esta conteniendo este nuevo estado*/
        for(int i=0; i < particion.size(); i++){
            ArrayList<Estado> grupo = particion.get(i);
            boolean esFinal = false;
            /*Si el grupo actual tiene un estado de aceptacion,
            entonces este nuevo grupo tambien sera de aceptacion
            en el AFD minimizado*/
            if(tieneEstadoAceptacion(grupo))
                esFinal = true;
            
            
                
            /*la nueva etiqueta de este grupo de estados sera la
            concatenacion de sus id's separados por coomas(,)*/
            String etiqueta = obtenerEtiqueta(grupo);
            /*agrego el estado al nuevo AFD minimizado*/
            Estado estado = new Estado(i, esFinal);
            estado.setEtiqueta(etiqueta);
            if(tieneEstadoInicial(grupo)){
                estado.setEsInicial(true);
            }
//            for(Estado e: grupo){
//                if (e.getIdEstado() ==0)
//                    estado.setEsInicial(true);
//                else
//                    estado.setEsInicial(false);
//            }
            afdMinimizado.agregarEstado(estado);
//            System.out.println("etiqueta " + estado.getEtiqueta());
//            System.out.println("id " + estado.getIdEstado());
        }
        
        /*Genero un mapeo de grupo a estados del AFD original
        para que sea mas facil obtener los estados correctos al
        momento de agregar las transiciones al nuevo AFD minimizado*/
        Hashtable<Estado, Estado> mapeo = new Hashtable<>();
        for(int i=0; i<particion.size();i++){
            //Grupo que se va a mapear
            ArrayList<Estado> grupo = particion.get(i);
            Estado valor = afdMinimizado.getEstado(i); //estado del nuevo AFD
            
            //guardar mapeo
            for(Estado key: grupo)
                mapeo.put(key, valor);
        }
        
        /*Agrego las transiciones al nuevo AFD minimizado usando
        el mapeo de estados entre dicho AFD y el AFD original, realizado
        en el paso anterior*/
        for(int i=0; i<particion.size(); i++){
            //estado representante del grupo actual
            Estado repActual = particion.get(i).get(0);
            //estado del nuevo AFD
            Estado origen = afdMinimizado.getEstado(i);
            //agrego las transiciones
            for(Transicion trans: repActual.getTransiciones()){
                Estado destino = mapeo.get(trans.getDestino());
                origen.getTransiciones().add(new Transicion(trans.getSimbolo(), destino));
            }
        }
        System.out.println("\n-----------------DESCRIPCION DE AFD MINIMIZADO-----------------");
        System.out.println("\n" + afdMinimizado.toString());
        return afdMinimizado;
    }    
}
