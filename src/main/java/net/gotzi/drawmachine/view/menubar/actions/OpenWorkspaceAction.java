package net.gotzi.drawmachine.view.menubar.actions;

import net.gotzi.drawmachine.DrawMachineSim;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class OpenWorkspaceAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int returnVal = fileChooser.showOpenDialog(DrawMachineSim.getInstance().getView());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            DrawMachineSim.getInstance().getView().getWorkspace().loadWorkspace(file);
        }
    }
}
