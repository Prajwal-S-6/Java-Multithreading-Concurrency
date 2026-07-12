package com.multithreathing.course260711;

class Worker {
    public static void task1() {
        System.out.println("trying task 1");
        synchronized (Worker.class)
        {
            try {
                System.out.println("entered task 1");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("finished task 1");


    }
    public synchronized static void task2() {
        System.out.println("trying task 2");
        synchronized (Worker.class)
        {
            System.out.println("entered task 2");
        }
        System.out.println("finished task 2");

    }
}
public class SynchronizationClassLevelLocking {

    static void main() throws InterruptedException {
        Runnable runnable1 = Worker::task1;
        Runnable runnable2 = Worker::task2;

        var t1 = new Thread(runnable1);
        var t2 = new Thread(runnable2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

}
