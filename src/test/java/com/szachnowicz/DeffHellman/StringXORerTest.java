package com.szachnowicz.DeffHellman;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringXORerTest {
    @Test
    public void name() throws Exception {

        StringXORer stringXORer = new StringXORer();

        final String message = "message";
        final String key = "412";

        final String encode = stringXORer.encode(message, key);
        System.out.println(encode);

        System.out.println(stringXORer.decode(encode,key));





    }
}