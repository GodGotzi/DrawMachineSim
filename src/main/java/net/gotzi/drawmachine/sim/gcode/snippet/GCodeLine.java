/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.sim.gcode.snippet;

import net.gotzi.drawmachine.sim.gcode.GCodeConstructError;
import net.gotzi.drawmachine.sim.gcode.Motor;

public abstract class GCodeLine implements GCodeSnippet {

    private final boolean inSequence;

    protected long duration;
    protected double degree;
    private Motor motor;

    public GCodeLine(String str, boolean inSequence) throws GCodeConstructError {
        this.inSequence = inSequence;


        String[] commands = str.trim().split(" ");
        this.constructGCodeLineFromStr(commands);
        this.constructExtraInfo(commands);
    }

    public double getDegree() {
        return degree;
    }

    public Motor getMotor() {
        return motor;
    }

    @Override
    public long getDuration() {
        return duration;
    }

    @Override
    public String getSnippetInfo() {
        return "";
    }

    private void constructGCodeLineFromStr(String[] commandSplit) throws GCodeConstructError {

        this.motor = Motor.valueOf(String.valueOf(commandSplit[1].charAt(0)));
        this.degree = Double.parseDouble(commandSplit[1].substring(1));

        long duration = Long.parseLong(commandSplit[findValueIndex(commandSplit, 'D')].substring(1));

        if (duration == -1 && !inSequence)
            throw new GCodeConstructError();

        this.duration = duration;
    }

    public abstract double calculateDegree(double timestamp);

    public abstract void constructExtraInfo(String[] commands);

    protected int findValueIndex(String[] commandSplit, char cmd) {
        for (int i = 0; i < commandSplit.length; i++) {
            if (commandSplit[i].charAt(0) == cmd)
                return i;
        }

        return -1;
    }
}