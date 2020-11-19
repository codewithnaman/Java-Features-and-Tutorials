package com.example.random.impl;

import com.example.random.RandomProvider;

import java.util.Random;

public class RandomProviderImpl implements RandomProvider {
    private static final Random random = new Random();

    @Override
    public int getRandomNumber() {
        return random.nextInt();
    }

    public double getRandomDecimalNumber() {
        return random.nextDouble();
    }
}
