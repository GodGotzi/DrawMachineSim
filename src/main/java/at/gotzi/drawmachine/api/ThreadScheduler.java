package at.gotzi.drawmachine.api;

public abstract class ThreadScheduler implements Runnable {

    private boolean stop = false;

    public void start() {
        Thread thread = new Thread(() -> {
            while (!isStopped()) {
                run();
            }
        });

        thread.start();
    }

    public void sleep(long milli) {
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized boolean isStopped() {
        return stop;
    }

    public synchronized void stop() {
        stop = true;
    }

}
