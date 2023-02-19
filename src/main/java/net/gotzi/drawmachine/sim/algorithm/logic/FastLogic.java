package net.gotzi.drawmachine.sim.algorithm.logic;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.api.Action;
import net.gotzi.drawmachine.api.sim.SimCompletedInfo;
import net.gotzi.drawmachine.api.sim.SimPoint;
import net.gotzi.drawmachine.api.sim.SimRenderState;
import net.gotzi.drawmachine.error.PencilOutOfCanvas;
import net.gotzi.drawmachine.sim.SimInfo;
import net.gotzi.drawmachine.sim.Canvas;
import net.gotzi.drawmachine.sim.SimRenderer;
import net.gotzi.drawmachine.sim.algorithm.SimGCodeLoader;
import net.gotzi.drawmachine.utils.BenchmarkTimer;
import net.gotzi.drawmachine.utils.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastLogic extends Logic {

    private final SimInfo simInfo;
    private final Action<SimRenderState> update;
    private final Canvas paper;
    private final Action<SimCompletedInfo> finishedAction;
    private final List<SimPoint[]> simPoints;
    private final SimGCodeLoader simGCodeLoader;
    private boolean finished = false;

    public FastLogic(SimInfo simInfo, Action<SimRenderState> update, Canvas paper, Action<SimCompletedInfo> finishedAction) {
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
                } catch (PencilOutOfCanvas ex) {
                    ex.showErrorInfo(DrawMachineSim.getInstance().getWindow(), "Pencil out of Canvas");
                }
            }
        });
    }

    protected double sumUpTravelDistance(List<SimPoint[]> simPointsArrayCollection) {
        SimPoint last = null;
        double travelDistance = 0;

        for (SimPoint[] simPointArray : simPointsArrayCollection) {
            for (SimPoint point : simPointArray) {
                if (last == null) {
                    last = point;
                    continue;
                }

                travelDistance += distance(last, point);
            }
        }

        return travelDistance;
    }

    /**
     * It creates a bunch of threads, each of which calculates a bunch of points, and then when all the threads are done,
     * it draws the points and calls the finishedAction
     */
    @Override
    public synchronized void run() {
        double stepFactor = this.simInfo.getStepFactor();
        long nativeTime = simGCodeLoader.getFullTime();
        double time = (double)nativeTime * stepFactor;

        MathLogic mathLogic = new MathLogic(this.simInfo);
        Thread[] mathThreads = new Thread[Helper.processorAmount() + 1];

        Action<Long> finished = renderTime -> {
            finished();
            synchronized (simPoints) {
                drawPoints();

                SimCompletedInfo completedInfo = new SimCompletedInfo(renderTime, sumUpTravelDistance(simPoints));
                finishedAction.run(completedInfo);
                this.update.run(new SimRenderState((int) nativeTime, (int) nativeTime));
            }
        };

        BenchmarkTimer benchmarkTimer = new BenchmarkTimer();
        benchmarkTimer.start();

        int timestampsPerThread = (int) (time/mathThreads.length);
        for (int i = 0; i < mathThreads.length; i++) {
            int start = i*timestampsPerThread;
            int stop = (int) Math.min(start + timestampsPerThread, time);

            mathThreads[i] = new Thread(() -> {
                SimPoint[] threadSimPoints = new SimPoint[timestampsPerThread];

                for (int timestamp = start; timestamp < stop && !Thread.currentThread().isInterrupted(); timestamp++) {
                    threadSimPoints[timestamp - start] = mathLogic.calculatePencilPoint((double)timestamp/stepFactor, simGCodeLoader);
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
}
