
package estructuras;

import java.util.*;

public class Arbol {
    Arbol hijo1;
    Arbol hijo2;
    boolean nullable;
    int posicion;
    Character etiqueta;
    ArrayList<Integer> firstpos;
    ArrayList<Integer> lastpos;
    public static final String VACIO = "%";

    public Arbol(Arbol hijo1, Arbol hijo2, int posicion, Character etiqueta) {
        this.hijo1 = hijo1;
        this.hijo2 = hijo2;
        this.posicion = posicion;
        this.etiqueta = etiqueta;
        this.nullable = nullable();
    }
    
    public Arbol getHijo1() {
        return hijo1;
    }

    public void setHijo1(Arbol hijo1) {
        this.hijo1 = hijo1;
    }

    public Arbol getHijo2() {
        return hijo2;
    }

    public void setHijo2(Arbol hijo2) {
        this.hijo2 = hijo2;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public Character getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(Character etiqueta) {
        this.etiqueta = etiqueta;
    }

    public ArrayList<Integer> getFirstpos() {
        return firstpos;
    }

    public void setFirstpos(ArrayList<Integer> firstpos) {
        this.firstpos = firstpos;
    }

    public ArrayList<Integer> getLastpos() {
        return lastpos;
    }

    public void setLastpos(ArrayList<Integer> lastpos) {
        this.lastpos = lastpos;
    }

    

    public boolean nullable(){
        List<Character> operadores = Arrays.asList('|', '*', '+', '?', '.');
        if(etiqueta.equals('*') || etiqueta.equals('?'))
            return true;
        //si la etiqueta es un simbolo != epsilon u operador
        else if(!etiqueta.equals(VACIO) && !operadores.contains(etiqueta))
            return false;
        //si la etiqueta es epsilon
        else
            return true;
    }
    
    public boolean nullable(Arbol nodo){
        if(nodo.etiqueta.equals('|')){
            if(nullable(nodo.getHijo1()) || nullable(nodo.getHijo2()))
                return true;
            else
                return false;
        }
        if(nodo.etiqueta.equals('.')){
            if(nullable(nodo.getHijo1()) && nullable(nodo.getHijo2()))
                return true;
            else
                return false;
        }
        if(nodo.etiqueta.equals('+')){
            return false;
        }
        else
            return false;
    }
    
    public ArrayList<Integer> firstpos(){
        List<Character> operadores = Arrays.asList('|', '*', '+', '?', '.');
        
        //si tiene epsilon
        if(etiqueta.equals(VACIO))
            firstpos.add(null);
        //si es un simbolo diferente de epsilon y no es un operador
        else if(!etiqueta.equals(VACIO) && !operadores.contains(etiqueta))
            firstpos.add(posicion);
        //si es el operador union
        else if(etiqueta.equals('|')){
            for(Integer i: this.getHijo1().firstpos()){
                firstpos.add(i);
            }
            for(Integer i: this.getHijo2().firstpos()){
                if(!firstpos.contains(i))
                    firstpos.add(i);
            }
        }
        //si es el operador concatenacion
        else if(etiqueta.equals('.')){
            if(nullable(this.getHijo1())){
                for(Integer i: this.getHijo1().firstpos()){
                    firstpos.add(i);
                }
                for(Integer i: this.getHijo2().firstpos()){
                    if(!firstpos.contains(i))
                        firstpos.add(i);
                }
            }
            else{
                for(Integer i: this.getHijo1().firstpos()){
                    firstpos.add(i);
                }
            }
        }
        //si es el operador kleene
        else if(etiqueta.equals('*')){
            for(Integer i: this.getHijo1().firstpos()){
                    firstpos.add(i);
            }
        }
        //si es el operador cerradura positiva
        else if(etiqueta.equals('+')){
            for(Integer i: this.getHijo1().firstpos()){
                    firstpos.add(i);
            }
        }
        //si es el operador opcion
        else if(etiqueta.equals('?')){
            for(Integer i: this.getHijo1().firstpos()){
                    firstpos.add(i);
            }
        }         
        return firstpos;
    }
    
    public ArrayList<Integer> lastpos(){
        List<Character> operadores = Arrays.asList('|', '*', '+', '?', '.');
        
        //si tiene epsilon
        if(etiqueta.equals(VACIO))
            lastpos.add(null);
        //si es un simbolo diferente de epsilon y no es un operador
        else if(!etiqueta.equals(VACIO) && !operadores.contains(etiqueta))
            lastpos.add(posicion);
        //si es el operador union
        else if(etiqueta.equals('|')){
            for(Integer i: this.getHijo1().lastpos()){
                lastpos.add(i);
            }
            for(Integer i: this.getHijo2().lastpos()){
                if(!lastpos.contains(i))
                    lastpos.add(i);
            }
        }
        //si es el operador concatenacion
        else if(etiqueta.equals('.')){
            if(nullable(this.getHijo2())){
                for(Integer i: this.getHijo2().lastpos()){
                    lastpos.add(i);
                }
                for(Integer i: this.getHijo1().lastpos()){
                    if(!lastpos.contains(i))
                        lastpos.add(i);
                }
            }
            else{
                for(Integer i: this.getHijo2().lastpos()){
                    lastpos.add(i);
                }
            }
        }
        //si es el operador kleene
        else if(etiqueta.equals('*')){
            for(Integer i: this.getHijo1().lastpos()){
                    lastpos.add(i);
            }
        }
        //si es el operador cerradura positiva
        else if(etiqueta.equals('+')){
            for(Integer i: this.getHijo1().lastpos()){
                    lastpos.add(i);
            }
        }
        //si es el operador opcion
        else if(etiqueta.equals('?')){
            for(Integer i: this.getHijo1().lastpos()){
                    lastpos.add(i);
            }
        }         
        return lastpos;
    }

    @Override
    public String toString() {
        String imprimir = "";
//        Vector<Integer> fp = new Vector<>();
//        Vector<Integer> lp = new Vector<>();
//        for(Integer i: firstpos){
//            fp.add(i);
//        }
//        for(Integer i: lastpos){
//            lp.add(i);
//        }
        
        imprimir += "Etiqueta: " + this.etiqueta 
                + " Posicion: " + Integer.toString(this.posicion);
        if(getHijo1() == null && getHijo2()== null ){
            imprimir += " Nullable: " + this.nullable + "\n";
        }
        else{
            
            imprimir += " \n\tHijo 1 de " + this.etiqueta+": " + this.hijo1.toString() 
                + "\n\tHijo 2 de "+this.etiqueta +": " + this.hijo2.toString()
                + " Nullable: " + this.nullable + "\n";
        } 
//                + "\nFirstPos: " + fp
//                + "\nLastPos: " + lp + "\n";
        System.out.println(imprimir);
        
        return imprimir;
    }
    
    
    
    
    
}
