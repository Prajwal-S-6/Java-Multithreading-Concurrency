package com.multithreathing.course260711;

class Task {
    int counter;

    public synchronized void increment() {
        this.counter++;
    }
}
public class Synchronization {
    static void main() throws InterruptedException {
        Task task = new Task();
        Runnable increment = () -> {
            for (int i = 0; i < 1000; i++) {
                task.increment();
            }
        };
        var thread1 = new Thread(increment);
        var thread2 = new Thread(increment);

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(task.counter);
    }
}
