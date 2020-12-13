package com.example.compute;

import com.example.random.RandomProvider;
import com.example.random.impl.RandomProviderImpl;

public class Calculator {
    public int add(int number1, int number2) {
        return number1 + number2;
    }

    public RandomProvider getRandomProvider() {
        return new RandomProviderImpl();
    }
}
