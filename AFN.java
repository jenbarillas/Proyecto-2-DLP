
package estructuras;
import algoritmos.*;
import java.util.*;

public class AFN extends Automata {
    /*Atributos*/
    String regex;
    public final String VACIO = "%";
    
    /*Constructores*/
    public AFN(){};

    public AFN(String regex) {
        this.regex = regex;
    }
    
    /*Getters & Setters*/

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
    
    /*Metodos*/
    public Automata crearAFN(String identificador, String cadena){
        Stack<Automata> pila = new Stack<>();
        Postfix cadenaPostfix  = new Postfix();
        String regex = cadenaPostfix.infixToPostfix(cadena, false);
        
        Thompson afn = new Thompson();
        Vector<String> temp = new Vector<>();
        for(Character car: regex.toCharArray())
            temp.add(""+car);
        for(String letra: temp){
            switch(letra){
                case "|":
                    if(pila.size() >= 2){
                        Automata afn2 = pila.pop();
                        Automata afn1 = pila.pop();
                        Automata afnUnion = afn.union(afn1, afn2);
                        pila.push(afnUnion);
                    }
                    continue;
                case ".":
                    if(pila.size() >= 2){
                        Automata afn2 = pila.pop();
                        Automata afn1 = pila.pop();
                        Automata afnConc = afn.concatenacion(afn1, afn2);
                        pila.push(afnConc);
                    }
                    continue;
                case "*":
                    if(pila.size() >= 1){
                        Automata afn1 = pila.pop();
                        Automata afnKleene = afn.kleene(afn1);
                        pila.push(afnKleene);
                    }
                    continue;
                case "+":
                    if(pila.size() >=1){
                        Automata afn1 = pila.pop();
                        Automata afnPositivo = afn.cerraduraPositiva(afn1);
                        pila.push(afnPositivo);
                    }
                    continue;
                case "?":
                    if(pila.size() >= 1){
                        Automata afn1 = pila.pop();
                        Automata afnOpcion = afn.opcion(afn1);
                        pila.push(afnOpcion);
                    }
                    continue;
                default:
                    /*Si no es un operador, entonces es un simbolo,
                    por lo tanto; que haga un automata fundamental*/
                    Automata basico = afn.fundamental(letra);
                    pila.push(basico);
            }
        }
        Automata afnFinal = pila.pop();
        afnFinal.setIdentificador(identificador);
        afnFinal.setAlfabeto(afnFinal.obtenerAlfabeto(regex));
        //System.out.println("\n-----------------DESCRIPCION DE AFN-----------------");
        //System.out.println("\n" + afnFinal.toString());
        return afnFinal;
    }
}