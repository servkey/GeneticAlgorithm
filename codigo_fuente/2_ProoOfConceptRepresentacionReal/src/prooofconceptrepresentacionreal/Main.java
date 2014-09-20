/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package prooofconceptrepresentacionreal;

import EvolutivoReales.EvolutivoReales;
import java.text.DecimalFormat;
import java.util.Scanner;

/**
 *
 * @author servkey
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
           EvolutivoReales eb = new EvolutivoReales();
           Scanner br = new Scanner(System.in);

           System.out.println("***Algoritmo genético \n1) Representación reales***\n");
           System.out.print("Tamaño de población: " );
           eb.setPoblacionTamano(Integer.valueOf(br.nextInt()));
           System.out.print("Generaciones: " );eb.setMAXGEN(Integer.valueOf(br.nextInt()));
           System.out.print("Porcentaje de cruza: " );eb.setProbabilidadCruza(br.nextFloat());
           System.out.print("Porcentaje de mutación: " );eb.setProbabilidadMutacion(br.nextFloat());
           System.out.print("Porcentaje por torneo: " );eb.setProbabilidadTorneo(br.nextFloat());
           System.out.print("Semilla: " );eb.setSemilla(Float.valueOf(br.nextFloat()));
           System.out.println();
           eb.start();
    }
}
