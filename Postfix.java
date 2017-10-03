
package algoritmos;
import java.util.*;

public class Postfix { 
    /*
    modifica la expresion regular para añadirle el '.' de 
    concatenacion por defecto, asi el usuario lo ingresa como 
    concatenacion por yuxtaposicion
    */
    private static String fixRegex(String cadena){
        String regex = new String();
        List<Character> opeDerecha = Arrays.asList('|', '*', '+', '?');
        List<Character> opeIzquierda = Arrays.asList('|');
        
        for(int i = 0; i< cadena.length(); i++){
            Character car1 = cadena.charAt(i);
            if(i+1 < cadena.length()){
                Character car2 = cadena.charAt(i+1);
                regex += car1;
                if(!car1.equals('(') && !car2.equals(')') && !opeDerecha.contains(car2) && !opeIzquierda.contains(car1)){
                    regex += '.';
                }
            }
        }
        regex += cadena.charAt(cadena.length()-1);
        return regex;
    }
    
    private static String addHashtag(String cadena){
        String regex = "";
        cadena += "#";
        regex = fixRegex(cadena);
        return regex;
    }
    
    private static final Map<Character, Integer> mapaPrioridad;
    static{
            Map <Character, Integer> mapa = new HashMap<Character, Integer>();
            /*establezco la prioridad de los operandos 
            (numero mas alto = mayor prioridad)*/
            mapa.put('(', 1);
            mapa.put('|', 2);
            mapa.put('.', 3);
            mapa.put('?', 4);
            mapa.put('*', 4);
            mapa.put('+', 4);
            mapaPrioridad = Collections.unmodifiableMap(mapa);
    };
    
    private static Integer obtenerPrioridad(Character car){
            Integer prioridad = mapaPrioridad.get(car);
            /*si es null, le asignara 6.
            de lo contrario, asignara el valor obtenido
            por el mapa de prioridades*/
            return prioridad == null ? 6 : prioridad;
    }
    
    public String infixToPostfix(String cadena, boolean afdDirecto){
            String postfix = new String();
            /*pila para ir metiendo y sacando los caracteres*/
            Stack<Character> stack = new Stack<>();
            String cadenaModificada = "";
            /*modifica la regex para añadirle el '.' de concatenacion
            por defecto*/
            if(afdDirecto){
                cadenaModificada = addHashtag(cadena);
            }
            else
                cadenaModificada = fixRegex(cadena);
            
            for(Character car : cadenaModificada.toCharArray()){
                switch(car){
                    case '(':
                        stack.push(car);
                        break;
                    case ')':
                        while(!stack.peek().equals('(')){
                            postfix += stack.pop();
                        }
                        stack.pop();
                        break;
                    default:
                        while(stack.size() > 0){
                            Character peekedChar = stack.peek();
                            Integer prioriPeekedChar = obtenerPrioridad(peekedChar);
                            Integer prioriCharActual = obtenerPrioridad(car);
                            if(prioriPeekedChar >= prioriCharActual){
                                postfix += stack.pop();
                            }
                            else{
                                break;
                            }
                        }
                        stack.push(car);
                        break;
                }
            }
            while(stack.size() > 0){
                postfix += stack.pop();
            }
            return postfix;
    }
}