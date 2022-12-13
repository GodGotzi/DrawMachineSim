package net.gotzi.drawmachine.sim.algorithm.logic;

import net.gotzi.drawmachine.api.Action;
import net.gotzi.drawmachine.api.sim.SimCompletedInfo;
import net.gotzi.drawmachine.api.sim.SimPoint;
import net.gotzi.drawmachine.error.PencilOutOfCanvas;
import net.gotzi.drawmachine.sim.SimInfo;
import net.gotzi.drawmachine.sim.Canvas;
import net.gotzi.drawmachine.sim.algorithm.SimGCodeLoader;
import net.gotzi.drawmachine.utils.BenchmarkTimer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastLogic implements Logic {

    private final SimInfo simInfo;
    private final Action<Integer> update;
    private final Canvas paper;
    private final Action<SimCompletedInfo> finishedAction;
    private final List<SimPoint[]> simPoints;
    private final SimGCodeLoader simGCodeLoader;
    private boolean finished = false;

    public FastLogic(SimInfo simInfo, Action<Integer> update, Canvas paper, Action<SimCompletedInfo> finishedAction) {
        this.simInfo = simInfo;
        this.update = update;
        this.paper = paper;
        this.finishedAction = finishedAction;
        this.simPoints = new ArrayList<>();

        this.simGCodeLoader = new SimGCodeLoader(simInfo.getSimValues().gCode());
    }

    @Override
    public boolean isFinished(int step) {
        return this.finished;
    }

    /**
     * > This function is called by each thread to add its results to the global array of results
     *
     * @param threadSimPoint The SimPoint array that the thread is trying to add to the simPoints array.
     */
    private void collect(SimPoint[] threadSimPoint) {
        synchronized (simPoints) {
            this.simPoints.add(threadSimPoint);
        }
    }

    /**
     * "If there is only one thread left alive, then return true, otherwise return false."
     *
     * The function is synchronized because it is called from multiple threads
     *
     * @param mathThreads An array of threads that are running the math operations.
     * @return The number of threads that are still alive.
     */
    private synchronized boolean checkFinished(Thread[] mathThreads) {
        return Arrays.stream(mathThreads).filter(Thread::isAlive).count() <= 1;
    }

    /**
     * It draws all the points in the simulation
     */
    private synchronized void drawPoints() {
        simPoints.forEach(simPoints -> {
            for (SimPoint point : simPoints) {
                try {
                    if (point != null)
                        this.paper.setPoint((int) point.x(), (int) point.y());
                } catch (PencilOutOfCanvas ignored) { }
            }
        });

        //System.out.println("juhu");
    }

    /**
     * It creates a bunch of threads, each of which calculates a bunch of points, and then when all the threads are done,
     * it draws the points and calls the finishedAction
     */
    @Override
    public synchronized void run() {
        SimGCodeLoader simGCodeLoader = new SimGCodeLoader(
                this.simInfo.getSimValues().gCode()
        );

        BenchmarkTimer benchmarkTimer = new BenchmarkTimer();
        benchmarkTimer.start();

        double stepFactor = this.simInfo.getStepFactor();
        int stepAmount = (int) (simGCodeLoader.calculateTime()/stepFactor);

        MathLogic mathLogic = new MathLogic(this.simInfo);
        Thread[] mathThreads = new Thread[(stepAmount/80000) + 1];

        Action<Long> finished = time -> {
            finished();
            synchronized (simPoints) {
                System.out.println("Calculation Time (ms): " + time);

                drawPoints();
                SimCompletedInfo completedInfo = new SimCompletedInfo();
                finishedAction.run(completedInfo);
                this.update.run(stepAmount);
            }
        };

        for (int i = 0; i < mathThreads.length; i++) {
            int start = i*80000;
            int stop = Math.min(start + 80000, stepAmount);

            mathThreads[i] = new Thread(() -> {
                SimPoint[] threadSimPoints = new SimPoint[80000];

                for (int timestamp = start; timestamp < stop && !Thread.currentThread().isInterrupted(); timestamp++) {
                    threadSimPoints[timestamp - start] = mathLogic.calculatePencilPoint(timestamp, simGCodeLoader);
                }

                collect(threadSimPoints);
                if (checkFinished(mathThreads))
                    finished.run(benchmarkTimer.stop());
            });
        }

        Arrays.stream(mathThreads).forEach(Thread::start);
    }

    public synchronized void finished() {
        this.finished = true;
    }

    @Override
    public double getTravelDistance() {
        return 0;
    }
}
