package com.example.user;

import com.example.compute.Calculator;
import com.example.random.RandomProvider;

import java.lang.reflect.Method;

public class Application {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        System.out.println(calculator.add(5, 3));
        RandomProvider randomProvider = calculator.getRandomProvider();
        System.out.println("Random Number is " + randomProvider.getRandomNumber());
        System.out.println("RandomProvider Implementation got is " + randomProvider.getClass());

        try {
            Method decimalMethod = randomProvider.getClass().getMethod("getRandomDecimalNumber");
            System.out.println(decimalMethod);
            System.out.println(decimalMethod.invoke(randomProvider));
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
