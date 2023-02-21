package net.gotzi.drawmachine.api.sim;

import net.gotzi.drawmachine.sim.gcode.GCode;

public record SimProgramInfo(SimEditorValues saved, GCode gcode) {

}
