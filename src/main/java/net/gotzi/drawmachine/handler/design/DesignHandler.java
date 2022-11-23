package net.gotzi.drawmachine.handler.design;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class DesignHandler {

    public DesignHandler() {
        instance = this;
    }

    private final Map<DesignColor, DesignColorChanges> designColorChangesMap = new HashMap<>();

    public DesignColorChanges getDesignColorChanges(DesignColor designColor) {
        return this.designColorChangesMap.get(designColor);
    }

    /**
     * Register a design color with a default color.
     *
     * @param designColor The design color you want to change.
     * @param defaultColor The default color of the design color.
     */
    public void registerDesignColor(DesignColor designColor, Color defaultColor) {
        DesignColorChanges designs = new DesignColorChanges();
        designs.setDefaultColor(defaultColor);

        this.designColorChangesMap.put(designColor, designs);
    }

    /**
     * > Removes the design color from the design color changes map
     *
     * @param designColor The DesignColor object that you want to deregister.
     */
    public void deregisterDesignColor(DesignColor designColor) {
        this.designColorChangesMap.remove(designColor);
    }

    private static DesignHandler instance;

    public static DesignHandler getInstance() {
        return instance;
    }
}
