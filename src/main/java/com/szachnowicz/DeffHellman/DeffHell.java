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

    public String getCalculMoudloJson() {
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
        json.put("mod", new StringXORer().encode(message,String.valueOf(encryKey)));
        return json.toString();

    }


    public String decodeMessage(String message) {
        JSONObject json = new JSONObject(message);
        final String mod = json.getString("mod");
        return new StringXORer().decode(mod, String.valueOf(encryKey));

    }


}
