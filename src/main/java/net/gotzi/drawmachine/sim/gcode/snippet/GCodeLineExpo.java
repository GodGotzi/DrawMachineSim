/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.sim.gcode.snippet;

import net.gotzi.drawmachine.sim.gcode.GCodeConstructError;

public class GCodeLineExpo extends GCodeLine {

    private double acceleration;
    private double accelerationDist;

    public GCodeLineExpo(String str, boolean inSequence) throws GCodeConstructError {
        super(str, inSequence);
    }

    @Override
    public void constructExtraInfo(String[] commands) {
        int index = findValueIndex(commands, 'W');
        this.acceleration = Double.parseDouble(commands[index].substring(1));
        index = findValueIndex(commands, 'T');
        this.accelerationDist = Double.parseDouble(commands[index].substring(1));
    }

    @Override
    public double calculateDegree(double timestamp) {
        return acceleration * Math.pow(timestamp/accelerationDist, 2) / 2.0;
    }
}