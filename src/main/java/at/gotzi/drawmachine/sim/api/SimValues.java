package at.gotzi.drawmachine.sim.api;

public record SimValues(SimPoint middlePoint,
                        SimPoint m1,
                        SimPoint m2,
                        double lm1,
                        double lm2,
                        double ls1,
                        double ls2,
                        double lp,
                        double speedMiddle,
                        double speedM1,
                        double speedM2,
                        int baseSteps) {
}