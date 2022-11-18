package net.gotzi.drawmachine.handler;

import net.gotzi.drawmachine.api.KeyAction;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

public class HotKeyHandler extends KeyAdapter implements IHotKeyHandler {

    private final Map<KeyStroke, KeyAction> map;

    private final List<KeyStroke> currentlyClicked;

    public HotKeyHandler() {
        this.map = new HashMap<>();
        this.currentlyClicked = new LinkedList<>();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.currentlyClicked.add(KeyStroke.getKeyStrokeForEvent(e));
        this.runKeyActions(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.currentlyClicked.add(KeyStroke.getKeyStrokeForEvent(e));
    }

    private void runKeyActions(KeyEvent keyEvent) {
        map.entrySet().stream()
                .filter(entry -> currentlyClicked.contains(entry.getKey())).forEach(entry -> runAction(entry, keyEvent));
    }

    private void runAction(Map.Entry<KeyStroke, KeyAction> entry, KeyEvent keyEvent) {
        entry.getValue().run(keyEvent);
        currentlyClicked.remove(entry.getKey());
    }

    @Override
    public void addHotKey(KeyStroke keyStroke, KeyAction action) {
        map.put(keyStroke, action);
    }
}
