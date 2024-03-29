/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.sim.algorithm.logic;

import net.gotzi.drawmachine.sim.SimInfo;
import net.gotzi.drawmachine.api.sim.SimPoint;
import net.gotzi.drawmachine.sim.algorithm.SimGCodeLoader;

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
     * @param timestamp The current step of the simulation
     * @return The point where the pencil is.
     */
    protected SimPoint calculatePencilPoint(double timestamp, SimGCodeLoader simGCodeLoader) {
        double middleDegree = simGCodeLoader.getMiddleDegree(timestamp);
        double m1Degree = simGCodeLoader.getStepperADegree(timestamp);
        double m2Degree = simGCodeLoader.getStepperBDegree(timestamp);

        double m1exOffset = Math.cos(Math.toRadians(m1Degree)) * simInfo.getSimValues().m1Horn();
        double m1eyOffset = Math.sin(Math.toRadians(m1Degree)) * simInfo.getSimValues().m1Horn();

        double m2exOffset = Math.cos(Math.toRadians(m2Degree)) * simInfo.getSimValues().m2Horn();
        double m2eyOffset = Math.sin(Math.toRadians(m2Degree)) * simInfo.getSimValues().m2Horn();

        HelperPoint m1PositionOut = new HelperPoint(simInfo.getSimValues().m1Point().x() + m1exOffset, simInfo.getSimValues().m1Point().y() + m1eyOffset);
        HelperPoint m2PositionOut = new HelperPoint(simInfo.getSimValues().m2Point().x() + m2exOffset, simInfo.getSimValues().m2Point().y() + m2eyOffset);

        double c = Math.sqrt(Math.pow(m1PositionOut.x-m2PositionOut.x,2) + Math.pow(m1PositionOut.y-m2PositionOut.y,2));

        double s = 0.5 * (simInfo.getSimValues().intersection() + simInfo.getSimValues().supportPole() + c);
        double hc = (2.0/c) * Math.sqrt(s * (s - simInfo.getSimValues().intersection()) * (s - simInfo.getSimValues().supportPole()) * (s - c));

        double alphaRadiant = Math.asin(hc/simInfo.getSimValues().supportPole());
        double miniRadiant = Math.atan((m1PositionOut.y-m2PositionOut.y)/(m1PositionOut.x-m2PositionOut.x));

        double newRadiant = alphaRadiant + miniRadiant;

        double cxOffset = Math.cos(newRadiant) * simInfo.getSimValues().supportPole();
        double cyOffset = Math.sin(newRadiant) * simInfo.getSimValues().supportPole();

        HelperPoint C = new HelperPoint(m1PositionOut.x + cxOffset, m1PositionOut.y + cyOffset);

        double beta = Math.asin(hc/simInfo.getSimValues().intersection());
        double ls3 = simInfo.getSimValues().mainPole()-simInfo.getSimValues().intersection();

        double pencilXOffset = Math.cos( Math.abs((beta - miniRadiant - Math.PI)) ) * ls3;
        double pencilYOffset = Math.sin(Math.abs((beta - miniRadiant - Math.PI))) * ls3;

        HelperPoint pencil = new HelperPoint(C.x + pencilXOffset, C.y + pencilYOffset);

        double absX = Math.abs(pencil.x - simInfo.getSimValues().middlePoint().x());
        double absY = Math.abs(pencil.y - simInfo.getSimValues().middlePoint().y());

        double abs = Math.sqrt(absX * absX + absY * absY);
        double radiantOffset1 = Math.atan(absY/absX);

        double newPencil1X = Math.cos(Math.toRadians(middleDegree) + radiantOffset1) * abs;
        double newPencil1Y = Math.sin(Math.toRadians(middleDegree) + radiantOffset1) * abs;

        pencil = new HelperPoint(newPencil1X + simInfo.getSimValues().middlePoint().x(), newPencil1Y + simInfo.getSimValues().middlePoint().y());

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
    }
}