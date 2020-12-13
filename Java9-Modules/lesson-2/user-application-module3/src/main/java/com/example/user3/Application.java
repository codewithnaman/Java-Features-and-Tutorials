package com.example.user3;

import com.exmaple.advance.AdvanceCalculator;

public class Application {

    public static void main(String[] args) {
        AdvanceCalculator advanceCalculator = new AdvanceCalculator();
        System.out.println("Square root of 4 is " + advanceCalculator.getSquareRoot(4));
        System.out.println("Sum of 5 and 4 is "+ advanceCalculator.getCalculator().add(5,4));
    }
}
