package multithreading.course260711;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

class WorkerList implements Runnable {

    List<Integer> list = new ArrayList<>();
    List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
    //creates copy on every write; not good for write heavy workload
    List<Integer> synchronizedCopyOnArrList = Collections.synchronizedList(new ArrayList<>());
    BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(200);

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
//            list.add(1);
//            synchronizedList.add(1);
//            synchronizedCopyOnArrList.add(1);
            blockingQueue.add(1);
        }
    }

    public int getListSize() {
//        return list.size();
//        return synchronizedList.size();
//        return synchronizedCopyOnArrList.size();
        return blockingQueue.size();
    }
}

class WorkerList2 implements Runnable {

    PriorityBlockingQueue<Integer> priorityBlockingQueue;

    public WorkerList2(PriorityBlockingQueue<Integer> priorityBlockingQueue) {
        this.priorityBlockingQueue = priorityBlockingQueue;
    }

    @Override
    public void run() {
        try {

            System.out.println("added 10" + priorityBlockingQueue.add(10));
            System.out.println("added 20" + priorityBlockingQueue.add(20));

            Thread.sleep(100);
            System.out.println("added 30" + priorityBlockingQueue.add(30));


            System.out.println("added 40" + priorityBlockingQueue.add(40));

            Thread.sleep(100);
            System.out.println("added 50" + priorityBlockingQueue.add(50));

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public PriorityBlockingQueue<Integer> getPriorityBlockingQueue() {
        return priorityBlockingQueue;
    }

    public int getPriorityBlockingQueueElement() {
        return priorityBlockingQueue.poll();
    }
}
public class RegularCollectionProblem {

    static void main() throws InterruptedException {
        var workerList = new WorkerList();
        var workerList2 = new WorkerList2(new PriorityBlockingQueue<>(5, Comparator.reverseOrder()));
        Thread thread = new Thread(workerList2);
        Thread thread1 = new Thread(workerList2);
        Thread thread3 = new Thread(() -> {
            try {
                System.out.println("Waiting for elements to take");
                while(true) {
                    System.out.println("Taking elements" + workerList2.getPriorityBlockingQueue().take());
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread3.start();
        Thread.sleep(1000);
        thread.start();
        thread1.start();
        try {
            thread.join();
            thread1.join();
            thread3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        while(!workerList2.priorityBlockingQueue.isEmpty()){
//            System.out.println(workerList2.getPriorityBlockingQueue());
//        }
    }
}
