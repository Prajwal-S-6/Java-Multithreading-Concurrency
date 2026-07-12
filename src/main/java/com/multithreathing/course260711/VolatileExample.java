package com.multithreathing.course260711;

class Example1 implements Runnable {

    private volatile boolean flag = true;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        while (flag) {
            // doing some work
        }
        System.out.println("Done!!!");
    }
}
public class VolatileExample {

    static void main() throws InterruptedException {
        Example1 example1 = new Example1();
        Thread t1 = new Thread(example1);
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                example1.setFlag(false);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

    }

}
