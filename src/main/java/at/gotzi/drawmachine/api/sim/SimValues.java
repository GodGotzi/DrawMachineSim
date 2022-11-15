package at.gotzi.drawmachine.api.sim;

public record SimValues(SimPoint middlePoint, //DC gear Punkt ausgehend Canvas
                        SimPoint m1, //Stepper 1 Punkt ausgehend Canvas
                        SimPoint m2, //Stepper 2 Punkt ausgehend Canvas
                        double lm1, //Stepper 1 Arm länge
                        double lm2, //Stepper 2 Arm länge
                        double ls1, //Länge a Hauptlänge
                        double ls2, //Länge b
                        double s1_2, //Schnittpunkt von ls1 und ls2 Länge a
                        double speedMiddle,
                        double speedM1,
                        double speedM2,
                        int baseSteps) {
}