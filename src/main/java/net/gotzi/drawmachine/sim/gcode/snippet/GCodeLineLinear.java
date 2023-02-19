package net.gotzi.drawmachine.sim.gcode.snippet;

import net.gotzi.drawmachine.sim.gcode.GCodeConstructError;

public class GCodeLineLinear extends GCodeLine {

    public GCodeLineLinear(String str, boolean inSequence) throws GCodeConstructError {
        super(str, inSequence);
    }

    public double calculateDegree(double timestamp) {
        return this.degree/(double)this.duration * timestamp;
    }

    @Override
    public void constructExtraInfo(String[] commands) {

    }
}
