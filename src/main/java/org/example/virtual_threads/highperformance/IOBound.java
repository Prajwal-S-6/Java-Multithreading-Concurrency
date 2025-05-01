package org.example.virtual_threads.highperformance;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IOBound {
    private static final int NUMBER_OF_TASKS = 10_0000;

    public static void main(String[] args) {
        System.out.printf("Running %d tasks\n", NUMBER_OF_TASKS);

        long start = System.currentTimeMillis();
        performTasks();
        System.out.printf("Tasks took %dms to complete\n", System.currentTimeMillis() - start);
    }

    private static void performTasks() {

        // platform thread with dynamic thread pool // 11.5s
//        try(ExecutorService executorService = Executors.newCachedThreadPool()) {
//            for (int i = 0; i < NUMBER_OF_TASKS; i++) {
//                executorService.submit(() ->  blockingIoOperation());
//            }
//        }

        // virtual thread  // 3.5s
        try(ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < NUMBER_OF_TASKS; i++) {
                executorService.submit(() ->  blockingIoOperation());
            }
        }
    }

    // Simulates a long blocking IO
    private static void blockingIoOperation() {
        System.out.println("Executing a blocking task from thread: " + Thread.currentThread());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}