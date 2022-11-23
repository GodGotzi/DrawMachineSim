package net.gotzi.drawmachine.utils;

public class BenchmarkTimer {

    private long saveTime;

    public synchronized void start() {
        saveTime = System.currentTimeMillis();
    }

    public synchronized long stop() {
        return System.currentTimeMillis()-saveTime;
    }
}
