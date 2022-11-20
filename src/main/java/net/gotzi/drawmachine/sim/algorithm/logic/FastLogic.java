package net.gotzi.drawmachine.sim.algorithm.logic;

import net.gotzi.drawmachine.api.Action;
import net.gotzi.drawmachine.api.sim.SimCompletedInfo;
import net.gotzi.drawmachine.api.sim.SimPoint;
import net.gotzi.drawmachine.error.PencilOutOfCanvas;
import net.gotzi.drawmachine.sim.SimInfo;
import net.gotzi.drawmachine.sim.algorithm.Canvas;
import net.gotzi.drawmachine.sim.algorithm.SimRenderer;
import net.gotzi.drawmachine.utils.BenchmarkTimer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastLogic implements Logic {

    private final SimInfo simInfo;
    private final Action<Integer> update;
    private final Canvas paper;
    private final SimRenderer simRenderer;
    private final Action<SimCompletedInfo> finishedAction;
    private final List<SimPoint[]> simPoints;
    private boolean finished = false;

    public FastLogic(SimInfo simInfo, Action<Integer> update, Canvas paper, SimRenderer simRenderer, Action<SimCompletedInfo> finishedAction) {
        this.simInfo = simInfo;
        this.update = update;
        this.paper = paper;
        this.simRenderer = simRenderer;
        this.finishedAction = finishedAction;
        this.simPoints = new ArrayList<>(simInfo.getStepAmount());
    }

    @Override
    public boolean isFinished(int step) {
        return this.finished;
    }

    private void collect(SimPoint[] threadSimPoint) {
        synchronized (simPoints) {
            this.simPoints.add(threadSimPoint);
        }
    }

    private synchronized boolean checkFinished(Thread[] mathThreads) {
        if (Arrays.stream(mathThreads).filter(Thread::isAlive).count() <= 1) {
            return true;
        }

        return false;
    }

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

    @Override
    public synchronized void run() {
        //TODO fastlogic

        System.out.println("test1");

        BenchmarkTimer benchmarkTimer = new BenchmarkTimer();
        benchmarkTimer.start();

        int stepAmount = this.simInfo.getStepAmount();
        MathLogic mathLogic = new MathLogic(this.simInfo);
        Thread[] mathThreads = new Thread[(stepAmount/80000) + 1];

        Action<Long> finished = time -> {
            finished();
            synchronized (simPoints) {
                System.out.println("Time (ms): " + time);

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

                for (int step = start; step < stop && !Thread.currentThread().isInterrupted(); step++) {
                    threadSimPoints[step - start] = mathLogic.calculatePencilPoint(step);
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
