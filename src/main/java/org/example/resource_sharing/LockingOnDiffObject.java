package org.example.resource_sharing;

public class LockingOnDiffObject {
    public static void main(String [] args) {
        SharedClass sharedObject = new SharedClass();

        Thread thread1 = new Thread(() -> {
            while (true) {
                sharedObject.incrementCounter1();
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {
                sharedObject.incrementCounter2();
            }
        });

        thread1.start();
        thread2.start();
    }

    static class SharedClass {
        private int counter1 = 0;
        private int counter2 = 0;

        private Object lock1 = new Object();
        private Object lock2 = new Object();

        // when thread2 is executing incrementCounter2, then thread1 to can execute this method; because this is locked on different object
        public void incrementCounter1() {
            synchronized (lock1) {
                this.counter1++;
            }
        }

        // when thread1 is executing incrementCounter1, then thread2 to can execute this method; because this is locked on different object
        public void incrementCounter2() {
            synchronized (lock2) {
                this.counter2++;
            }
        }
    }
}
