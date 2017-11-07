package com.szachnowicz;

import com.szachnowicz.DeffHellman.NumberService;
import org.junit.Test;

public class NumberServiceTest
{
    @Test
    public void name() throws Exception {
        for (int i = 0; i <100 ; i++) {
            System.out.println(NumberService.getRandom());
        }
    }
}