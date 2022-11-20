package net.gotzi.drawmachine.sim.algorithm.logic;

import net.gotzi.drawmachine.sim.SimInfo;
import net.gotzi.drawmachine.api.sim.SimPoint;

public class MathLogic {
    private final SimInfo simInfo;

    public MathLogic(SimInfo simInfo) {
        this.simInfo = simInfo;
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
        double middleDegree = speedToDegree(step, simInfo.getRealSpeedMiddle());
        double m1Degree = speedToDegree(step, simInfo.getRealSpeedM1());
        double m2Degree = speedToDegree(step, simInfo.getRealSpeedM2());

        double m1exOffset = Math.cos(Math.toRadians(m1Degree)) * simInfo.getSimValues().m1Horn();
        double m1eyOffset = Math.sin(Math.toRadians(m1Degree)) * simInfo.getSimValues().m1Horn();

        double m2exOffset = Math.cos(Math.toRadians(m2Degree)) * simInfo.getSimValues().m2Horn();
        double m2eyOffset = Math.sin(Math.toRadians(m2Degree)) * simInfo.getSimValues().m2Horn();

        HelperPoint m1PositionOut = new HelperPoint(simInfo.getSimValues().m1Point().x() + m1exOffset, simInfo.getSimValues().m1Point().y() + m1eyOffset);
        HelperPoint m2PositionOut = new HelperPoint(simInfo.getSimValues().m2Point().x() + m2exOffset, simInfo.getSimValues().m2Point().y() + m2eyOffset);

        //System.out.println("m1exOffset: " + m1exOffset + "m1eyOffset: " + m1eyOffset)

        double c = Math.sqrt(Math.pow(m1PositionOut.x-m2PositionOut.x,2) + Math.pow(m1PositionOut.y-m2PositionOut.y,2));

        double s = 0.5 * (simInfo.getSimValues().intersection() + simInfo.getSimValues().supportPole() + c);
        double hc = (2.0/c) * Math.sqrt(s * (s - simInfo.getSimValues().intersection()) * (s - simInfo.getSimValues().supportPole()) * (s - c));

        double alphaRadiant = Math.asin(hc/simInfo.getSimValues().supportPole());
        double miniRadiant = Math.atan((m1PositionOut.y-m2PositionOut.y)/(m1PositionOut.x-m2PositionOut.x));

        double newRadiant = alphaRadiant + miniRadiant;

        //System.out.println("hc: " + hc);
        //System.out.println("Alpha: " + Math.toDegrees(alphaRadiant));
        //System.out.println("Mini: " + Math.toDegrees(miniRadiant));
        //System.out.println("New: " + Math.toDegrees(newRadiant));

        double cxOffset = Math.cos(newRadiant) * simInfo.getSimValues().supportPole();
        double cyOffset = Math.sin(newRadiant) * simInfo.getSimValues().supportPole();

        HelperPoint C = new HelperPoint(m1PositionOut.x + cxOffset, m1PositionOut.y + cyOffset);

        //System.out.println("ABS C: " + Math.sqrt(Math.pow(C.x - simInfo.getSimValues().middlePoint().x(), 2) + Math.pow(C.y - simInfo.getSimValues().middlePoint().y(), 2)));

        double beta = Math.asin(hc/simInfo.getSimValues().intersection());
        double ls3 = simInfo.getSimValues().mainPole()-simInfo.getSimValues().intersection();
        //System.out.println("Beta: " + Math.toDegrees(beta));
        //System.out.println("Miniradiant: " + Math.toDegrees(miniRadiant));
        //System.out.println("Dif: " + Math.toDegrees(beta-miniRadiant));

        double pencilXOffset = Math.cos( Math.abs((beta - miniRadiant - Math.PI)) ) * ls3;
        double pencilYOffset = Math.sin(Math.abs((beta - miniRadiant - Math.PI))) * ls3;

        HelperPoint pencil = new HelperPoint(C.x + pencilXOffset, C.y + pencilYOffset);

        //System.out.println(pencil);

        double absX = pencil.x - simInfo.getSimValues().middlePoint().x();
        double absY = pencil.y - simInfo.getSimValues().middlePoint().y();

        double abs = Math.sqrt(absX * absX + absY * absY);
        double radiantOffset1 = Math.atan(absY/absX);

        //System.out.println("ABS: " + abs);

        double newPencil1X = Math.cos(Math.toRadians(middleDegree) + radiantOffset1) * abs;
        double newPencil1Y = Math.sin(Math.toRadians(middleDegree) + radiantOffset1) * abs;

        pencil = new HelperPoint(newPencil1X + simInfo.getSimValues().middlePoint().x(), newPencil1Y + simInfo.getSimValues().middlePoint().y());

        /*

        double c = Math.sqrt(Math.pow(m1PositionOut.x-m2PositionOut.x,2) + Math.pow(m1PositionOut.y-m2PositionOut.y,2));

        double s = 0.5 * (simInfo.getSimValues().supportPole() + simInfo.getSimValues().mainPole() + c);
        double hc = (2.0/c) * Math.sqrt(s * (s - simInfo.getSimValues().supportPole()) * (s - simInfo.getSimValues().mainPole()) * (s - c));

        double alphaRadiant = Math.asin(hc/simInfo.getSimValues().supportPole());
        double miniRadiant = Math.atan((simInfo.getSimValues().m1().y() - simInfo.getSimValues().m2().y()) /
                (simInfo.getSimValues().m1().x() - simInfo.getSimValues().m2().x()));

        double newRadiant = alphaRadiant - miniRadiant;

        double cxOffset = Math.cos(newRadiant) * simInfo.getSimValues().supportPole();
        double cyOffset = Math.sin(newRadiant) * simInfo.getSimValues().supportPole();

        HelperPoint C = new HelperPoint(m1PositionOut.x + cxOffset, m1PositionOut.y + cyOffset);

        double bRadiant = Math.asin(hc/simInfo.getSimValues().mainPole());

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