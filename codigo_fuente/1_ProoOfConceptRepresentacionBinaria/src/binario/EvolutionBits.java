package binario;
import binario.util.BitsOperation;
import binario.util.Random;
import binario.util.TruncarDecimal;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author servkey
 */
public class EvolutionBits {
    private float[][] poblacion;
    private float[] aptitudes;    
    private int poblacionTamano = 200;
    private int[][] padres1;
    private int[][] padres2;
    private int[][] hijos;
    private float probabilidadCruza = 0.6f; //5 OK
    private float probabilidadMutacion = 0.3f;
    private float semilla = .2f;
    private float linf = -10f;
    private float lsup = 10f;
    private int individuoTamano = 10;
    private int MAXGEN = 1250;
    //private int evaluaciones = 0;
    private int longitudCadena = 0;
    private int precision = 2;

    //Para representación binaria
    private int genotipo[][];

    public void start(){
        Random.setRseed(semilla);
        longitudCadena = BitsOperation.calculateLongitudCadena(linf, lsup, precision);
        poblacion = new float[this.poblacionTamano][this.individuoTamano];

        int generaciones = 0;
        iniciarPoblacion();
        //Calcular aptitudes de poblacion inicial
        this.aptitudes = this.calcularAptitudes(poblacion);
        while(generaciones < MAXGEN){
            seleccion();
            cruza();
            mutacionSimple();
            reemplazo();
            System.out.println("Gen " + generaciones);
            BitsOperation.imprimirPoblacionVector(poblacion[this.elegirMejorIndividuo(aptitudes)]);
            System.out.println(" f(x) = " + TruncarDecimal.truncateRnd(this.aptitudes[this.elegirMejorIndividuo(aptitudes)]));
            generaciones++;
        }

        //BitsOperation.imprimirPoblacionVector(poblacion[this.elegirMejorIndividuo(aptitudes)]);
        //System.out.println(" f(x) = " + TruncarDecimal.truncate(this.aptitudes[this.elegirMejorIndividuo(aptitudes)]));
        
        System.out.println("Número de evaluaciones = " + (MAXGEN * this.poblacionTamano ));
        System.out.println("Número de generaciones = " + generaciones);
    }

    public EvolutionBits(){
    }

    private int[] generarIndividuo(){
        int[] individuo = new int[this.longitudCadena * this.individuoTamano];
        for (int i = 0; i < individuo.length; i++)
            individuo[i] = Random.rnd(0, 1);
        return individuo;
    }

    public void iniciarPoblacion(){
        Random.randomize();
        genotipo = new int [this.getPoblacionTamano()][longitudCadena * this.individuoTamano];
        int i = 0;        

        while (i < getPoblacionTamano()){                
                genotipo[i] =  generarIndividuo();
                i++;
         }
         poblacion = poblacionToFenotipo(genotipo);
   }

   private float[][] poblacionToFenotipo(int [][] genotipo){
       float[][] poblacion = new float[this.getPoblacionTamano()][this.individuoTamano];
        for (int index0 = 0; index0 < genotipo.length; index0++)
            poblacion[index0] = this.GenotipoToFenotipo(genotipo[index0]);
        return poblacion;
   }

   private float[] GenotipoToFenotipo(int[] genotipo){
        float[] fenotipo = new float[this.individuoTamano];
        int indexGenotipo = 0;
        int[] individuoGenotipo = new int[longitudCadena];

        for(int index = 0; index < this.individuoTamano; index++){
                System.arraycopy(genotipo, indexGenotipo, individuoGenotipo, 0, longitudCadena);
                indexGenotipo = index * this.longitudCadena;
                fenotipo[index] = TruncarDecimal.truncate(BitsOperation.codificacion(individuoGenotipo, linf, lsup));
                //fenotipo[index] = BitsOperation.codificacion(individuoGenotipo, linf, lsup);
        }
        return fenotipo;
    }

   public float aptitud(float[] vector){
        float result = 0;
        //evaluaciones++;
        //elevar al cuadrado cada elemento y sumarlos
        for (int i = 0; i < vector.length; i++){
            result += (vector[i] * vector[i]);
        }
        return result;
    }

    public void seleccion(){        
        float[] aptitudes = new float[genotipo.length];
        padres1 = new int[this.getPoblacionTamano()/2][genotipo[0].length];
        padres2 = new int[this.getPoblacionTamano()/2][genotipo[0].length];
        
        //Como es minimización se multiplica x -1, copiar aptitudes
        System.arraycopy(this.aptitudes, 0, aptitudes, 0, this.aptitudes.length);
        BitsOperation.multForInteger(aptitudes, -1);

        //Recorrer a número positivos
        BitsOperation.recorrerAptitudes(aptitudes);        
        float sumaAptitud  = BitsOperation.sumarVector(aptitudes);        
        float mediaAptitud = sumaAptitud / aptitudes.length;        
        int N = this.getPoblacionTamano();
        int iterator = 0;        
        float[] VE = new float[aptitudes.length];
        float sumaVE = 0;

        //Calcular valores esperados
        for (int i = 0;  i < VE.length; i++)
            VE[i] = aptitudes[i] / mediaAptitud;

        sumaVE = BitsOperation.sumarVector(VE);
        float acumulador = 0;
        boolean padre1 = true;
        int indexPadre1 = 0;
        int indexPadre2 = 0;
        while (iterator < N){
            float r = Random.rndreal(0, sumaVE);
            acumulador = 0;
            for (int i = 0; i < aptitudes.length; i++){
                acumulador += VE[i];
                if (acumulador >= r){
                    if (padre1){
                        System.arraycopy(genotipo[i], 0, padres1[indexPadre1++],0,genotipo[i].length);            
                        padre1 = false;
                    }else{
                        System.arraycopy(genotipo[i], 0, padres2[indexPadre2++],0,genotipo[i].length);                        
                        padre1 = true;
                    }
                    break;
                }
            }
            iterator++;
        }
   }

    //cruzaDosPuntos
    public void cruza(){
        hijos = new int[this.getPoblacionTamano()][this.longitudCadena * this.individuoTamano];

        int indexHijo1 = 0;
        int indexHijo2 = indexHijo1 + 1;
        for (int index = 0; index < padres1.length; index++){
            hijos[indexHijo1] = padres1[index];
            hijos[indexHijo2] = padres2[index];

            //Si probabilidad es 1 entonces aplica cruza de dos puntos
            if (Random.flip(this.getProbabilidadCruza()) == 1){
                int ktmp0 =  Random.rnd(0, (this.longitudCadena * 10) - 1);
                
                int ktmp1 =  BitsOperation.generarAleatorioDiferente(ktmp0,0, (this.longitudCadena * 10) - 1);
                
                int k1,k2;

                if (ktmp0 > ktmp1){
                    k1 = ktmp1;
                    k2 = ktmp0;
                }else{
                    k1 = ktmp0;
                    k2 = ktmp1;
                }
                for (int ik = k1; ik <= k2;ik++){
                    //intercambio
                    int aux1 = hijos[indexHijo1][ik];
                    int aux2 = hijos[indexHijo2][ik];
                    hijos[indexHijo1][ik] = aux2;
                    hijos[indexHijo2][ik] = aux1;
                }
            }
            indexHijo1 += 2;
            indexHijo2 = indexHijo1 + 1;
        }
    }

    public void mutacionSimple(){
        for (int i = 0; i < hijos.length ; i++){
             if (Random.flip(this.getProbabilidadMutacion()) == 1){
                 int k = Random.rnd(0, (this.longitudCadena * 10) - 1);
                 hijos[i][k] = hijos[i][k] == 1? 0: 1;
             }
        }
    }

    public void reemplazo(){
        //Recorrer los hijos
        float[] aptitudesHijos = new float[this.getPoblacionTamano()];
        float[][] hijosFenotipo = this.poblacionToFenotipo(hijos);
        aptitudesHijos = calcularAptitudes(hijosFenotipo);
        int indexPadreMejor = elegirMejorIndividuo(aptitudes);
        int indexHijoPeor =  elegirPeorIndividuo(aptitudesHijos);
        
        hijos[indexHijoPeor] = genotipo[indexPadreMejor];
        hijosFenotipo[indexHijoPeor] = poblacion[indexPadreMejor];
        aptitudesHijos[indexHijoPeor] = aptitudes[indexPadreMejor];

        genotipo = hijos;
        poblacion = hijosFenotipo;
        aptitudes = aptitudesHijos;
    }


    private float[] calcularAptitudes(float[][] vector){
        float aptitudes[] = new float[this.getPoblacionTamano()];
        for (int i = 0; i < vector.length; i++)
            aptitudes[i] = aptitud(vector[i]);                    
        return aptitudes;
    }

    public int elegirPeorIndividuo(float[] vector){
        int index = 0;
        for (int i = 1; i < vector.length; i++){
            if (vector[i] > vector[index]){
                index = i;
            }
        }
        return index;
    }

    public int elegirMejorIndividuo(float[] vector){
        int index = 0;        
        for (int i = 1; i < vector.length; i++){
            if (vector[i] < vector[index]){
                index = i;
            }
        }
        return index;
    }

    /**
     * @return the poblacionTamano
     */
    public int getPoblacionTamano() {
        return poblacionTamano;
    }

    /**
     * @param poblacionTamano the poblacionTamano to set
     */
    public void setPoblacionTamano(int poblacionTamano) {
        this.poblacionTamano = poblacionTamano;
    }

    /**
     * @return the probabilidadCruza
     */
    public float getProbabilidadCruza() {
        return probabilidadCruza;
    }

    /**
     * @param probabilidadCruza the probabilidadCruza to set
     */
    public void setProbabilidadCruza(float probabilidadCruza) {
        this.probabilidadCruza = probabilidadCruza;
    }

    /**
     * @return the probabilidadMutacion
     */
    public float getProbabilidadMutacion() {
        return probabilidadMutacion;
    }

    /**
     * @param probabilidadMutacion the probabilidadMutacion to set
     */
    public void setProbabilidadMutacion(float probabilidadMutacion) {
        this.probabilidadMutacion = probabilidadMutacion;
    }

    /**
     * @return the semilla
     */
    public float getSemilla() {
        return semilla;
    }

    /**
     * @param semilla the semilla to set
     */
    public void setSemilla(float semilla) {
        this.semilla = semilla;
    }

    /**
     * @return the MAXGEN
     */
    public int getMAXGEN() {
        return MAXGEN;
    }

    /**
     * @param MAXGEN the MAXGEN to set
     */
    public void setMAXGEN(int MAXGEN) {
        this.MAXGEN = MAXGEN;
    }

}
