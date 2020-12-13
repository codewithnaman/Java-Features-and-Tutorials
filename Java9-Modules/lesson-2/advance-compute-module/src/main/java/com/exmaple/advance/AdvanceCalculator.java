package com.exmaple.advance;

import com.example.compute.Calculator;

public class AdvanceCalculator {

    public Calculator getCalculator(){
        return new Calculator();
    }

    public double getSquareRoot(double number){
        return Math.sqrt(number);
    }
}
