package com.multithreathing.course260711;

import java.time.LocalDateTime;

class Task {
    int counter;
    int counter2;
    Object object =  new Object();
    Object object2 = new Object();
    public void increment() throws InterruptedException {
        synchronized (object)
        {
            counter++;
            Thread.sleep(100);
        }
    }
    public void increment2() throws InterruptedException {
        synchronized (object)
        {
            counter2++;
            Thread.sleep(100);
        }
    }
}
public class Synchronization {
    static void main() throws InterruptedException {
        Task task = new Task();
        Runnable increment = () -> {

                try {
                    for (int i = 0; i < 1000; i++) {
                        task.increment();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
        };
        Runnable increment2 = () -> {
            try {
                for (int i = 0; i < 1000; i++) {
                    task.increment2();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        var thread1 = new Thread(increment);
        var thread2 = new Thread(increment2);
        long start = System.currentTimeMillis();
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        var end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start));
        System.out.println(task.counter);
        System.out.println(task.counter2);
    }
}
