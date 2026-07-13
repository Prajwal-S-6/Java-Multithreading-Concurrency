package com.multithreathing.course260711;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

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
public class RegularCollectionProblem {

    static void main() {
        var workerList = new WorkerList();
        Thread thread = new Thread(workerList);
        Thread thread1 = new Thread(workerList);
        thread.start();
        thread1.start();

        try {
            thread.join();
            thread1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("List size: " + workerList.getListSize());
    }
}
