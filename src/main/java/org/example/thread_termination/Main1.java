package org.example.thread_termination;

public class Main1 {
    public static void main(String [] args) throws InterruptedException {
        Thread thread = new Thread(new BlockingTask());
        thread.start();
        System.out.println(Thread.currentThread().getName());
//        Thread.sleep(5000);
        System.out.println(Thread.currentThread().getName()+ "---------");
        thread.interrupt();
        System.out.println(Thread.currentThread().getName()+ "**********");
    }

    private static class BlockingTask implements Runnable {

        @Override
        public void run() {
            //do things
            while(true) {
                if(Thread.currentThread().isInterrupted()) {
                    System.out.println("Thread got interrupted from external source");
                    break;
                }
                System.out.println("Running on worker thread" + Thread.currentThread().getName());
            }

        }
    }
}
