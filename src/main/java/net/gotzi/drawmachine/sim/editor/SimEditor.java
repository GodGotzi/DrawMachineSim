/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.sim.editor;

import net.gotzi.drawmachine.api.sim.SimEditorValues;

public interface SimEditor {

    SimEditorValues getSimEditorValues();

    int getBaseSteps();


}
