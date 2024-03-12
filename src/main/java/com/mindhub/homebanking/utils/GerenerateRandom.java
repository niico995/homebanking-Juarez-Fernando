package com.mindhub.homebanking.utils;

import java.util.Random;

public class GerenerateRandom {


    public static int cvv(){
        Random rand = new Random();
        int randomNumber = rand.nextInt(900) + 100;
        return randomNumber;
    }

    public static int number(){
        Random rand = new Random();
        int randomNumber = rand.nextInt(9000) +1000;
        return randomNumber;
    }
}
