package multithreading.course260711;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class SharedBuffer1 {
    List<Integer> buffer = new LinkedList<>();
    int capacity = 5;
    Lock lock = new ReentrantLock();
    Condition producerCondition = lock.newCondition();
    Condition consumerCondition = lock.newCondition();

    public void add(Integer integer) {
        lock.lock();
        try {
            while(buffer.size() == capacity) {
                try {
                    producerCondition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            buffer.add(integer);
            System.out.println("Adding item... " + buffer);
            consumerCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void remove() {
        lock.lock();
        try {
            while (buffer.isEmpty()) {
                try {
                    consumerCondition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            buffer.removeFirst();
            System.out.println("Removing item... " + buffer);
            producerCondition.signal();
        } finally {
            lock.unlock();
        }
    }
}

class Producer2  {
    private final SharedBuffer1 sharedBuffer;

    public Producer2(SharedBuffer1 sharedBuffer) {
        this.sharedBuffer = sharedBuffer;
    }

    public void add(Integer i) {
        sharedBuffer.add(i);
    }
}

class Consumer2 {
    private final SharedBuffer1 sharedBuffer;

    public Consumer2(SharedBuffer1 sharedBuffer) {
        this.sharedBuffer = sharedBuffer;
    }

    public void removeItem() {
        sharedBuffer.remove();
    }
}
public class ConditionExample {
    static void main() {
        SharedBuffer1 sharedBuffer = new SharedBuffer1();
        Producer2 producer2 = new Producer2(sharedBuffer);
        Consumer2 consumer2 = new Consumer2(sharedBuffer);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int j = 0; j < 5; j++) {
            executorService.execute(() -> {
                for (int i = 0; i < 10; i++) {
                    try {
                        producer2.add(i);
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        for (int j =0 ; j < 5; j++) {
            executorService.execute(() -> {
                for (int i = 0; i < 10; i++) {
                    consumer2.removeItem();
                }
            });
        }

        //shutdown
        executorService.shutdown();
    }

}
