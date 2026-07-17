package multithreading.course260711;

import java.util.concurrent.atomic.AtomicInteger;

class Example2 implements Runnable {
    private int counter;
    AtomicInteger atomicCounter =  new AtomicInteger(0);

    private synchronized void increment() {
        counter++;
    }

    // instead of synchronization use Atomic classes ensures atomicity
    private void atomicIncrement() {
        atomicCounter.incrementAndGet();
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            increment();
            atomicIncrement();
        }
    }

    public int getCounter() {
        return counter;
    }
}
public class AtomicExample {

    static void main() throws InterruptedException {
        Example2 example2 = new Example2();
        Thread t1 = new Thread(example2);
        Thread t2 = new Thread(example2);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Count: "+example2.getCounter());
        System.out.println("Count: "+example2.atomicCounter.get());
    }
}
