package org.example.threadtermination;

public class Main1 {
    public static void main(String [] args) {
        Thread thread = new Thread(new BlockingTask());

        thread.start();
        thread.interrupt();
    }

    private static class BlockingTask implements Runnable {

        @Override
        public void run() {
            //do things
            while(true) {
                try {
                    Thread.sleep(500000);
                } catch (InterruptedException e) {
                    System.out.println("Existing blocking thread");
                    break;   // have to return/or break from this loop even if this thread is interrupted
                }
            }

        }
    }
}
