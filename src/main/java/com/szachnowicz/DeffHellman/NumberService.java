package com.szachnowicz.DeffHellman;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class NumberService {

    private static Random random;
    public static final int randomLimit = 1000;

    public static BigInteger getRandom() {
        if (random == null) {
            random = new Random();
        }
        int prime;
        do {
            prime = random.nextInt(randomLimit);
        } while (!isPrime(prime));


        return BigInteger.valueOf(prime);
    }


    public static boolean isPrime(int number) {
        for (int check = 2; check < number; ++check) {
            if (number % check == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Java 8 / Lambda approach to generate Prime number.
     * Prime always start to look from number 1.
     *
     * @param series Number of how many Prime number should be generated
     * @return List holding resulting Prime number.
     */
    public static List<Integer> generate(int series) {
        Set<Integer> set = new TreeSet<>();
        return Stream.iterate(1, i -> ++i)
                .filter(i -> i % 2 != 0)
                .filter(i -> {
                    set.add(i);
                    return 0 == set.stream()
                            .filter(p -> p != 1)
                            .filter(p -> !Objects.equals(p, i))
                            .filter(p -> i % p == 0)
                            .count();
                })
                .limit(series)
                .collect(toList());
    }

}
