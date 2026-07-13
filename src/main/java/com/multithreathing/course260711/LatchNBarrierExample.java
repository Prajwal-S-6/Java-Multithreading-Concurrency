package com.multithreathing.course260711;

import java.util.concurrent.*;

class Thread1 implements Runnable {

    private final CountDownLatch countDownLatch;
    private final CyclicBarrier cyclicBarrier;

    public Thread1(CountDownLatch countDownLatch, CyclicBarrier cyclicBarrier) {
        this.countDownLatch = countDownLatch;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
            System.out.println("Thread1 working ....");
            countDownLatch.countDown();

            cyclicBarrier.await();
            System.out.println("barrier tripped thread1 working again");
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}

class Thread2 implements Runnable {

    private final CountDownLatch countDownLatch;
    private final CyclicBarrier cyclicBarrier;

    public Thread2(CountDownLatch countDownLatch, CyclicBarrier cyclicBarrier) {
        this.countDownLatch = countDownLatch;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
            System.out.println("Thread2 working ....");
            countDownLatch.countDown();

            cyclicBarrier.await();
            System.out.println("barrier tripped thread2 working again");
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}

class Thread3 implements Runnable {

    private final CountDownLatch countDownLatch;
    private final CyclicBarrier cyclicBarrier;


    public Thread3(CountDownLatch countDownLatch, CyclicBarrier cyclicBarrier) {
        this.countDownLatch = countDownLatch;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            countDownLatch.await();
            System.out.println("Thread3 working ....");

            cyclicBarrier.await();
            System.out.println("barrier tripped thread3 working again");
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }
}

public class LatchNBarrierExample {

    static void main() {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(new Thread1(countDownLatch, cyclicBarrier));
        executorService.execute(new Thread2(countDownLatch, cyclicBarrier));
        executorService.execute(new Thread3(countDownLatch, cyclicBarrier));

        executorService.shutdown();

    }
}
