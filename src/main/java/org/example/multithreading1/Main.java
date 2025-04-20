package org.example.multithreading1;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        //to thread object constructor pass the class which implements Runnable interface
        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // this code executes on new thread
                System.out.println("This is from " + Thread.currentThread().getName());
            }
        });

        newThread.setPriority(Thread.MIN_PRIORITY);
        newThread.setName("New Thread");
        newThread.start();
        System.out.println("This is from thread " + Thread.currentThread().getName());
    }
}