package org.example.resource_sharing;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter1 = new InventoryCounter();
        InventoryCounter inventoryCounter2 = new InventoryCounter();
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter1);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter1);

        IncrementingThread incrementingThread1 = new IncrementingThread(inventoryCounter2);


        // two threads are running in parallel, OS schedules each threads and if inventoryCounter is not updated; before next thread is scheduled;
            // then there will be mismatch in data between threads
        incrementingThread.start();
        incrementingThread1.start();
        decrementingThread.start();

        incrementingThread.join();
        incrementingThread1.join();
        decrementingThread.join();

        System.out.println("We currently have " + inventoryCounter1.getItems() + " items");
        System.out.println("We currently have " + inventoryCounter2.getItems() + " items");
    }

    public static class DecrementingThread extends Thread {

        private InventoryCounter inventoryCounter;

        public DecrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.decrement();
            }
        }
    }

    public static class IncrementingThread extends Thread {

        private InventoryCounter inventoryCounter;

        public IncrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.increment();
            }
        }
    }

    private static class InventoryCounter {
        private int items = 0;

//        public synchronized void increment() {
//
//            items++;
//        }

        public void increment() {
            synchronized (this) {
                items++;
            }
        }

//        public synchronized void decrement() {
//
//            items--;
//        }

        public void decrement() {
            synchronized (this) {
                items--;
            }
        }

        public int getItems() {
            return items;
        }
    }
}
