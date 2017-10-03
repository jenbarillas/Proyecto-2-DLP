
package Fase2;

import java.io.*;
import java.io.FileReader;
import java.io.BufferedReader;

import java.util.*;

public class Main {
    
    public static void main(String[] args) throws Exception{
        FileReader file = new FileReader("C:/Users/Jennifer/Documents/NetBeansProject/GeneradorLexer/src/Pruebas/archivo.txt");
        BufferedReader br = new BufferedReader(file);
        try (Scanner scanner = new Scanner (br)) {
            Analizador sin = new Analizador(scanner);
            sin.Cocol();
        }        
    }   
}
