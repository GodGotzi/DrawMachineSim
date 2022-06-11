package at.gotzi.drawmachine.menubar.actions;

import at.gotzi.drawmachine.DrawMachineSim;
import at.gotzi.drawmachine.api.sim.SimModeInfo;
import at.gotzi.drawmachine.builder.ModeStringBuilder;
import at.gotzi.drawmachine.view.file.ModeFileView;
import at.gotzi.drawmachine.view.workspace.Workspace;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class NewModeFileAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = getName();
        if (str == null) return;
        if (!str.contains(".mdm")) str += ".mdm";

        SimModeInfo simModeInfo = new SimModeInfo();
        Object data = createData(simModeInfo);

        try {
            this.createFile(str, data);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(DrawMachineSim.getInstance().getWindow().getFrame(), "Problem while creating File: " + ex.getMessage());
            return;
        }

        ModeFileView modeFileView = new ModeFileView(simModeInfo, str);
        DrawMachineSim.getInstance().getFileHub().openFilePage(modeFileView);
    }

    private Object createData(SimModeInfo simModeInfo) {
        ModeStringBuilder modeStringBuilder = new ModeStringBuilder(simModeInfo);
        modeStringBuilder.build();
        return modeStringBuilder.getResult();
    }

    private void createFile(String str, Object data) throws IOException {
        Workspace workspace = DrawMachineSim.getInstance().getWorkspace();
        File newFile = new File(workspace.getDirectoryPath() + "\\" + str);
        boolean ret = newFile.createNewFile();

        if (!ret) {
            JOptionPane.showMessageDialog(DrawMachineSim.getInstance().getWindow().getFrame(), "File/Mode already exists");
            return;
        }

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newFile));
        bufferedWriter.write(data.toString());
        bufferedWriter.close();
    }

    private String getName() {
        return JOptionPane.showInputDialog("Name your Mode");
    }
}
