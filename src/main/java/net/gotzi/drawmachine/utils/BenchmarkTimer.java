package net.gotzi.drawmachine.utils;

import net.gotzi.drawmachine.api.Action;

public class BenchmarkTimer {

    private long saveTime;

    public synchronized void start() {
        saveTime = System.currentTimeMillis();
    }

    public synchronized long stop() {
        return System.currentTimeMillis()-saveTime;
    }
}
