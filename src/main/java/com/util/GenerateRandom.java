package com.util;

import java.util.Random;

public class GenerateRandom {

    public static String generate(int length){
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }
}
