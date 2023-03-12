/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.handler.design;

import java.awt.*;
import java.util.ArrayList;

public class DesignColorChanges extends ArrayList<ChangeDesign> {

    private Color color;

    public void redesignAll(Color newColor) {
        this.forEach(changeDesign -> changeDesign.redesign(newColor));
        this.color = newColor;
    }

    /**
     * Add the change design to the list of possible changes, and then redesign the change design with the current color.
     *
     * @param changeDesign The object that will be changed.
     */
    public void registerPossibleChange(ChangeDesign changeDesign) {
        add(changeDesign);
        changeDesign.redesign(color);
    }

    /**
     * Remove the given change design from the list of possible changes.
     *
     * @param changeDesign The ChangeDesign object that is being deregistered.
     */
    public void deregisterPossibleChange(ChangeDesign changeDesign) {
        remove(changeDesign);
    }

    public void setDefaultColor(Color defaultColor) {
        this.color = defaultColor;
    }

    public Color getDefaultColor() {
        return color;
    }
}
