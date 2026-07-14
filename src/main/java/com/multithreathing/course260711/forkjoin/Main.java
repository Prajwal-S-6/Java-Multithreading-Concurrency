package com.multithreathing.course260711.forkjoin;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

class ListAdditionWorker extends RecursiveAction {
    int[] arr;

    public ListAdditionWorker(int[] arr) {
        this.arr = arr;
    }

    @Override
    protected void compute() {
        if(arr.length <= 2) {
            for (int i = 0; i < arr.length; i++) {
                System.out.println(arr[i]);
            }
        }
        else {
            int mid = (arr.length) / 2;
            System.out.println("Splitting the array..." + arr.length);
            ListAdditionWorker listAdditionWorker1 = new ListAdditionWorker(splitarray(arr, 0, mid));
            ListAdditionWorker listAdditionWorker2 = new ListAdditionWorker(splitarray(arr, mid, arr.length));

            invokeAll(listAdditionWorker1, listAdditionWorker2);
        }
    }

    private int[] splitarray(int[] a, int low, int high) {
        int[] res = new int[high - low];
        for (int i=0; i < (high - low); i++) {
            res[i] = a[low + i];
        }
        return res;
    }
}
public class Main {

    static void main() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(new ListAdditionWorker(new int[] {1,2,3,4,5,6,7,8,9,10}));
    }
}
