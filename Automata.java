
package estructuras;
import java.util.*;
import algoritmos.*;

public class Automata {
    /*Atributos*/
    Vector<String> alfabeto; /*alfabeto*/
    ArrayList<Estado> estados; /*conjunto de estados*/
    Estado eInicial; /*estado inicial*/
    Estado eAceptacion; /*estado de aceptacion*/
    ArrayList<Estado> conjuntoAceptacion; /*conjunto de estados de */
    String regex; /*expresion regular que el automata esta describiendo*/
    String identificador;
    public static final String VACIO = "%";
    boolean marcado;
    
    /*Constructores*/
    public Automata(Vector<String> alfabeto, String regex){
        estados = new ArrayList<Estado>();
        setAlfabeto(alfabeto);
        setRegex(regex);
    }
    public Automata(){
        this(null, "");
    }
    
    /*Getters & Setters*/

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }
    
    

    public Vector<String> getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(Vector<String> alfabeto) {
        this.alfabeto = alfabeto;
    }

    public ArrayList<Estado> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<Estado> estados) {
        this.estados = estados;
    }

//    public Estado getEInicial() {
//        eInicial = estados.get(0);
//        return eInicial;
//    }
    public Estado getEInicial(){
        Estado ei = new Estado();
        for(Estado e: estados){
            if(e.getEsInicial())
                ei = e;
        }
        return ei;
    }

    public void setEInicial(Estado eInicial) {
        this.eInicial = eInicial;
    }
    
    public Estado getEAceptacion(){
        return eAceptacion;
    }
    
    public void setEAceptacion(Estado eAceptacion) {
        this.eAceptacion = eAceptacion;
    }

    public ArrayList<Estado> getConjuntoAceptacion() {
        conjuntoAceptacion = new ArrayList<>();
        for(Estado est: estados)
            if(est.getEsFinal())
                conjuntoAceptacion.add(est);
        return conjuntoAceptacion;
    }
    
    public ArrayList<Estado> getConjuntoNoAceptacion() {
        ArrayList<Estado> conjuntoNoAceptacion = new ArrayList<>();
        for(Estado est: estados)
            if(est.getEsFinal()== false)
                conjuntoNoAceptacion.add(est);
        return conjuntoNoAceptacion;
    }

    public void setConjuntoAceptacion(ArrayList<Estado> conjuntoAceptacion) {
        this.conjuntoAceptacion = conjuntoAceptacion;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public boolean getMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }
    
    /*-----------------------------Metodos-----------------------------*/
    
    /*lee la regex y extrae los simbolos que conforman el alfabeto*/
    public Vector<String> obtenerAlfabeto(String regex){
        Vector<String> alfabeto;
        String[] temp = new String[regex.length()];
        
        for(int i = 0; i < regex.length(); i++)
            temp[i] = "" + regex.charAt(i);
        Arrays.sort(temp);
        alfabeto = new Vector<String>(temp.length);
        
        for(int i = 0; i < temp.length; i++){
            String temp1 = temp[i];
                switch(temp1){
                    case "|":
                        break;
                    case ".":
                        break;
                    case "*":
                        break;
                    case "?":
                        break;
                    case "%":
                        break;
                    case "+":
                        break;
                    default:
                        if(!alfabeto.contains(temp1))
                            alfabeto.add(temp1);
                }
        }
        return alfabeto;
    }
    
    /*Agrega un estado al conjunto de estados del automata*/
    public void agregarEstado(Estado estado){
        estados.add(estado);
    }
    
    /*Devuelve el estado que se encuentra en la posicion indicada*/
    public Estado getEstado(int pos){
        return estados.get(pos);
    }
    
    /*devuelve la cantidad de estados que tiene el conjunto de estados*/
    public int getCantidadEstados(){
        return estados.size();
    }
    
    /*Un conjunto de transiciones se aplica a un estado el cual se agregara a un automata destino.
    el incremento se hace con respecto al estado destino de una transicion dada*/
    public static void copiarTransiciones(Automata destino, ArrayList<Transicion> transiciones, Estado eNuevo, int increTrans){
        for(Transicion trans: transiciones){
            int idDestino = trans.getDestino().getIdEstado(); /*id del estado destino*/
            String simbolo = trans.getSimbolo(); /*simbolo de la transicion*/
            
            Estado eDestino = destino.getEstado(idDestino + increTrans);
            Transicion nuevaTrans = new Transicion(simbolo, eDestino);
            eNuevo.getTransiciones().add(nuevaTrans);
        }
    }
    
    public static  void copiarEstados(Automata origen, Automata destino, int increTrans, int quitar){
        int increEst = increTrans;
        
        for(int i = 0; i < origen.getCantidadEstados(); i++)
            destino.agregarEstado(new Estado(destino.getCantidadEstados()));
        
        int cont = 0;
        
        for(Estado temp: origen.getEstados()){
            if(quitar > cont++)
                continue;
            Estado eNuevo = destino.getEstado(temp.getIdEstado() + increTrans);
            copiarTransiciones(destino, temp.getTransiciones(), eNuevo, increTrans);
        }
    }
    
    public static void copiarEstados(Automata origen, Automata destino, int increTrans){
        copiarEstados(origen, destino, increTrans, 0);
    }
    
    public Automata conversionAFD(Automata afn, Vector<String> simbolos){
        Automata afd = new Automata();
        int contador = 0;
        Simulacion simulador = new Simulacion();
        EstadoAFD estado = new EstadoAFD(contador++);
        ArrayList<Estado> inicial = new ArrayList<>();
        inicial.add(afn.getEInicial());
        estado.setDstates(simulador.eClosure(inicial));
        ArrayList<EstadoAFD> estadosD = new ArrayList<>();
        ArrayList<Estado> auxiliar = new ArrayList<>();
        Stack<EstadoAFD> pila = new Stack<>();
        
        estadosD.add(estado);
        auxiliar.add((Estado)estado);
        pila.addAll(estadosD);
        
        while(!pila.isEmpty()){
            EstadoAFD temp = pila.pop();
            for(String s: simbolos){
                EstadoAFD U = new EstadoAFD(contador++);
                U.setDstates(simulador.eClosure(simulador.move(temp.getDstates(), s)));
                if(!estadosD.contains(U)){
                    estadosD.add(U);
                    auxiliar.add((Estado)U);
                    pila.add(U);
                }
                else{
                    for(EstadoAFD a: estadosD){
                        if(a.equals(U)){
                            U = a;
                        }
                    }
                }
                Transicion transAFD = new Transicion();
                transAFD.setSimbolo(s);
                transAFD.setDestino(U);
                temp.getTransiciones().add(transAFD);
            }
        }
        //afd.setEInicial(estadosD.get(0));
        afd.setEstados(auxiliar);
        afd.setAlfabeto(simbolos);
        
        for(Estado eTemp: afd.getEstados()){
            if(((EstadoAFD)eTemp).getDstates().contains(afn.getConjuntoAceptacion().get(0))){
                eTemp.setEsFinal(true);
            }
        }
        
        int val = 0;
        for(Estado estD: afd.getEstados()){
            estD.idEstado = val;
            estD.etiqueta = Integer.toString(val);
            val++;
        }
        
        System.out.println("\n-----------------DESCRIPCION DE AFD-----------------");
        System.out.println("\n" + afd.toString());
        return afd;
    }
    
    @Override
    public String toString(){
        /*conjunto de estados*/
        Estado temp = new Estado();
        //String todosEstados = temp.toString(estados);
        
        /*estados de aceptacion*/
        //String estadosAccept = temp.toString(getConjuntoAceptacion());
        
        /*transiciones*/
        String todasTransiciones = "";
        Transicion transEst = new Transicion();
        for(Estado actual: estados){
            todasTransiciones += transEst.toString(actual);
        }
        return "ESTADOS = " + temp.toString(estados) + "\n" +
                "SIMBOLOS = " + alfabeto + "\n"  +
                "INICIO = " + getEInicial().toString() + "\n" +
                "ACEPTACION = " + temp.toString(getConjuntoAceptacion()) + "\n" +
                "TRANSICION = " + todasTransiciones + "\n";
    }
}