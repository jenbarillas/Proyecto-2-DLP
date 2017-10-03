

package algoritmos;
import java.io.*;
import java.util.*;
import java.lang.Runtime;
import estructuras.*;

public class Archivo {
    public void crearArchivo(Automata aFinal, String nombreArchivo){
        FileWriter fw;
        try{
            fw = new FileWriter(nombreArchivo + ".txt");
            fw.write(aFinal.toString());
            fw.close();
            System.out.println("Documento creado!");
        }
        catch(IOException io){
            System.out.println("Error al crear el archivo.");
            return;
        }
    }
    
    public void escribirArchivo(String cuerpo, String titulo){
        FileWriter fw;
        try{
            fw = new FileWriter(titulo + ".txt");
            fw.write(cuerpo);
            fw.close();
        }
        catch(IOException io){
            System.out.println("Error al crear el archivo.");
        }
    }
    

    
    public void crearArchivoJava(ArrayList<String> Keys, ArrayList<String> Values){
        File f = new File("C:/Users/Jennifer/Documents/NetBeansProject/Resultado(lexer)/src/resultado/LexerGenerado.java");
        try{
            PrintWriter Fescribe = new PrintWriter(f,"UTF-8"); 
            Fescribe.append("" +
                    "package resultado;\n" +
                    "\n" +
                    "import algoritmos.*;\n" +
                    "import Fase2.Token;\n" +
                    "import estructuras.*;\n" +
                    "import java.util.*;\n" +
                    "import java.io.*;" +
                    "public class LexerGenerado {\n" +
                    "    Scanner sc;\n" +
                    "    ArrayList<Token> listaToken;\n" +
                    "    ArrayList<Automata> listaAutomatas;\n" +
                    "    ArrayList<String> regexs;\n" +
                    "    String[] palabras;\n" +
                    "    \n" +
                    "    public static void main(String[] args) throws IOException{\n" +
                    "        Scanner sc ;\n" +
                    "        LexerGenerado lexer = new LexerGenerado();\n" +
                    "    }\n" +
                    "\n" +
                    "    public LexerGenerado() throws IOException {\n" +
                    "        this.sc = new Scanner(System.in);\n" +
                    "        this.listaToken = new ArrayList<>();\n" +
                    "        this.listaAutomatas = new ArrayList<>();\n" +
                    "        this.regexs = new ArrayList<>();\n" +
                    "        crearAutomatas();\n" +
                    "    }\n" +
                    "    \n" +
                    "    private void crearAutomatas() throws IOException{\n" +
                    "        AFN afn = new AFN();\n" +
                    "        Automata automata = new Automata();\n" +
                    "        \n" +
                    "        String number = \"(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*\";\n" +
                    "        automata = afn.crearAFN(\"number\", number);\n" +
                    "        listaAutomatas.add(automata);\n" +
                    "        String ident = \"(a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|A|B|C|D|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z)(a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|A|B|C|D|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|0|1|2|3|4|5|6|7|8|9)+\";\n" +
                    "        automata = afn.crearAFN(\"ident\", ident);\n" +
                    "        listaAutomatas.add(automata);\n" +
                    "        String letter = \"(a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|A|B|C|D|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z)\";\n" +
                    "        automata = afn.crearAFN(\"letter\", letter);\n" +
                    "        listaAutomatas.add(automata);\n" +
                    "        String digit = \"(0|1|2|3|4|5|6|7|8|9)\";\n" +
                    "        automata = afn.crearAFN(\"digit\", digit);\n" +
                    "        listaAutomatas.add(automata);\n"); 
            for (int i=0; i<Keys.size(); i++){
                Fescribe.append(
                        "        automata = afn.crearAFN(\""+ Keys.get(i) + "\"," + Values.get(i) + ");\n" +
                        "        listaAutomatas.add(automata);\n"
                    
                );
            }
            
            Fescribe.append(
                    "        Simular();\n" +
                    "    }\n" +
                    "    \n" +
                    "    private ArrayList<String> LeerArchivo() throws IOException{\n" +
                    "        File file = new File(\"C:/Users/Jennifer/Documents/NetBeansProject/GeneradorLexer/src/Pruebas/input.txt\");\n" +
                    "        sc = new Scanner(file);\n" +
                    "        ArrayList<String> palabrasLeidas = new ArrayList<>();\n" +
                    "        while(sc.hasNext()){\n" +
                    "            palabrasLeidas.add(sc.next());\n" +
                    "        }\n" +
                    "        return palabrasLeidas;\n" +
                    "    }\n" +
                    "    \n" +
                    "    private void Simular() throws IOException{\n" +
                    "        Simulacion simulador = new Simulacion();\n" +
                    "        Token token = new Token();\n" +
                    "        boolean aceptaAFN;\n" +
                    "        ArrayList<String> conjuntoPalabras = LeerArchivo();\n" +
                    "        System.out.println(\"Resultado:\");\n" +
                    "        for(String palabra: conjuntoPalabras){\n" +
                    "            for(Automata afn: listaAutomatas){\n" +
                    "                aceptaAFN = simulador.AFN(afn.getEstados(), palabra);\n" +
                    "                if(aceptaAFN){\n" +
                    "                    token.setId(afn.getIdentificador());\n" +
                    "                    token.setCuerpo(palabra);\n" +
                    "                }\n" +
                    "                else{ continue; }\n" +
                    "            }\n" +
                    "            if(token.getId()==null){\n" +
                    "                System.out.println(\"No se reconoció \" + palabra);\n" +
                    "                continue;\n" +
                    "            }\n" +
                    "            else{\n" +
                    "                System.out.println(\"\\t\" + token.toString());\n" +
                    "                listaToken.add(token);\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }    \n" +
                    "}\n" +
                    ""
            );
            Fescribe.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }
}