package com.backstreetbrogrammer.ch03_memorymodel;

import java.util.ArrayList;
import java.util.List;

public class StackAndHeap {

    public static void main(final String[] args) {
        final List<String> list = new ArrayList<>();
        list.add("One");
        list.add("Two");
        list.add("Three");

        print(list);

        System.out.printf("main list: %s%n", list);
    }

    private static void print(final List<String> list) {
        final String value = list.get(1);
        list.add("Four");
        System.out.println(value);
    }

}
