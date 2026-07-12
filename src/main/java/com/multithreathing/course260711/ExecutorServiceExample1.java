package com.multithreathing.course260711;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class Task1 {

    public void task(int i) {
        System.out.println("Doing task " + i + " " + Thread.currentThread().getName());
    }
}
public class ExecutorServiceExample1 {
    static void main() {
        //ExecutorService executorService = Executors.newSingleThreadExecutor();
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        ExecutorService executorService = Executors.newCachedThreadPool();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(6);
        Task1 task1 = new Task1();
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            /// we can use execute() or submit() with Runnable type
            /// we can only use submit() with Callable type
//            executorService.submit(() -> {
//                task1.task(finalI);
//            });
//            executorService.execute(() -> {
//                task1.task(finalI);
//            });
            executorService.schedule(() -> {
                task1.task(finalI);
            }, 5, TimeUnit.SECONDS);
        }
        executorService.shutdown();
    }
}
