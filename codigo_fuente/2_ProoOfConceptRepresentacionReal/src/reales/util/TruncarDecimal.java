/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package reales.util;

import java.text.DecimalFormat;

/**
 *
 * @author servkey
 */
public class TruncarDecimal {
    public static float truncate(float number){
        float res = 0;
        if (number != 0){
            res = number * 100;
            res = (((int)(res)) / 100.0f);
        }
        return res;
    }

    public static String truncateRnd(float number){
            DecimalFormat twoDForm = new DecimalFormat("####.####");
            return twoDForm.format(number);
    }
//		System.out.println(Double.valueOf(twoDForm.format(4.2395)));
}
