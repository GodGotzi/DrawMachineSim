package net.gotzi.drawmachine.sim.gcode;

import java.util.ArrayList;
import java.util.List;

public class GCode {

    public String[] source;

    public GCode(String[] source) {
        this.source = source;
    }

    public String[] getSource() {
        return source;
    }
}
