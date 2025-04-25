package org.example.resource_sharing;

public class RaceCondition2 {
    public static void main(String [] args) throws InterruptedException {
        SharedClass sharedObject1 = new SharedClass();
        SharedClass sharedObject2 = new SharedClass();

        Thread thread1 = new Thread(() -> {
            for (int i=0; i<10000; i++) {
                sharedObject1.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i=0; i<10000; i++) {
                sharedObject1.increment();   //change it to sharedObject1 and see
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("We have " + sharedObject1.getCounter() + " on" + sharedObject1);
        System.out.println("We have " + sharedObject2.getCounter() + " on" +  sharedObject2);
    }

    static class SharedClass {
        private volatile int counter = 0;

        // mark as synchronized and observe
        public void increment() {
            this.counter++;
        }

        public int getCounter() {
            return counter;
        }
    }
}
