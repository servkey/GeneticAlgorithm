package EvolutivoReales;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author servkey
 */
public class Cruza {

    public static float alfa (float l, float v, float w){
        return ((l-w)/(v-w));
    }

    public static float beta(float u, float v, float w){
        return ((u-v)/(w-v));
    }

    public static float gamma(float l, float v, float w){
        return ((l-v)/(w-v));
    }

    public static float sigma(float u, float v, float w){
        return ((u-w)/(v-w));
    }

    public static float max(float a, float b){
        if (a>b)
            return a;
        else return b;
    }

    public static float min(float a, float b){
        if (a<b)
            return a;
        else return b;
    }
}