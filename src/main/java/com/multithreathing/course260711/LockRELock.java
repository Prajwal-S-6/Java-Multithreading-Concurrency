package com.multithreathing.course260711;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Work {
    public Lock lock = new ReentrantLock();

    public void task1() {
        lock.lock();
        try {
            System.out.println("Got lock1, performing task1...");
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
    public void task2() {
        while (true)
        {
                if(lock.tryLock()){
                    try {
                        System.out.println("Got lock1, performing task2...");
                        break;
                    }
                    finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println("Doing other work meanwhile...");
                }
        }

    }
}
public class LockRELock {

    static void main() throws InterruptedException {
        Work work = new Work();
        Runnable r1 = work::task1;
        Runnable r2 = work::task2;

        var t1 = new Thread(r1);
        var t2 = new Thread(r2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

    }
}
