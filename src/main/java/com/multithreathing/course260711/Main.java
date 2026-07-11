package com.multithreathing.course260711;

class Worker1 implements Runnable {

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000);
                System.out.println("Runner 1: " + i);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Worker2 extends Thread{

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
                System.out.println("Runner 2: " + i);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Daemon implements Runnable {
    @Override
    public void run() {
        while(true)
        {
            try {
                Thread.sleep(1000);
                System.out.println("Daemon thread running...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
public class Main {

    public static void main(String[] args) throws InterruptedException {
        //Worker thread; jvm doesnt terminate until all worker thread terminates
        var thread1 = new Thread(new Worker1());    // Runnable interface
        var thread2 = new Thread(new Worker2());    // Thread class extend
        var thread3 = new Thread(new Runnable() {   // Runnable anonymous class
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Runner 3: " + i);
                }
            }
        });
        Runnable worker4 = () -> {                  // Lambda syntax
            for (int i = 0; i < 10; i++) {
                System.out.println("Runner 4: " + i);
            }
        };
        var thread4 = new Thread(worker4);


        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        //Daemon thread; jvm terminates when all worker thread terminates, even though daemon thread is running
        var daemonThread = new Thread(new Daemon());
        daemonThread.setDaemon(true);
        daemonThread.start();

        thread3.join();
        thread2.join();

        System.out.println("Main thread finished!!!");
    }
}
