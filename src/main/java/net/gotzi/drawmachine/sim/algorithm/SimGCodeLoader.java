package net.gotzi.drawmachine.sim.algorithm;

import net.gotzi.drawmachine.sim.gcode.*;
import net.gotzi.drawmachine.sim.gcode.snippet.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SimGCodeLoader {

    private double middleOffset = 0;
    private double stepperAOffset = 0;
    private double stepperBOffset = 0;

    private final GCode gCode;
    private final long fullTime;
    private double currentTimestamp;
    private final List<GCodeSnippet> gCodeSnippets = new ArrayList<>();

    public SimGCodeLoader(GCode gCode) {
        this.gCode = gCode;
        try {
            this.compileGCode();
        } catch (GCodeConstructError e) {
            throw new RuntimeException(e);
        }

        this.fullTime = calculateTime();
    }

    private void compileGCode() throws GCodeConstructError {
        String line;

        for (int i = 0; i < gCode.source.length; i++) {
            line = gCode.source[i];
            String[] commands = line.split(" ");

            if (line.contains("G54")) {
                this.middleOffset = findValue(commands, Motor.M.toChar());
                this.stepperAOffset = findValue(commands, Motor.A.toChar());
                this.stepperBOffset = findValue(commands, Motor.B.toChar());
            } else if (line.contains("G8")) {
                List<GCodeLine> lineList = new ArrayList<>();
                int j;
                for (j = 0; j < 3 && !gCode.source[j+1+i].contains("G9"); j++) {
                    GCodeLine gCodeLine = computeGCommand(gCode.source[j + 1 + i]
                            .split(" ")[0], gCode.source[j + 1 + i], true);
                    lineList.add(gCodeLine);
                }

                i+=j+1;

                GCodeSequence sequence = new GCodeSequence(lineList.toArray(new GCodeLine[0]), line);
                gCodeSnippets.add(sequence);
            } else {
                GCodeLine gCodeLine = computeGCommand(commands[0], line, false);

                gCodeSnippets.add(gCodeLine);
            }
        }
    }

    private long calculateTime() {
        String line;
        long time = 0;

        for (int i = 0; i < gCode.getSource().length; i++) {
            line = gCode.getSource()[i];

            if (line.contains("G8")) {
                time += findLargestTimestamp(gCode.getSource(), i);
            } else {
                String[] commands = line.split(" ");
                time += getTime(commands);
            }
        }

        return time;
    }

    private int findLargestTimestamp(String[] source, int offset) {
        int time = 0;
        int new_time;

        for (int i = offset; i < source.length; i++) {
            String[] commands = source[i].split(" ");

            if (time < (new_time = getTime(commands)))
                time = new_time;

            if (Arrays.stream(commands).anyMatch(cmd -> cmd.contains("G9")))
                return time;
        }

        //TODO throw exception
        return 0;
    }

    private int getTime(String[] commands) {
         Optional<String> option=  Arrays.stream(commands).filter(cmd -> cmd.contains("D")).findAny();

         if (option.isEmpty())
             return 0;

         return Integer.parseInt(option.get().replace("D", ""));
    }

    public boolean isFinished() {
        return currentTimestamp == fullTime;
    }

    private double getAxisDegree(double timestamp, Motor motor) {
        double time = 0;
        double degree = 0;

        for (GCodeSnippet snippet : gCodeSnippets) {
            if (time + snippet.getDuration() >= timestamp) {
                if (snippet instanceof GCodeSequence sequence) {
                    Optional<GCodeLine> optional = Arrays.stream(sequence.getLines())
                            .filter(line -> line.getMotor() == motor).findAny();

                    if (optional.isPresent()) {
                        if (time + optional.get().getDuration() >= timestamp) {
                            time = timestamp;
                            degree += optional.get().calculateDegree(timestamp);
                        } else {
                            time += optional.get().getDuration();
                            degree += optional.get().getDegree();
                        }
                    }
                } else if (snippet instanceof GCodeLine line) {
                    time = timestamp;
                    degree += line.calculateDegree(timestamp);
                }
            } else {
                time += snippet.getDuration();

                if (snippet instanceof GCodeSequence sequence) {
                    Optional<GCodeLine> optional = Arrays.stream(sequence.getLines())
                            .filter(line -> line.getMotor() == motor).findAny();

                    if (optional.isPresent()) {
                        degree += optional.get().getDegree();
                    }
                } else if (snippet instanceof GCodeLine line) {
                    degree += line.getDegree();
                }
            }
        }

        return degree;
    }

    public double getMiddleDegree(double timestamp) {
        this.currentTimestamp = timestamp;
        double degree = getAxisDegree(timestamp, Motor.M);

        return degree + middleOffset;
    }

    public double getStepperADegree(double timestamp) {
        this.currentTimestamp = timestamp;

        double degree = getAxisDegree(timestamp, Motor.A);

        return degree + stepperAOffset;
    }

    public double getStepperBDegree(double timestamp) {
        this.currentTimestamp = timestamp;

        double degree = getAxisDegree(timestamp, Motor.B);

        return degree + stepperBOffset;
    }

    public long getFullTime() {
        return fullTime;
    }

    private int findValue(String[] commandSplit, char cmd) {
        for (int i = 0; i < commandSplit.length; i++) {
            if (commandSplit[i].charAt(0) == cmd)
                return i;
        }

        return -1;
    }

    private GCodeLine computeGCommand(String g, String str, boolean inSequence) throws GCodeConstructError {
        return switch (Integer.parseInt(g.substring(1))) {
            case 0 -> new GCodeLineLinear(str, inSequence);
            case 1 -> new GCodeLineExpo(str, inSequence);

            default -> {
                throw new GCodeConstructError();
            }
        };
    }
}
