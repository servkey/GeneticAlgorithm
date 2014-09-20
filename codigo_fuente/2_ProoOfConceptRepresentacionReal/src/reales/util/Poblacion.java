/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package reales.util;

/**
 *
 * @author servkey
 */
public class Poblacion {
  public static void imprimirPoblacion(float[][] poblacion){
        for (int i = 0; i < poblacion.length; i++){
            for (int j = 0; j < poblacion[0].length; j++)
                System.out.print(poblacion[i][j] +  ",");
            System.out.println("");
        }
   }


  public static void imprimirPoblacion(int[][] poblacion){
        for (int i = 0; i < poblacion.length; i++){
            for (int j = 0; j < poblacion[0].length; j++)
                System.out.print(poblacion[i][j] +  ",");
            System.out.println("");
        }
   }


  public static void imprimirPoblacionVector(float[] poblacion){
        for (int i = 0; i < poblacion.length; i++){
            System.out.print((i == 0? "[":"") + poblacion[i] + (i == poblacion.length-1? "]":","));
            // System.out.print(poblacion[i] +  ",");
        }

   }

  public static boolean isIntoVector(int i, int[] vector){
        boolean result = false;
        for (int index = 0; index < vector.length; index++){
            if (vector[index] == i)
            {
                result = true;
                break;
            }
        }
        return result;
    }

    public int generateAleatorioDiferenteOf(int minimo, int maximo, int val){
        int result = 0;
        //while(){

        
        return result;
    }
    public static boolean isIntoMatrix(int i, int[][] matrix){
        boolean result = false;
        for (int index = 0; index < matrix.length; index++){
            for (int column = 0; column < matrix[0].length; column++){
                if (matrix[index][column] == i)
                {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }


    public static void initialize(int matrix[][], int val){
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
                matrix[i][j] = val;
        }
    }

    public static float[][] clone(float[][] matrix){
        float matrixClone[][] = new float[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++)
                matrixClone[i][j] = matrix[i][j];
            
        }
        return matrixClone;
    }

}
