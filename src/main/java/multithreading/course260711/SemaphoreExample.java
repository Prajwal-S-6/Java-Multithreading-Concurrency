package multithreading.course260711;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

class Example3 {
    Semaphore semaphore = new Semaphore(3);

    public void task() {
        try {
            semaphore.acquire();
            System.out.println("Doing work... " + Thread.currentThread().getName());
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Releasing... " + Thread.currentThread().getName());
            semaphore.release();
        }
    }

}
public class SemaphoreExample {

    static void main() {
        Example3 example3 = new Example3();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 20; i++) {
            executorService.execute(() -> {
                example3.task();
            });

        }
            executorService.shutdown();
    }
}
