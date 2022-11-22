package net.gotzi.drawmachine.handler.design;

import java.awt.*;
import java.util.ArrayList;

public class DesignColorChanges extends ArrayList<ChangeDesign> {

    private Color color;

    public void redesignAll(Color newColor) {
        this.forEach(changeDesign -> changeDesign.redesign(newColor));
        this.color = newColor;
    }

    public void registerPossibleChange(ChangeDesign changeDesign) {
        add(changeDesign);
        changeDesign.redesign(color);
    }

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
