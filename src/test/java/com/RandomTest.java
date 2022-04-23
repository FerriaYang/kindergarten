package com;

import org.junit.Test;

import java.util.Random;

public class RandomTest {

    @Test
    public void test(){
        String val = "";
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        System.out.println(val);
    }
}
