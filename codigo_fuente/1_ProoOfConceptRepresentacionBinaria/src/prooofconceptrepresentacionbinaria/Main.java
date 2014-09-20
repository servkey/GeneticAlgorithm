/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prooofconceptrepresentacionbinaria;

import binario.EvolutionBits;
import binario.util.BitsOperation;
import binario.util.TruncarDecimal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 *
 * @author servkey
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
       Scanner br = new Scanner(System.in);
       EvolutionBits eb = new EvolutionBits();            
       System.out.println("***Algoritmo genético \n1) Representación binaria***\n");
       System.out.print("Tamaño de población: " );
       eb.setPoblacionTamano(Integer.valueOf(br.nextInt()));
       System.out.print("Generaciones: " );eb.setMAXGEN(Integer.valueOf(br.nextInt()));
       System.out.print("Porcentaje de cruza: " );eb.setProbabilidadCruza(br.nextFloat());
       System.out.print("Porcentaje de mutación: " );eb.setProbabilidadMutacion(br.nextFloat());
       System.out.print("Semilla: " );eb.setSemilla(Float.valueOf(br.nextFloat()));
       System.out.println();
       eb.start();       

    }

}
