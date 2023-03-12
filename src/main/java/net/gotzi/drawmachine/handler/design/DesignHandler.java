/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.handler.design;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DesignHandler {

    public DesignHandler() {
        instance = this;
    }

    private final Map<DesignColor, DesignColorChanges> designColorChangesMap = new HashMap<>();

    private final Map<Class<?>, ChangeManualDesign> manualChanges = new HashMap<>();

    public DesignColorChanges getDesignColorChanges(DesignColor designColor) {
        return this.designColorChangesMap.get(designColor);
    }

    public void registerManualChange(Class<?> cls, ChangeManualDesign changeDesign) {
        this.manualChanges.put(cls, changeDesign);
    }

    public void deregisterManualChange(Class<?> cls) {
        this.manualChanges.remove(cls);
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

    public <T> void manualColorChange(T o) {
        Class<?> cls = o.getClass();

        try {
            for (Field field : cls.getDeclaredFields()) {
                field.setAccessible(true);
                Object obj = field.get(o);
                if (manualChanges.containsKey(obj.getClass())) {
                    if (obj instanceof JComponent jComponent) {
                        manualChanges.get(obj.getClass()).redesign(jComponent);
                    }
                }

                field.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    private static DesignHandler instance;

    public static DesignHandler getInstance() {
        return instance;
    }
}
