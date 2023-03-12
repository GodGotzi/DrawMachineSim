/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.view.menubar.actions;

import net.gotzi.drawmachine.DrawMachineSim;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SaveAllProgramAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            DrawMachineSim.getInstance().getView().getFileHub().saveAll();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
