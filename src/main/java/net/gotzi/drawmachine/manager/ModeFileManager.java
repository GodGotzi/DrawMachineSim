package net.gotzi.drawmachine.manager;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.api.sim.SimModeInfo;
import net.gotzi.drawmachine.builder.ModeStringBuilder;
import net.gotzi.drawmachine.view.workspace.Workspace;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ModeFileManager {

    public static class ModeFileCreator {

        private final String name;
        private final SimModeInfo simModeInfo;

        public ModeFileCreator(String name, SimModeInfo simModeInfo) {
            this.name = name;
            this.simModeInfo = simModeInfo;
        }

        public File createNewModeFile() {
            Object data = createData(this.simModeInfo);
            File file;

            try {
                file = createFile(name, data);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(DrawMachineSim.getInstance().getWindow(), "Problem while creating File: " + ex.getMessage());
                return null;
            }

            return file;
        }

        private File createFile(String str, Object data) throws IOException {
            Workspace workspace = DrawMachineSim.getInstance().getView().getWorkspace();
            File newFile = new File(workspace.getDirectoryPath() + "\\" + str);
            boolean ret = newFile.createNewFile();

            if (!ret) {
                throw new IOException("File/Mode already Exists");
            }

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newFile));
            bufferedWriter.write(data.toString());
            bufferedWriter.close();

            return newFile;
        }

        private Object createData(SimModeInfo simModeInfo) {
            ModeStringBuilder modeStringBuilder = new ModeStringBuilder(simModeInfo);
            modeStringBuilder.build();
            return modeStringBuilder.getResult();
        }

        public SimModeInfo getSimModeInfo() {
            return simModeInfo;
        }
    }

}
