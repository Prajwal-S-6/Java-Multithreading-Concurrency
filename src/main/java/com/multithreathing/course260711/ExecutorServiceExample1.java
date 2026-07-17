package com.multithreathing.course260711;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

class Task1 {

    public void task(int i) {
        try {
            Thread.sleep(1000);
            System.out.println("Doing task " + i + " " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            System.out.println("Tasks interrupted "+ i);
            Thread.currentThread().interrupt();
            throw new RuntimeException("test");
        }
    }
}


public class ExecutorServiceExample1 {
    static void main() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(6);
//        Task1 task1 = new Task1();
//        for (int i = 0; i < 100; i++) {
//            int finalI = i;

//            executorService.submit(() -> {
//                task1.task(finalI);
//            });
////            executorService.execute(() -> {
////                task1.task(finalI);
////            });
////            executorService.schedule(() -> {
////                task1.task(finalI);
////            }, 6, TimeUnit.SECONDS);
//        }


           /// execute() - Runnable type; doesnt throw checked exception exception travels to worker thread
           /// submit() - Runnable, Callable type, throws checked exception and is stored in Future object
        Runnable runnable = () -> {
            System.out.println("Runnable...");
        };

        Callable callable = () -> {
            System.out.println("Callable...");
            Thread.sleep(10000);
            return 6;
        };

        executorService.submit(runnable);
        executorService.execute(runnable);
        Future<Integer> futureObj = executorService.submit(callable);

        executorService.shutdown();
        try {
            if(!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
               executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.out.println("Exception was thrown " + e.getMessage());
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        try {
            System.out.println("Value: " + futureObj.get());
        } catch (Exception  ee) {
            System.out.println("Couldnt get the value within time" + ee);
        }

        List<Integer> list1 = Arrays.asList(1,2,3);
        List<Integer> list2 = Arrays.asList(4,5);

        List<List<Integer>> res = list1.stream()
                .map(n1 -> list2.stream().map(n2 -> Arrays.asList(n1, n2)))
                .flatMap(x -> x)
                .collect(Collectors.toList());
        System.out.println(res);
    }
}
