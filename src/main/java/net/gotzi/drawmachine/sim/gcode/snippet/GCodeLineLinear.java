/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.sim.gcode.snippet;

import net.gotzi.drawmachine.sim.gcode.GCodeConstructError;

public class GCodeLineLinear extends GCodeLine {

    public GCodeLineLinear(String str, boolean inSequence) throws GCodeConstructError {
        super(str, inSequence);
    }

    @Override
    public double calculateDegree(double timestamp) {
        return 0;
    }

    @Override
    public void constructExtraInfo(String[] commands) {

    }
}
