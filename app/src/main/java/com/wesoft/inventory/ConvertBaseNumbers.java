package com.wesoft.inventory;

import java.math.BigInteger;

/**
 * Created by USER275 on 9/4/2017.
 */

public class ConvertBaseNumbers {

    public ConvertBaseNumbers(){}
    //bin
    public String decTobin(String decimal){
        return new BigInteger(decimal, 10).toString(2);
    }

    public String hexTobin(String hex){
        return new BigInteger(hex, 16).toString(2);
    }
    //Dec
    public String binToDec(String bin){
        return String.valueOf(Integer.parseInt(bin, 2));
    }

    public String hexToDec(String hexadecimal){
        return String.valueOf(Integer.parseInt(hexadecimal, 16));

    }
    //hex
    public String binTohex(String bin){
        String hex = new BigInteger(bin, 2).toString(16);
        return hex;
    }

    public String decTohex(String dec){
        return Integer.toHexString(Integer.parseInt(dec));
    }
}
