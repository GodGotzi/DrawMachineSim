/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.handler.hotkey;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.api.Action;

import javax.swing.*;

public interface IHotKeyHandler {

    void addHotKey(String key, KeyStroke comb, Action<DrawMachineSim> action);

}
