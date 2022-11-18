package net.gotzi.drawmachine.handler;

import net.gotzi.drawmachine.api.KeyAction;

import javax.swing.*;

public interface IHotKeyHandler {

    void addHotKey(KeyStroke comb, KeyAction action);

}
