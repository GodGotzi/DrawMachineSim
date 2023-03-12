/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.api.sim;

import net.gotzi.drawmachine.sim.gcode.GCode;

public record SimProgramInfo(SimEditorValues saved, GCode gcode) {

}
