package com.multithreathing.course260711;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class SharedBuffer {
    List<Integer> buffer = new LinkedList<>();
    int capacity = 5;

    public synchronized void add(Integer integer) {
        while(buffer.size() == capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        buffer.add(integer);
        System.out.println("Adding item... " + buffer);
        notifyAll();
    }

    public synchronized void remove() {
        while (buffer.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        buffer.removeFirst();
        System.out.println("Removing item... " + buffer);
        notifyAll();
    }
}

class Producer1  {
    private final SharedBuffer sharedBuffer;

    public Producer1(SharedBuffer sharedBuffer) {
        this.sharedBuffer = sharedBuffer;
    }

    public void add(Integer i) {
        sharedBuffer.add(i);
    }
}

class Consumer1 {
    private final SharedBuffer sharedBuffer;

    public Consumer1(SharedBuffer sharedBuffer) {
        this.sharedBuffer = sharedBuffer;
    }

    public void removeItem() {
        sharedBuffer.remove();
    }
}

public class ProducerConsumerExample {

    static void main() {
        SharedBuffer sharedBuffer = new SharedBuffer();
        Producer1 producer1 = new Producer1(sharedBuffer);
        Consumer1 consumer1 = new Consumer1(sharedBuffer);

        ExecutorService executorService = Executors.newFixedThreadPool(8);
        for (int j = 0; j < 4; j++) {
            executorService.execute(() -> {
                for (int i = 0; i < 10; i++) {
                    try {
                        producer1.add(i);
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        for (int j =0 ; j < 4; j++) {
            executorService.execute(() -> {
                for (int i = 0; i < 10; i++) {
                    consumer1.removeItem();
                }
            });
        }

        //shutdown
        executorService.shutdown();
    }
}
