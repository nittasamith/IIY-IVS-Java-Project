public class TimeTracker implements Runnable {

    private boolean running = false;
    private long startTime;
    private long elapsedTime;

    public void startTracking() {
        if (!running) {
            running = true;
            startTime = System.currentTimeMillis();
            Thread thread = new Thread(this);
            thread.start();
        }
    }

    public long stopTracking() {
        if (running) {
            running = false;
            elapsedTime = System.currentTimeMillis() - startTime;
        }
        return elapsedTime;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);  // Background thread sleep
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}