package com.multithreathing.course260711;

class Producer implements Runnable {

    private final Object lock;

    public Producer(Object object) {
        this.lock = object;
    }

    @Override
    public void run() {
        synchronized (lock) {
            try {

                System.out.println("Completed adding items, waiting...");
                lock.wait();
                System.out.println("Adding items...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Consumer  implements Runnable {

    private final Object lock;

    public Consumer(Object object) {
        this.lock = object;
    }

    @Override
    public void run() {
        synchronized (lock)
        {
            try {
                Thread.sleep(1000);
                System.out.println("Completed removing items, waiting...");
                lock.notify();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
public class WaitNotify {
    static void main() throws InterruptedException {
        Object o = new Object();
        var t1 = new Thread(new Producer(o));
        var t2 = new Thread(new Consumer(o));

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
