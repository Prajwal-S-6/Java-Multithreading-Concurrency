package com.multithreathing.course260711;

import java.util.ArrayList;
import java.util.List;

public class RegularCollectionProblem {

    static void main() {
        List<Integer> list = new ArrayList<>();
        Runnable r1 = () -> {
            for (int i = 0; i < 100; i++) {
                list.add(1);
            }
        };

        Thread thread = new Thread(r1);
        Thread thread1 = new Thread(r1);
        thread.start();
        thread1.start();

        try {
            thread.join();
            thread1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("List size: " + list.size());
    }
}
