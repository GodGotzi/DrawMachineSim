package net.gotzi.drawmachine.handler.design;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class DesignHandler {

    private final Map<DesignColor, DesignColorChanges> designColorChangesMap = new HashMap<>();

    public DesignColorChanges getDesignColorChanges(DesignColor designColor) {
        return this.designColorChangesMap.get(designColor);
    }

    public void registerDesignColor(DesignColor designColor, Color defaultColor) {
        DesignColorChanges designs = new DesignColorChanges();
        designs.setDefaultColor(defaultColor);

        this.designColorChangesMap.put(designColor, designs);
    }

    public void deregisterDesignColor(DesignColor designColor) {
        this.designColorChangesMap.remove(designColor);
    }
}
