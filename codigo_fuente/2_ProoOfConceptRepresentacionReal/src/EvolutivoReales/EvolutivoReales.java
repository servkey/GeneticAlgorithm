/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EvolutivoReales;

import reales.util.Random;
import reales.util.TruncarDecimal;
import reales.util.*;


/**
 *
 * @author servkey
 */
public class EvolutivoReales {
    private float[][] poblacion;
    private float[][] poblacionSeleccionada;
    private int poblacionTamano = 10;
    private float[][] padres1;
    private float[][] padres2;
    private float[][] hijos;
    private float probabilidadTorneo = 0.3f;
    private float probabilidadMutacion = 0.96f;
    private float probabilidadCruza = 0.45f;
    private float semilla = 0.1f;
    private float l = -10;
    private float u = 10;
    private int individuoTamano = 10;
    private int MAXGEN = 25000;
    
    public EvolutivoReales(){
        
    }

    public void start(){
        Random.setRseed(semilla);
        poblacion = new float[poblacionTamano][individuoTamano];
        poblacionSeleccionada = new float[poblacionTamano][individuoTamano * 2];
        int generaciones = 0;
        generarPoblacion();
        while(generaciones < MAXGEN){
            seleccion();
            cruzaAritmeticaSimple();
            mutacionUniforme();
            reemplazo();
            System.out.println("Gen " + generaciones);
            Poblacion.imprimirPoblacionVector(poblacion[this.elegirMejorIndividuo(poblacion)]);
            System.out.println(" f(x) = " + TruncarDecimal.truncateRnd(this.aptitud(poblacion[this.elegirMejorIndividuo(poblacion)])));
            generaciones++;
        }

        //System.out.println("Gen " + generaciones);
        //Poblacion.imprimirPoblacionVector(poblacion[this.elegirMejorIndividuo(poblacion)]);
        //System.out.println(" f(x) = " + TruncarDecimal.truncate(this.aptitud(poblacion[this.elegirMejorIndividuo(poblacion)])));
        System.out.println("Número de evaluaciones = " + (this.MAXGEN * this.poblacionTamano));
        System.out.println("Número de generaciones = " + generaciones);
        //Poblacion.imprimirPoblacion(poblacion);
    }

    public void generarPoblacion(){
        int i = 0;
        Random.randomize();
        while (i < getPoblacionTamano()){
            for (int j = 0; j < poblacion[0].length; j++){
                poblacion[i][j] = TruncarDecimal.truncate(Random.rndreal(l, u));                
            }
            i++;
        }
    //        System.out.println("Población inicial");
    //      Poblacion.imprimirPoblacion(poblacion);
    }

    public void seleccion(){
        //Clonar poblacion
        float[][] poblacion = Poblacion.clone(this.poblacion);


        poblacionSeleccionada = new float[this.getPoblacionTamano()*2][individuoTamano];
        //Generar el doble de la poblacion
        for (int i = 0; i < poblacionSeleccionada.length;i++){
            int individuoSeleccionado = Random.rnd(0, getPoblacionTamano() - 1);
            poblacionSeleccionada[i] = poblacion[individuoSeleccionado];
        }
        //System.out.println("Poblacion Generada al doble");
        //Poblacion.imprimirPoblacion(poblacionSeleccionada);

       //Generar parejas de la poblacion generada al doble
        int[][] matrixParejas = new int[getPoblacionTamano()][2];

        int[] matrixParejaSeleccionada = new int[getPoblacionTamano()];
        //Inicializar matrix con -1
        Poblacion.initialize(matrixParejas, -1);
        //while(iteracion < maxIteraciones){
         for (int i = 0; i < matrixParejas.length;i++){
             for (int j = 0; j < matrixParejas[0].length;j++)
             {
                 while (true)
                {
                    int indexPadreSeleccionado = Random.rnd(0, poblacionSeleccionada.length - 1);
                    if (!Poblacion.isIntoMatrix(indexPadreSeleccionado, matrixParejas))
                    {
                        matrixParejas[i][j] = indexPadreSeleccionado;
                        break;
                    }
                }
            }
        }

        //Si flip(probabilidad) = 1 elemento columna 0, = 2 elemento columna 1
        for (int i = 0; i < matrixParejas.length; i++){            
            matrixParejaSeleccionada[i] = matrixParejas[i][Random.flip(getProbabilidadTorneo())];
        }
        float[][] padre10seleccionados = new float[matrixParejaSeleccionada.length][this.individuoTamano];

        for (int i = 0; i < matrixParejaSeleccionada.length; i++)
            padre10seleccionados[i] = poblacionSeleccionada[matrixParejaSeleccionada[i]];



        //Generar parejas de la poblacion generada de 10
        matrixParejas = new int[getPoblacionTamano()/2][2];

        //Inicializar matrix con -1
        Poblacion.initialize(matrixParejas, -1);
        for (int i = 0; i < matrixParejas.length;i++){
             for (int j = 0; j < matrixParejas[0].length;j++)
             {
                 while (true)
                {
                    int indexPadreSeleccionado = Random.rnd(0, padre10seleccionados.length - 1);
                    if (!Poblacion.isIntoMatrix(indexPadreSeleccionado, matrixParejas))
                    {
                        matrixParejas[i][j] = indexPadreSeleccionado;
                        break;
                    }
                }
            }
        }

       // System.out.println("Padres Seleccionados en parejas de 5 o mitad");

        padres1 = new float[matrixParejas.length][this.individuoTamano];
        padres2 = new float[matrixParejas.length][this.individuoTamano];

        for (int i = 0; i < matrixParejas.length; i++)
        {
           padres1[i] = padre10seleccionados[matrixParejas[i][0]];
           padres2[i] = padre10seleccionados[matrixParejas[i][1]];
        }
       
    }


    public void cruzaAritmeticaSimple(){
        //Calcular a
        //Iterar las parejas seleccionadas
        int numeroParejas = padres1.length;
        hijos = new float[this.getPoblacionTamano()][this.individuoTamano];
        int filaHijo = 0;
        int columnaHijo = 0;
        for (int i = 0; i < numeroParejas; i++)
        {
            //Primera pareja
            //Iterar elementos de los padres

            if (Random.flip(this.getProbabilidadCruza()) == 1){
                for (int elemento = 0; elemento < individuoTamano; elemento++){
                //Primer padre
                 
                    float alfa = Cruza.alfa(l,padres1[i][elemento],padres2[i][elemento]);
                    float beta = Cruza.beta(u,padres1[i][elemento],padres2[i][elemento]);
                    float gamma = Cruza.gamma(l,padres1[i][elemento],padres2[i][elemento]);
                    float sigma = Cruza.sigma(u,padres1[i][elemento],padres2[i][elemento]);

                    float limiteInferior = 0;
                    float limiteSuperior = 0;

                    if (padres1[i][elemento] < padres2[i][elemento])
                    {
                        limiteInferior = Cruza.max(gamma, sigma);
                        limiteSuperior = Cruza.min(alfa, beta);
                    }else if (padres1[i][elemento] > padres2[i][elemento]){
                        limiteInferior = Cruza.max(alfa, beta);
                        limiteSuperior = Cruza.min(gamma, sigma);
                    }else{
                        //Queda cero
                    }

                    float a = Random.rndreal(limiteInferior, limiteSuperior);

                    //Calcular elemento para hijos
                    hijos[filaHijo][columnaHijo] = TruncarDecimal.truncate((padres2[i][elemento] * a ) + (padres1[i][elemento] * (1 - a)));
                    hijos[filaHijo + 1][columnaHijo++] = TruncarDecimal.truncate((padres1[i][elemento] * a ) + (padres2[i][elemento] * (1 - a)));
                 }
            }else{
                    hijos[filaHijo] = padres1[i];
                    hijos[filaHijo + 1] = padres2[i];
           }

           filaHijo = filaHijo + 2;
           columnaHijo = 0;
        }
        //System.out.println("Hijos");
        //Poblacion.imprimirPoblacion(hijos);
    }

    public void mutacionUniforme(){
        for (int i = 0; i < hijos.length ; i++){
            int k = Random.rnd(0, individuoTamano - 1);
            float reemplazoValor = TruncarDecimal.truncate(Random.rndreal(l, u));
            if (Random.flip(this.getProbabilidadMutacion()) == 1)
                hijos[i][k] = reemplazoValor;

        }
        //System.out.println("Hijos mutados");
        //Poblacion.imprimirPoblacion(hijos);

    }
   

    public void reemplazo(){
        int mejorPadre = elegirMejorIndividuo(poblacion);
        int peorHijo = elegirPeorIndividuo(hijos);        
        hijos[peorHijo] = poblacion[mejorPadre];
        poblacion = hijos;        
    }

    public float aptitud(float[] vector){
        float result = 0;
        //elevar al cuadrado cada elemento y sumarlos
        for (int i = 0; i < vector.length; i++){
            result += (vector[i] * vector[i]);
        }
        return result;
    }


    public int elegirPeorIndividuo(float[][] vector){
        int index = 0;
        float aptitudes[] = new float[this.getPoblacionTamano()];
        //float aptitudes[] = new float[vector[0].length];
        for (int i = 0; i < vector.length; i++)
            aptitudes[i] = aptitud(vector[i]); 

        for (int i = 1; i < aptitudes.length; i++)
            if (aptitudes[i] > aptitudes[index])
                index = i;
            
        
        return index;
    }

    public int elegirMejorIndividuo(float[][] vector){
        int index = 0;
        float aptitudes[] = new float[this.getPoblacionTamano()];
        //float aptitudes[] = new float[vector[0].length];
        for (int i = 0; i < vector.length; i++)
            aptitudes[i] = aptitud(vector[i]);

        for (int i = 1; i < aptitudes.length; i++){
            if (aptitudes[i] < aptitudes[index]){
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
     * @return the probabilidadTorneo
     */
    public float getProbabilidadTorneo() {
        return probabilidadTorneo;
    }

    /**
     * @param probabilidadTorneo the probabilidadTorneo to set
     */
    public void setProbabilidadTorneo(float probabilidadTorneo) {
        this.probabilidadTorneo = probabilidadTorneo;
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
