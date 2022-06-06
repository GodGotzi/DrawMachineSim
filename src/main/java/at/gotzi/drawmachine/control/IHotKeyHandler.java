package at.gotzi.drawmachine.control;

import at.gotzi.drawmachine.api.KeyAction;

import javax.swing.*;

public interface IHotKeyHandler {

    void addHotKey(KeyStroke comb, KeyAction action);

}
