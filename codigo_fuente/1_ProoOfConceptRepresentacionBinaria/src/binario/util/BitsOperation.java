package binario.util;

public class BitsOperation {

    public static int calculateLongitudCadena(float limiteInferior, float limiteSuperior, int presicion){
        int L = 0;
        L = (int)(((Math.log((limiteSuperior-limiteInferior)*Math.pow(10,2)))/ Math.log(2)) + 0.9);
        return L;
    }

    public static float codificacion(int[] cadenaBinaria, float limiteInferior, float limiteSuperior){
        float xReal = 0;
        xReal = (float)(limiteInferior + ((convertToInteger(cadenaBinaria) * (limiteSuperior - limiteInferior))/ ((float)Math.pow(2,cadenaBinaria.length)-1)));
        return xReal;
    }

    public static double convertToInteger(int[] cadenaBinaria){
        double result = 0;
        double mult = 1;
        for (int i = cadenaBinaria.length -1; i >= 0; i--){
            result += cadenaBinaria[i] == 1? mult:0;
            mult *= 2;
        }
        return result;
    }


    public static double convertToIntegerDoble(int[] cadenaBinaria){
        double result = 0;
        double mult = 1;
        for (int i = cadenaBinaria.length -1; i >= 0; i--){
            result += cadenaBinaria[i] == 1? mult:0;
            mult *= 2;
        }
        return result;
    }

   public static void imprimirPoblacion(float[][] poblacion){
        for (int i = 0; i < poblacion.length; i++){
            for (int j = 0; j < poblacion[0].length; j++)
                System.out.print(poblacion[i][j] +  ",");
            System.out.println("");
        }
   }

    public static void imprimirPoblacion(int[][] poblacion){
        for (int i = 0; i < poblacion.length; i++){
            for (int j = 0; j < poblacion[i].length; j++)
                System.out.print(poblacion[i][j] +  ",");
            System.out.println("");
        }
   }

    public static void imprimirGenotipo(int[] genotipo){
        for (int i = 0; i < genotipo.length; i++){
            System.out.print(genotipo[i] +  ",");
        }
   }

    public static float sumarVector(float[] vector){
        float acumulador = 0;
        for (int i = 0; i < vector.length; i++)
            acumulador += vector[i];
        return acumulador;
    }


    public static boolean hasNegative(float[] vector){
        boolean flag = false;
        for (int i = 0; i < vector.length; i++)
            if (vector[i] < 0)
            {
                flag = true;
                break;
            }

        return flag;
    }

    public static int findSmallest(float[] vector){
        int index = 0;
        for (int i = 1; i < vector.length; i++){
            if (vector[i] < vector[index])
            {
                index = i;                
            }
        }
        return index;
    }

    public static void multForInteger(float[] vector, int multiplicador){
        for (int i = 0; i < vector.length; i++)
            vector[i] *= multiplicador;
    }

    public static int findBigest(float[] vector){
        int index = 0;
        for (int i = 0; i < vector.length; i++)
            if (vector[i] > vector[index])
            {
                index = i;
                
            }
        return index;
    }

   public static void recorrerAptitudes(float[] aptitudes){
        if ( BitsOperation.hasNegative(aptitudes)){
               float menorX2 = aptitudes[BitsOperation.findSmallest(aptitudes)] * 2;
               for (int i = 0; i < aptitudes.length; i++){
                    aptitudes[i] += Math.abs(menorX2);
               }
        }
   }
   
   public static int generarAleatorioDiferente(float diff, int limiteInf, int limiteSup){
        int result = 0;
        boolean diferente = true;
        while (diferente){
            result = Random.rnd(limiteInf, limiteSup);
            if (result != diff)
                break;
        }
        return result;
   }

   public static void imprimirPoblacionVector(float[] poblacion){
        for (int i = 0; i < poblacion.length; i++)
            System.out.print((i == 0? "[":"") + poblacion[i] + (i == poblacion.length-1? "]":","));
   }


}
