package com.backstreetbrogrammer.ch02_jit;

public class Main {

    public static void main(final String[] args) {
        final PrimeNumbers primeNumbers = new PrimeNumbers();
        final Integer max = Integer.parseInt(args[0]);
        primeNumbers.generateNumbers(max);
    }
}
