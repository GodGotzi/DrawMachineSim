package at.gotzi.drawmachine.sim.alogrithm;

import at.gotzi.drawmachine.api.Action;
import at.gotzi.drawmachine.error.PencilOutOfCanvas;
import at.gotzi.drawmachine.sim.editor.SimEditor;
import at.gotzi.drawmachine.sim.view.SimMonitor;

public class SimRenderer implements Renderer {

    private final Canvas paper;
    private final Action<Integer> update;
    private int currentSteps = 0;

    private boolean running = false;

    public SimRenderer(Canvas canvas, Action<Integer> update) {
        this.paper = canvas;
        this.update = update;
    }

    @Override
    public void render(SimMonitor simMonitor, SimEditor simEditor) {
        if (!isRunning()) {
            running = true;

            int stepAmount = simMonitor.getSimulationSteps().get();

            speedMiddle = (10000.0 / stepAmount) * simEditor.getSpeedMiddle();
            speedM1 = (10000.0 / stepAmount) * simEditor.getSpeedM1();
            speedM2 = (10000.0 / stepAmount) * simEditor.getSpeedM2();

            System.out.println("SpeedMiddle: " + speedMiddle + " SpeedM1: " + speedM1 + " SpeedM2: " + speedM2);

            Thread thread = new Thread(() -> {
                for (int i = 1; i <= simMonitor.getSimulationSteps().get() && isRunning(); i++) {
                    runStep(i, simMonitor, simEditor);

                    try {
                        Thread.sleep(999/simMonitor.getSimulationSpeed().get());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    if (isRunning())
                        update(i);
                }
            });

            thread.start();
        }
    }

    static Point paperMiddle = new Point(1050, 1050);
    static Point m1 = new Point(200, 1050 - 1500 - 80);
    static Point m2 = new Point( 1900, 1050 - 1500 - 80);
    static double lm1 = 350.0;
    static double lm2 = 400.0;
    static double ls1 = 1250.0;
    static double ls2 = 1600.0;
    static double lp = 200.0;

    static double speedMiddle;
    static double speedM1;
    static double speedM2;

    private void runStep(int step, SimMonitor simMonitor, SimEditor simEditor) {
        double middleRadiant = Math.toRadians((step * speedMiddle) % 360);

        double m1Degree = (step * speedM2) % 360;
        double m2Degree = (step * speedM1) % 360;

        double m1exOffset = Math.cos(Math.toRadians(m1Degree)) * lm1;
        double m1eyOffset = Math.sin(Math.toRadians(m1Degree)) * lm1;

        double m2exOffset = Math.cos(Math.toRadians(m2Degree)) * lm2;
        double m2eyOffset = Math.sin(Math.toRadians(m2Degree)) * lm2;

        Point e = new Point(m1.x + m1exOffset, m1.y + m1eyOffset);
        Point f = new Point(m2.x + m2exOffset, m2.y + m2eyOffset);

        System.out.println("E: " + e + " F: " + f);

        double c = Math.sqrt(Math.pow(e.x-f.x,2) + Math.pow(e.y-f.y,2));

        System.out.println("C: " + c);

        double s = 0.5 * (ls1 + ls2 + c);
        double hc = (2/c) * Math.sqrt(s * (s - ls1) * (s - ls2) * (s - c));

        System.out.println(hc);

        double alphaRadiant = Math.asin(hc/ls1);
        double miniRadiant = Math.atan((m1.y - m2.y) / (m1.x - m2.x));

        double newRadiant = alphaRadiant - miniRadiant;
        System.out.println(Math.toDegrees(newRadiant));

        double cxOffset = Math.cos(newRadiant) * ls1;
        double cyOffset = Math.sin(newRadiant) * ls1;

        Point C = new Point(e.x + cxOffset, e.y + cyOffset);

        double bRadiant = Math.asin(hc/ls2);

        System.out.println(Math.toDegrees(bRadiant));

        double pencilXOffset = Math.cos(bRadiant) * lp;
        double pencilYOffset = Math.sin(bRadiant) * lp;

        System.out.println("Pencil X: " + pencilXOffset + " Pencil Y: " + pencilYOffset);

        Point pencil = new Point(C.x + pencilXOffset, C.y + pencilYOffset);
        System.out.println(pencil);

        try {
            this.paper.setPixel((int) pencil.x, (int) pencil.y);
        } catch (PencilOutOfCanvas ex) {
            System.out.println("pencil out of canvas");
            throw new RuntimeException(ex);
        }
    }

    record Point(double x, double y) {}

    public void stop() {
        running = false;
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void update(int step) {
        this.update.run(step);
    }

    protected void setCurrentSteps(int currentSteps) {
        this.currentSteps = currentSteps;
    }

    public int getCurrentSteps() {
        return this.currentSteps;
    }

    public void resetCanvas() {
        this.paper.reset();
        update.run(0);
    }
}
