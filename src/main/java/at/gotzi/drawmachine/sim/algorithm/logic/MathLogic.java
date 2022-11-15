package at.gotzi.drawmachine.sim.algorithm.logic;

import at.gotzi.drawmachine.sim.SimInfo;
import at.gotzi.drawmachine.api.sim.SimPoint;

public class MathLogic {

    private final SimLogic simLogic;
    private final SimInfo simInfo;

    public MathLogic(SimLogic simLogic) {
        this.simLogic = simLogic;
        this.simInfo = simLogic.getSimInfo();
    }

    /**
     * Given a current step and a speed, return the degree of rotation.
     *
     * @param currentStep The current step of the animation.
     * @param speed The speed of the rotation.
     * @return The degree of the current step.
     */
    protected double speedToDegree(int currentStep, double speed) {
        return (currentStep * speed) % 360;
    }

    /**
     * The function calculates the position of the pencil point based on the current position of the motors
     *
     * @param step The current step of the simulation
     * @return The point where the pencil is.
     */
    protected SimPoint calculatePencilPoint(int step) {
        double middleDegree = speedToDegree(step, simLogic.getRealSpeedMiddle());
        double m1Degree = speedToDegree(step, simLogic.getRealSpeedM1());
        double m2Degree = speedToDegree(step, simLogic.getRealSpeedM2());

        double m1exOffset = Math.cos(Math.toRadians(m1Degree)) * simInfo.getSimValues().lm1();
        double m1eyOffset = Math.sin(Math.toRadians(m1Degree)) * simInfo.getSimValues().lm1();

        double m2exOffset = Math.cos(Math.toRadians(m2Degree)) * simInfo.getSimValues().lm2();
        double m2eyOffset = Math.sin(Math.toRadians(m2Degree)) * simInfo.getSimValues().lm2();

        HelperPoint m1PositionOut = new HelperPoint(simInfo.getSimValues().m1().x() + m1exOffset, simInfo.getSimValues().m1().y() + m1eyOffset);
        HelperPoint m2PositionOut = new HelperPoint(simInfo.getSimValues().m2().x() + m2exOffset, simInfo.getSimValues().m2().y() + m2eyOffset);

        double c = Math.sqrt(Math.pow(m1PositionOut.x-m2PositionOut.x,2) + Math.pow(m1PositionOut.y-m2PositionOut.y,2));

        double xC = (Math.pow(simInfo.getSimValues().ls2(), 2) - Math.pow(simInfo.getSimValues().s1_2(), 2) + Math.pow(c, 2))/ (2.0 * c);
        double yC = Math.sqrt(Math.pow(simInfo.getSimValues().ls2(), 2) - Math.pow(xC, 2));

        System.out.println(" " + xC + " yC: " + yC + " c: " + c);

        HelperPoint C = new HelperPoint(xC + m1PositionOut.x, yC + m2PositionOut.y);

        double s = 0.5 * (simInfo.getSimValues().s1_2() + simInfo.getSimValues().ls2() + c);
        double hc = (2.0/c) * Math.sqrt(s * (s - simInfo.getSimValues().s1_2()) * (s - simInfo.getSimValues().ls2()) * (s - c));

        double beta = Math.asin(hc/simInfo.getSimValues().s1_2());

        double ls3 = simInfo.getSimValues().ls1()-simInfo.getSimValues().s1_2();

        double pencilXOffset = Math.cos(beta) * ls3;
        double pencilYOffset = Math.sin(beta) * simInfo.getSimValues().ls1();
        HelperPoint pencil = new HelperPoint(C.x + pencilXOffset, C.y + pencilYOffset);

        System.out.println(pencil);

        double absX = pencil.x - simInfo.getSimValues().middlePoint().x();
        double absY = pencil.y - simInfo.getSimValues().middlePoint().y();

        double abs = Math.sqrt(absX * absX + absY * absY);
        double radiantOffset1 = Math.atan(absY/absX);

        double newPencil1X = Math.cos(Math.toRadians(middleDegree) + radiantOffset1) * abs;
        double newPencil1Y = Math.sin(Math.toRadians(middleDegree) + radiantOffset1) * abs;

        pencil = new HelperPoint(newPencil1X + simInfo.getSimValues().middlePoint().x(), newPencil1Y + simInfo.getSimValues().middlePoint().y());

        /*

        double c = Math.sqrt(Math.pow(m1PositionOut.x-m2PositionOut.x,2) + Math.pow(m1PositionOut.y-m2PositionOut.y,2));

        double s = 0.5 * (simInfo.getSimValues().ls1() + simInfo.getSimValues().ls2() + c);
        double hc = (2.0/c) * Math.sqrt(s * (s - simInfo.getSimValues().ls1()) * (s - simInfo.getSimValues().ls2()) * (s - c));

        double alphaRadiant = Math.asin(hc/simInfo.getSimValues().ls1());
        double miniRadiant = Math.atan((simInfo.getSimValues().m1().y() - simInfo.getSimValues().m2().y()) /
                (simInfo.getSimValues().m1().x() - simInfo.getSimValues().m2().x()));

        double newRadiant = alphaRadiant - miniRadiant;

        double cxOffset = Math.cos(newRadiant) * simInfo.getSimValues().ls1();
        double cyOffset = Math.sin(newRadiant) * simInfo.getSimValues().ls1();

        HelperPoint C = new HelperPoint(m1PositionOut.x + cxOffset, m1PositionOut.y + cyOffset);

        double bRadiant = Math.asin(hc/simInfo.getSimValues().ls2());

        double pencilXOffset = Math.cos(bRadiant) * simInfo.getSimValues().lp();
        double pencilYOffset = Math.sin(bRadiant) * simInfo.getSimValues().lp();

        HelperPoint pencil = new HelperPoint(C.x + pencilXOffset, C.y + pencilYOffset);

        double absX = pencil.x - simInfo.getSimValues().middlePoint().x();
        double absY = pencil.y - simInfo.getSimValues().middlePoint().y();

        double hypo = Math.sqrt(absX * absX + absY * absY);
        double radiantOffset1 = Math.atan(absX/absY);

        double newPencil1X = Math.cos(Math.toRadians(middleDegree) + radiantOffset1) * hypo;
        double newPencil1Y = Math.sin(Math.toRadians(middleDegree) + radiantOffset1) * hypo;

         */
        return new SimPoint(pencil.x(), pencil.y());
    }

    record HelperPoint(double x, double y) {

        @Override
        public String toString() {
            return "HelperPoint{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    };



}