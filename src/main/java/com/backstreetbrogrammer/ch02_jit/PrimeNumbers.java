package com.backstreetbrogrammer.ch02_jit;

import java.util.ArrayList;
import java.util.List;

public class PrimeNumbers {

    private List<Integer> primes;

    private Boolean isPrime(final Integer testNumber) {
        for (int i = 2; i < testNumber; i++) {
            if (testNumber % i == 0) return false;
        }
        return true;
    }

    private Integer getNextPrimeAbove(final Integer previous) {
        Integer testNumber = previous + 1;
        while (!isPrime(testNumber)) {
            testNumber++;
        }
        return testNumber;
    }

    public void generateNumbers(final Integer max) {
        primes = new ArrayList<>();
        primes.add(2);

        Integer next = 2;
        while (primes.size() <= max) {
            next = getNextPrimeAbove(next);
            primes.add(next);
        }
        System.out.println(primes);
    }

}
