package com.multithreathing.course260711;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class DeadlockWork2 {
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

    public void task1() {
        while(true)
        {
            if(lock1.tryLock())
            {
                System.out.println("Acquired lock1" + Thread.currentThread().getName());
                try{
                    if(lock2.tryLock())
                    {
                        try{
                            System.out.println("Acquired lock2" + Thread.currentThread().getName());
                            System.out.println("Doing task1" + Thread.currentThread().getName());
                            break;
                        } finally {
                            lock2.unlock();
                        }
                    }

                } finally {
                    lock1.unlock();
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void task2() {
        while (true) {
            if (lock2.tryLock()) {
                System.out.println("Acquired lock2" + Thread.currentThread().getName());
                try {
                    if (lock1.tryLock()) {
                        try {
                            System.out.println("Acquired lock1" + Thread.currentThread().getName());
                            System.out.println("Doing task2" + Thread.currentThread().getName());
                            break;
                        } finally {
                            lock1.unlock();
                        }
                    }

                } finally {
                    lock2.unlock();
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
public class DeadlockSoultion2 {
    static void main() throws InterruptedException {
        DeadlockWork2 deadlockWork = new DeadlockWork2();
        Runnable r1 = () -> {
            for (int i = 0; i < 10; i++) {
                deadlockWork.task1();
            }
        };
        Runnable r2 = () -> {
            for (int i = 0; i < 10; i++) {
                deadlockWork.task2();
            }
        };

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

}
