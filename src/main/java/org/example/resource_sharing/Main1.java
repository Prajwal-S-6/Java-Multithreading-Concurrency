package org.example.resource_sharing;

public class Main1 {
    public static void main(String [] args) {
        SharedClass sharedObject1 = new SharedClass();
        SharedClass sharedObject2 = new SharedClass();

        Thread thread1 = new Thread(() -> {
            while (true) {
                sharedObject1.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {
                sharedObject2.increment();
            }
        });

        thread1.start();
        thread2.start();
    }

    static class SharedClass {
        private int counter = 0;

        // both thread1 and thread2 can execute concurrently because they are operating on different objects
        public synchronized void increment() {
            this.counter++;
        }
    }
}
