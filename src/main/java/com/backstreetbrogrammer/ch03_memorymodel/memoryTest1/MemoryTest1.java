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

    public void another(BlackBox blackbox1, final String newSecret) {
        newSecret.toLowerCase();
        blackbox1.setSecret("B");
        final BlackBox blackbox2 = new BlackBox();
        blackbox1 = blackbox2;
        System.out.print(blackbox1.getSecret());
        System.out.print(newSecret);
    }
}
