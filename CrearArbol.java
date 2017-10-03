
package algoritmos;

import estructuras.Arbol;
import java.util.*;

/**
 *
 * @author Jenny
 */
public class CrearArbol {
    
    String regex;
    public static final String VACIO = "%";

    public CrearArbol(String cadena) {
        Stack<Arbol> pila = new Stack<>();
        Postfix cadenaPostfix = new Postfix();
        this.regex = cadenaPostfix.infixToPostfix(cadena, true);
        System.out.println(regex);
        
        List<Character> operadores = Arrays.asList('|', '*', '+', '?', '.');
        
        Vector<Character> temp = new Vector<>();
        for(Character car: regex.toCharArray())
            temp.add(car);
        int contador = 1;
        for(Character c: temp){
            switch(c){
                case '|':
                    if(pila.size() >=2){
                        Arbol arbol2 = pila.pop();
                        Arbol arbol1 = pila.pop();
                        Arbol arbolUnion = new Arbol(arbol1, arbol2, 0, c);
                        //arbolUnion.toString();
                        pila.push(arbolUnion);
                    }
                continue;
                case '.':
                    if(pila.size() >= 2){
                        Arbol arbol2 = pila.pop();
                        Arbol arbol1 = pila.pop();
                        Arbol arbolConc = new Arbol(arbol1, arbol2, 0, c);
                        //arbolConc.toString();
                        pila.push(arbolConc);
                    }
                    continue;
                case '*':
                    if(pila.size() >= 1){
                        Arbol arbol1 = pila.pop();
                        Arbol arbolKleene = new Arbol(arbol1, null, 0, c);
                        //arbolKleene.toString();
                        pila.push(arbolKleene);
                    }
                    continue;
                case '+':
                    if(pila.size() >=1){
                        Arbol arbol1 = pila.pop();
                        Arbol arbolPositivo = new Arbol(arbol1, null, 0, c);
                        //arbolPositivo.toString();
                        pila.push(arbolPositivo);
                    }
                    continue;
                case '?':
                    if(pila.size() >=1){
                        Arbol arbol1 = pila.pop();
                        Arbol arbolOpcion = new Arbol(arbol1, null, 0, c);
                        //arbolOpcion.toString();
                        pila.push(arbolOpcion);
                    }
                    continue;
                default:
                    /*Si no es un operador, entonces es un simbolo,
                    por lo tanto, que cree un arbol sin hijos*/
                    Arbol basico = new Arbol(null, null, contador, c);
                    //basico.toString();
                    pila.push(basico);
                    contador++;
            }
        }
        Arbol arbolFinal = pila.pop();
        arbolFinal.toString();
    }
    
    
    
}
