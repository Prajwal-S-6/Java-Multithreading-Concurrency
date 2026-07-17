package multithreading.course260711;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class DeadlockWork1 {
    private final Lock lock1 = new ReentrantLock();
    private final Lock lock2 = new ReentrantLock();

    public void task1() {
        try {
            lock1.lock();
            System.out.println("Doing task1");
            Thread.sleep(1000);
            lock2.lock();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock1.unlock();
            lock2.unlock();
        }
    }
    public void task2() {
        try {
            lock1.lock();
            lock2.lock();
            System.out.println("Doing task2");
        } finally {
            lock2.unlock();
            lock1.unlock();
        }
    }

}
public class DeadlockSoultion {
    static void main() throws InterruptedException {
        DeadlockWork1 deadlockWork = new DeadlockWork1();
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
