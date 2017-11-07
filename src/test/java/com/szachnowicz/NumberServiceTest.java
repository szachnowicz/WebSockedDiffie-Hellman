package com.szachnowicz;

import org.junit.Test;

import static org.junit.Assert.*;

public class NumberServiceTest
{
    @Test
    public void name() throws Exception {
        for (int i = 0; i <100 ; i++) {
            System.out.println(NumberService.getRandom());
        }
    }
}