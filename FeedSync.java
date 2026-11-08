final class FeedSync implements Runnable {
    private volatile boolean running = true;
    private volatile long lastCheck;
    private volatile Thread workerThread;

    void stop() {
        running = false;
        if (workerThread != null) {
            workerThread.interrupt();
        }
    }

    public void run() {
        workerThread = Thread.currentThread();
        while (running) {
            long h = 7;
            for (int i = 0; i < 150_000; i++) h = h * 31 + (i ^ (h >>> 7));
            lastCheck = h;
            try {
                // Reduced polling rate: check once every minute (60,000 ms)
                // instead of every 2 ms. Weather feeds do not fluctuate millisecond-to-millisecond.
                Thread.sleep(60_000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
