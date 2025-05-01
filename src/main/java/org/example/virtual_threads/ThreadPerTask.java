package org.example.virtual_threads;


import java.util.ArrayList;
import java.util.List;

public class ThreadPerTask {
    private static final int NUMBER_OF_TASKS = 100000;

    public static void main(String[] args) throws InterruptedException {
        System.out.printf("Running %d tasks\n", NUMBER_OF_TASKS);

        long start = System.currentTimeMillis();
        performTasks();
        System.out.printf("Tasks took %dms to complete\n", System.currentTimeMillis() - start);
    }

    private static void performTasks() throws InterruptedException {
        List<Thread> virtualThreads = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_TASKS; i++) {
            // Thread thread = Thread.ofPlatform().unstarted(() -> blockingIoOperation());    // Regular java/platform thread each of which is OS thread, resulting in longer context switch when waiting on IO calls(Thrashing)
            Thread thread = Thread.ofVirtual().unstarted(() -> blockingIoOperation());     // Virtual thread where jvm creates fixed platform thread bts
            virtualThreads.add(thread);
        }

        for(Thread thread: virtualThreads) {
            thread.start();
        }

        for(Thread thread: virtualThreads) {
            thread.join();
        }
    }

    // Simulates a long blocking IO
    private static void blockingIoOperation() {
        System.out.println("Inside thread: " + Thread.currentThread() + " before blocking call");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Inside thread: " + Thread.currentThread() + " after blocking call");
    }
}