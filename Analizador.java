
package Fase2;

import algoritmos.*;
import java.util.*;
import java.io.*;


public class Analizador {
    
    String letter = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String digit = "0123456789";
    String lookahead;
    String identInicial;
    ArrayList<String> palabrasReservadas;
    ArrayList<String> KeyList = new ArrayList<>();
    ArrayList<String> ValueList = new ArrayList<>();
    Scanner scan;
    //HashMap hm = new HashMap();;
    String key; String value;
    
    public Analizador(Scanner scan) throws Exception{
        //this.hm = new HashMap();
        this.scan = scan;
    }
    
    public void PalabrasReservadas() throws Exception{
        palabrasReservadas = new ArrayList<>();
        palabrasReservadas.add("COMPILER");
        palabrasReservadas.add("END");
        palabrasReservadas.add("CHARACTERS");
        palabrasReservadas.add("KEYWORDS");
        palabrasReservadas.add("TOKENS");
        palabrasReservadas.add("EXCEPT");
        palabrasReservadas.add("IGNORE");
        palabrasReservadas.add("CHR");
    }
    
    public boolean match(String terminal) throws Exception{
        if(lookahead.equals(terminal)){
            if(scan.hasNext()){
                lookahead = scan.next();
            }
            return true;
        }
        else{
            return false;
        }
    }
    
    /********************************************************************************************************/
    
    /*verificador de ident*/
    public boolean esIdent(String palabra) throws Exception{
        String[] temp = new String[palabra.length()];
        boolean acepta = false;
        for(int i =0; i<palabra.length(); i++)
            temp[i] = "" + palabra.charAt(i);
        int contador = 0;
        for(String s: temp){
            if(digit.contains(s) && contador == 0){
                acepta = false;
                System.out.println("Error de sintaxis: ident " + palabra + " invalido. No puede empezar con digito");
                break;
            }
            else if(!letter.contains(s) && contador == 0){
                acepta = false;
                //System.out.println("Error de sintaxis: ident " + palabra + " invalido. Caracter de inicio no permitido");
                break;
            }
            else{
                contador += 1;
                acepta = true;
            }
        }
        return acepta;
    }
    
    public boolean ident() throws Exception{
        boolean b = false;
        //String ident = lookahead;
        if(esIdent(lookahead)){
            if(scan.hasNext()){
                lookahead = scan.next();
                b = true;
            }
            b = true;
        }
        else{
            b = false;
        }
        return b;
    }
    
    public boolean identKD() throws Exception{
        boolean b = false;
        //String ident = lookahead;
        if(esIdent(lookahead)){
            key = lookahead;
            KeyList.add(key);
            if(scan.hasNext()){
                lookahead = scan.next();
                b = true;
            }
            b = true;
        }
        else{
            b = false;
        }
        return b;
    }
    
    /*Verificador de number*/
    public boolean esNumber(String numero) throws Exception{
        String[] temp = new String[numero.length()];
        boolean acepta = false;
        for(int i = 0; i < numero.length(); i++)
            temp[i] = "" + numero.charAt(i);
        for(String s: temp){
            if(letter.contains(s)){
                acepta = false;
                System.out.println("Error de sintaxis: number " + numero + " invalido");
                break;
            }
            else{
                acepta = true;
            }
        }
        return acepta;
    }
    
    public boolean number() throws Exception{
        boolean b = false;
        //String number = lookahead;
        if(esNumber(lookahead)){
            if(scan.hasNext()){
                lookahead = scan.next();
                b = true;
            }
            b = true;
        }
        else{
            b = false;
        }
        return b;
    }
    
    /*verificador de string*/
    public boolean esString(String cadena) throws Exception{
        if(cadena.length()<=1){
            //System.out.println("Error de sintaxis: string " + cadena + "invalido");
            return false;
        }
        CharSequence dentro = cadena.subSequence(1, cadena.length()-1);
        String cadInterior = ""+dentro;
        char c = 34; //comillas
        String cst = ""+c;
        String backlashcomilla = "\\\"";
        if(cadena.startsWith(cst) && cadena.endsWith(cst) && !cadInterior.contains(cst) ){
            return true;
        }else if(cadena.startsWith(cst) && cadena.endsWith(cst) && cadInterior.contains(backlashcomilla)){
            //System.out.println(backlashcomilla);
            return true;
        }
        else{
            //System.out.println("Error de sintaxis: string " + cadena + "no es valido");
            return false;
        }
    }
        
    /* string = '"' {anyButQuote} '"' */
    public boolean string() throws Exception{
        boolean b = false;
        //String string = lookahead;
        if(esString(lookahead)){
            if(scan.hasNext()){
                lookahead = scan.next();
                b = true;
            }
            b = true;
        }
        else{
            b = false;    
        }
        return b;
    }
    
    public boolean stringKD() throws Exception{
        boolean b = false;
        //String string = lookahead;
        if(esString(lookahead)){
            value = lookahead;
            ValueList.add(value);
            if(scan.hasNext()){
                lookahead = scan.next();
                b = true;
            }
            b = true;
        }
        else{
            b = false;    
        }
        return b;
    }
    
    /*verificador de char*/
    public boolean esCaracter(String caracter) throws Exception{
        //System.out.println(caracter);
        if(caracter.length()<=1 || caracter.length()>=4)
            return false;
        else{
            char c = 39; //apostrofe
            String apostrofe = ""+c;
            CharSequence dentro = caracter.subSequence(1, caracter.length()-1);
            String cadenaInterior = ""+dentro;
            if(caracter.startsWith(apostrofe) && caracter.endsWith(apostrofe) && !cadenaInterior.contains(apostrofe)){
                return true;

            }
            else{
                System.out.println("Error de Sintaxis: char " + caracter + " inválido");
                return false;
            }
        }
    }
    
    //  char = '\'' anyButApostrophe '\''
    public boolean caracter() throws Exception{
        boolean b = false;
        //String car = lookahead;
        if(esCaracter(lookahead)){
            if(scan.hasNext()){
                lookahead = scan.next();
                b = true;
            }
            b = true;
        }
        else{
            b = false;
        }
        return b;
    }
 
    /********************************************************************************************************/
    
    /*
    * SetDecl
    * Set
    * BasicSet
    * Char
    * KeywordDecl
    * WhiteSpaceDecl
    */
    
    //  KeywordDecl   = ident '=' string '.'
    public boolean KeywordDecl() throws Exception{
        boolean acepta = false;
        if(esIdent(lookahead) && !palabrasReservadas.contains(lookahead)){
            if(identKD() && match("=") && stringKD() && match(".")){
                //hm.put(key, value);
                acepta = true;
            }
            else
                acepta= false;
        }
        else{
            acepta = false;
            System.out.println("Error en KeywordDecl!");
        }
        return acepta;
    }
    
    // Char = char | "CHR" '(' number ')'
    public boolean Char() throws Exception{
        boolean acepta = false;
        if(lookahead.equals("CHR")){
            if(match("CHR") && match("(") && number() && match(")"))
                acepta = true;
            else
                acepta = false;
        }
        else if(esCaracter(lookahead)){
            if(caracter())
                acepta = true;
        }
        else{
            System.out.println("Error en Char!");
            acepta = false;
        }
        return acepta;
    }
    
    public boolean esChar() throws Exception{
        if(lookahead.equals("CHR"))
            return true;
        else if(esCaracter(lookahead))
            return true;
        else
            return false;
    }
    
    //  BasicSet = string | ident | Char [ ".." Char ]
    public boolean BasicSet() throws Exception{
        boolean acepta = false;
        if(esString(lookahead)){
            if(string())
                acepta = true;
        }
        
        else if(esCaracter(lookahead)){
            if(caracter()){
                acepta = true;
                if(match("..") && caracter())
                    acepta = true;
            }
        }
        else if(esIdent(lookahead)){
            if(ident())
                acepta = true;
        }
        else{
            acepta = false;
            System.out.println("Error en BasicSet;");
        }
        return acepta;
    }
    
    //Set = BasicSet { ('+'|'-') BasicSet }
    public boolean Set() throws Exception{
        boolean acepta = false;
        if(BasicSet()){
            acepta = true;
            if((match("+") || match("-")) && BasicSet()){
                acepta = true;
                while((match("+") || match("-")) && BasicSet())
                    acepta = true;
            }
        }
        else{
            acepta=false;
            System.out.println("Error en Set!");
        }
        return acepta;
    }
    
    /*   SetDecl = ident '=' Set '.'   */
    public boolean SetDecl() throws Exception{
        boolean acepta = false;
        if(esIdent(lookahead) && !palabrasReservadas.contains(lookahead)){
            if(ident()){
                if(match("=")){
                    if(Set()){
                        if(match(".")){
                            acepta = true;                            
                        }
                    }
                }
            }
            else{
                acepta = false;
            }
        }
        else{
            acepta = false;
            System.out.println("Error de sintaxis en SetDecl");
        }
        return acepta;
    }
    
    //TokenDecl = ident [ '=' TokenExpr ] [ "EXCEPT KEYWORDS" ] '.'.
    public boolean TokenDecl() throws Exception{
        boolean acepta = false;
        if(esIdent(lookahead)){
            ident();
            if(lookahead.equals("=")){
                match("=");
                if(TokenExpr()){
                    //acepta = true;
                }
            }
            else if(lookahead.equals("EXCEPT")){
                    match("EXCEPT");
                    match("KEYWORDS");
                    
            }
            if(lookahead.equals(".")){
                match(".");
                acepta = true;
            }
        }
        return acepta;
    }
    
    //TokenEpr = TokenTerm{ '|' TokenTerm }.
    public boolean TokenExpr() throws Exception{
        boolean acepta = false;
        if(TokenTerm()){
            acepta = true;
            if(lookahead.equals("|")){
                match("|");
                if(TokenTerm()){
                        acepta = true;
                        while(lookahead.equals("|")){
                            match("|");
                            acepta = TokenTerm();
                            if(acepta=false){
                                System.out.println("Error de sintaxis en TokenExpr");
                            }
                        }
                    }
            }
        }
        else{
            acepta = false;
            System.out.println("Error de sintaxis en TokenExpr");
        }
        return acepta;
    }
    
    //TokenTerm = TokenFactor { TokenFactor }.
    public boolean TokenTerm()throws Exception{
        boolean acepta;
        if(TokenFactor()){
                acepta = true;
                while(isTokenFactor()){
                    TokenFactor();
                    acepta = true;
                    while(isTokenFactor()){
                        TokenFactor();
                        acepta = true;
                    }
                }
                
         }
        else{
            acepta = false;
            System.out.println("\"TokenFactor\" esperado");
        }
        return acepta;
    }
    
    /* TokenFactor = Symbol
                    | '(' TokenExpr ')'
                    | '[' TokenExpr ']'
                    | '{' TokenExpr '}'.*/
    public boolean TokenFactor() throws Exception{
        boolean acepta = false;
        switch(lookahead){
            case "(":
                if(match("(")){
                    if(TokenExpr()){
                        if(match(")")){
                            acepta = true;
                            break;
                        }
                        else{
                            acepta = false;
                            System.out.println("\")\" esperado");
                        }
                    }
                    else{
                        acepta = false;
                        System.out.println("\"TokenExpr\" esperado");
                    }
                }
            case "[":
                if(match("[")){
                    if(TokenExpr()){
                        if(match("]")){
                            acepta = true;
                            break;
                        }
                        else{
                            acepta = false;
                            System.out.println("\"]\" esperado");
                        }
                    }
                    else{
                        acepta = false;
                        System.out.println("\"TokenExpr\" esperado");
                    }
                }
            case "{":
                if(match("{")){
                    if(TokenExpr()){
                        if(match("}")){
                            acepta = true;
                            break;
                        }
                        else{
                            acepta = false;
                            System.out.println("\"}\" esperado");
                        }
                    }
                    else{
                        acepta = false;
                        System.out.println("\"TokenExpr\" esperado");
                    }
                }
            default:
                if(isSymbol()){
                    Symbol();
                    acepta = true;
                }
                else{
                    acepta = false;
                    System.out.println("Symbol esperado");
                }
        }    
        return acepta;
    }
    
    public boolean isTokenFactor() throws Exception{
        boolean acepta = false;
        if(lookahead.equals("("))
            acepta = true;
        else if(lookahead.equals("["))
            acepta = true;
        else if(lookahead.equals("{"))
            acepta = true;
        else if(isSymbol())
            acepta = true;
        else
            acepta = false;
        return acepta;
    }
    
    public boolean isSymbol() throws Exception{
        boolean acepta = false;
        if(esIdent(lookahead) || esString(lookahead) || esCaracter(lookahead)){
            acepta = true;
        }
        else{
            acepta = false;
        }
        return acepta;
    }
    
    //  Symbol = ident | string | char.
    public boolean Symbol() throws Exception{
        boolean acepta = false;
        if(esIdent(lookahead)){
            if(ident()){
                acepta = true;
            }
        }
        else if(esString(lookahead)){
            if(string()){
                acepta = true;
            }
        }
        else if(esCaracter(lookahead)){
            if(caracter()){
                acepta = true;
            }
        }
        else{
            acepta = false;
            System.out.println("Error en Symbol!");
        }
        return acepta;
    }
    
    //  WhiteSpaceDecl = "IGNORE" Set
    public boolean WhiteSpaceDecl() throws Exception{
        boolean acepta = false;
        if(lookahead.equals("IGNORE") && palabrasReservadas.contains(lookahead)){
            if(match("IGNORE") && Set())
                acepta = true;
        }
        else{
            acepta = false;
            System.out.println("Error en WhiteSpaceDecl!");
        }
        return acepta;
    }
    
    
    public boolean ScannerSpecification() throws Exception{
        boolean acepta = false;
        switch(lookahead){
            case "CHARACTERS":
                if(match("CHARACTERS")){
                    if(SetDecl()){
                        acepta = true;
                        int cont = 1;
                        System.out.println(cont+") CHARACTER válido");
                        while((!lookahead.equals("KEYWORDS") || !lookahead.equals("TOKENS")) && SetDecl() /*&& !lookahead.equals("END")*/){
                            cont++;
                            acepta = true;
                            System.out.println(cont+") CHARACTER válido");
                            if (lookahead.equals("END")) {break;}
                        }
                        if (lookahead.equals("KEYWORDS")) {}
                        if (lookahead.equals("TOKENS")) {}
                    }
                }
            case "KEYWORDS":
                if(match("KEYWORDS")){
                    if(KeywordDecl()){
                        acepta = true;
                        int cont = 1;
                        System.out.println(cont + ") KEYWORD válida");
                        while(!lookahead.equals("TOKENS") && KeywordDecl() /*&& !lookahead.equals("END")*/){
                            cont++;
                            acepta = true;
                            System.out.println(cont +") KEYWORD válida");
                            if (lookahead.equals("END")) {break;}
                        }
                        if (lookahead.equals("TOKENS")) {}
                    }
                }
            case "TOKENS":
                if(match("TOKENS")){
                    if(TokenDecl()){
                        acepta = true;
                        int cont = 1;
                        System.out.println(cont +") TOKEN válido");
                        while(TokenDecl()){
                            cont++;
                            acepta = true;
                            System.out.println(cont+") TOKEN válido");
                            if(lookahead.equals("END")){
                                break;
                            }
                        }
                    }
                }
        }
        
        if(lookahead.equals("IGNORE")){
            //match("IGNORE");
            if(WhiteSpaceDecl()){
                acepta = true;
                while(WhiteSpaceDecl()){
                    acepta = true;
                }
            }
        }
        return acepta;
    }
    
    public boolean Cocol() throws Exception{
        //hm = new HashMap();
        boolean acepta = false;
        PalabrasReservadas();
        lookahead = scan.next();
        if(lookahead.equals("COMPILER")){
            if(match("COMPILER")){
                System.out.println("Paso COMPILER");
                if(esIdent(lookahead)){
                    identInicial = lookahead;
                    System.out.println("identInicial: " + lookahead);
                    if(ident()){
                        if(ScannerSpecification()){
                            //break;
                            //System.out.println("Paso el ScannerSpecification");
                        }
//                        if(ParserSpecification()){
//                            System.out.println("Paso el ParserSpecification");
//                        }
                        if(match("END")){
                            System.out.println("ScannerSpecification Correcto");
                            System.out.println("Paso END");
                            if(esIdent(lookahead)){
                                if(lookahead.equals(identInicial)){
                                    System.out.println("Concuerda identFinal con identInicial: " + lookahead);
                                    if(ident()){
                                        if(match(".")){
                                            acepta = true;
                                            System.out.println("Archivo Sintacticamente Correcto");
                                            Archivo archivo = new Archivo();
                                            archivo.crearArchivoJava(KeyList, ValueList);
//                                            LinkedList ll = new LinkedList();
//                                            Iterator itr = new hm.keySet().iterator();
//                                            while(itr.hasNext()){
//                                                String par = (String)itr.next();
//                                                ll.add(par);
//                                            }
//                                            System.out.println(ll);
                                        }
                                    }
                                }
                                else{
                                    System.out.println("Identificador incial no concuerda con identificador final!");
                                }
                            }
                        }
                    }
                }
            }
        }
        else{
            acepta = false;
            System.out.println("El archivo no empieza con COMPILER! ERROR!!");
        }
        return acepta;
    }
}
