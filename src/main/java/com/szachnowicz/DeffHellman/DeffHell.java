package com.szachnowicz.DeffHellman;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

public class DeffHell {
    private BigInteger p, g;
    private BigInteger secretNumber;
    private BigInteger calculMoudlo;
    private BigInteger encryKey;


    public DeffHell(int p, int g) {
        this.p = BigInteger.valueOf(p);
        this.g = BigInteger.valueOf(g);
        this.secretNumber = NumberService.getRandom();
        ;
    }

    public DeffHell() {
        // SERVER CONSTRUCN

        p = NumberService.getRandom();
        g = NumberService.getRandom();
        this.secretNumber = NumberService.getRandom();
    }


    public String getKey() {
        JSONObject json = new JSONObject();
        json.put("request", "publicKey");
        json.put("p", p);
        json.put("g", g);

        return json.toString();
    }


    public BigInteger getCalculMoudlo() {
        calculMoudlo = g.modPow(secretNumber, p);
        return calculMoudlo;
    }

    public String getcalculMoudloJson() {
        JSONObject json = new JSONObject();
        json.put("request", "moduloPublicModulo");
        json.put("mod", getCalculMoudlo());

        return json.toString();
    }


    public BigInteger getEncryKey() {
        return encryKey;
    }

    public void setEncryKey(BigInteger mod) {
        encryKey = mod.modPow(secretNumber, p);
    }

    public String codeMessage(String message) {


        JSONObject json = new JSONObject();
        json.put("request", "message");

        json.put("mod", Arrays.toString(encrypt(message,String.valueOf(encryKey))));

        return json.toString();

    }


    public String decodeMessage(String message) {

        return decrypt(string2Arr(message), String.valueOf(encryKey));

    }



    private static String decrypt(int[] input, String key) {
        String output = "";
        for(int i = 0; i < input.length; i++) {
            output += (char) ((input[i] - 48) ^ (int) key.charAt(i % (key.length() - 1)));
        }
        return output;
    }

    private static int[] encrypt(String str, String key) {
        int[] output = new int[str.length()];
        for(int i = 0; i < str.length(); i++) {
            int o = (Integer.valueOf(str.charAt(i)) ^ Integer.valueOf(key.charAt(i % (key.length() - 1)))) + '0';
            output[i] = o;
        }
        return output;
    }


    private static int[] string2Arr(String str) {
        String[] sarr = str.split(",");
        int[] out = new int[sarr.length];
        for (int i = 0; i < out.length; i++) {
            out[i] = Integer.valueOf(sarr[i]);
        }
        return out;
    }
}
