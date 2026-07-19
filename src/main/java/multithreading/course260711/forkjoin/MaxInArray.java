package multithreading.course260711.forkjoin;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class MaxFindingTask extends RecursiveTask<Integer> {

    int[] arr;
    int low;
    int high;

    public MaxFindingTask(int[] arr, int low, int high) {
        this.arr = arr;
        this.low = low;
        this.high = high;
    }

    @Override
    protected Integer compute() {
        if((high-low) <= 1_000_000) {
            return maxInArray(arr, low, high);
        }
        int mid = (low+high)/2;
        var maxFindingTask1 = new MaxFindingTask(arr, low, mid);
        var maxFindingTask2 = new MaxFindingTask(arr, mid, high);

        invokeAll(maxFindingTask1, maxFindingTask2);

        return Math.max(maxFindingTask1.join(), maxFindingTask2.join());
    }

    public static int maxInArray(int[] arr, int low, int high) {
//        System.out.println("arr: " + (high-low));
        int max = arr[low];
        for (int i = low+1; i < high; i++) {
            if(arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
}
public class MaxInArray {

    public static void main(String[] args) {
        var cores = Runtime.getRuntime().availableProcessors();
        int[] arr = buildArray(800000000);
        MaxFindingTask maxFindingTask =  new MaxFindingTask(arr, 0, arr.length);

        try(ForkJoinPool forkJoinPool = new ForkJoinPool(cores)) {
            var startTime = System.currentTimeMillis();
            var res = forkJoinPool.invoke(maxFindingTask);
            System.out.printf("Max: %d. Time taken in ms: %d \n", res, (System.currentTimeMillis() - startTime));
        }

        var startTime = System.currentTimeMillis();
        var res = MaxFindingTask.maxInArray(arr, 0, arr.length);
        System.out.printf("Max: %d. Time taken in ms: %d \n", res, (System.currentTimeMillis() - startTime));

    }

    private static int[] buildArray(int size) {
        int[] arr = new int[size];
        var random = new Random();
        for(int i=0; i<arr.length; i++) {
            arr[i] = random.nextInt(size);
        }
        return arr;
    }


}
