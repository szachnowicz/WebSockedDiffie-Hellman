package com.szachnowicz.DeffHellman;

import java.math.BigInteger;
import java.util.Random;

public class NumberService {

    public static BigInteger getRandom() {
        int nubers[] = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
        final int i = new Random().nextInt(nubers.length - 1);
        return BigInteger.valueOf(nubers[i]);
    }


}
