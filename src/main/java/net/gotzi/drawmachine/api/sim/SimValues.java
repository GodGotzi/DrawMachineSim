package net.gotzi.drawmachine.api.sim;

public record SimValues(SimPoint middlePoint, //DC gear Punkt ausgehend Canvas
                        SimPoint m1Point, //Stepper 1 Punkt ausgehend Canvas
                        SimPoint m2Point, //Stepper 2 Punkt ausgehend Canvas
                        double m1Horn, //Stepper 1 Arm länge
                        double m2Horn, //Stepper 2 Arm länge
                        double mainPole, //Länge a Hauptlänge
                        double supportPole, //Länge b
                        double intersection, //Schnittpunkt von mainPole und supportPole Länge a
                        double speedMiddle,
                        double speedM1,
                        double speedM2,
                        int baseSteps) {
}