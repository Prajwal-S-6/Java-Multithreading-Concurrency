package org.example.thread_coordination;

import java.math.BigInteger;

public class Main1 {

    public static void main(String[] args) throws InterruptedException {
        ComplexCalculation complexCalculation = new ComplexCalculation();
        BigInteger result = complexCalculation.calculateResult(new BigInteger("100"), new BigInteger("2"), new BigInteger("100"), new BigInteger("2"));
        System.out.println(result);
    }
}
