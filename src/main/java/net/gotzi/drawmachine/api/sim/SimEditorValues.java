package net.gotzi.drawmachine.api.sim;

public record SimEditorValues(SimPoint middlePoint, //DC gear Punkt ausgehend Canvas
                              SimPoint m1Point, //Stepper 1 Punkt ausgehend Canvas
                              SimPoint m2Point, //Stepper 2 Punkt ausgehend Canvas
                              double m1Horn, //Stepper 1 Arm länge
                              double m2Horn, //Stepper 2 Arm länge
                              double mainPole, //Länge a Hauptlänge
                              double supportPole, //Länge b
                              double intersection) {
}