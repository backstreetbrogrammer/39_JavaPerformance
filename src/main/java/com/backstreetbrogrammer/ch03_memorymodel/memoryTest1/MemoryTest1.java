package com.backstreetbrogrammer.ch03_memorymodel.memoryTest1;

public class MemoryTest1 {

    public static void main(final String[] args) {
        final MemoryTest1 main = new MemoryTest1();
        main.start();
    }

    public void start() {
        final String last = "Z";
        final BlackBox blackBox = new BlackBox();
        blackBox.setSecret("C");
        another(blackBox, last);
        System.out.print(blackBox.getSecret());
    }

    public void another(BlackBox initialSecret, final String newSecret) {
        newSecret.toLowerCase();
        initialSecret.setSecret("B");
        final BlackBox initial2 = new BlackBox();
        initialSecret = initial2;
        System.out.print(initialSecret.getSecret());
        System.out.print(newSecret);
    }
}
