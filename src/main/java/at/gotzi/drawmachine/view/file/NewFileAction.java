package at.gotzi.drawmachine.view.file;

import at.gotzi.drawmachine.DrawMachineSim;
import at.gotzi.drawmachine.view.file.FileView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NewFileAction extends AbstractAction {
    @Override
    public void actionPerformed(ActionEvent e) {
        String str = getName();
        if (str == null) return;
        if (!str.contains(".mdm")) str += ".mdm";

        FileView fileView = new FileView(str);
        DrawMachineSim.getInstance().getFileHub().openNewFilePage(fileView);
    }

    private String getName() {
        return JOptionPane.showInputDialog("Name your Mode");
    }
}
