package net.gotzi.drawmachine.manager;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.api.sim.SimModeInfo;
import net.gotzi.drawmachine.api.sim.SimPoint;
import net.gotzi.drawmachine.api.sim.SimRawValues;
import net.gotzi.drawmachine.view.workspace.Workspace;
import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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

        private Object writeData(File file, SimModeInfo simModeInfo) {
            FileWriter fileWriter = new FileWriter(file);


        }

        public SimModeInfo getSimModeInfo() {
            return simModeInfo;
        }
    }

    public static class ModeFileSaver {

        private final File file;
        private final SimModeInfo simModeInfo;

        public ModeFileSaver(File file, SimModeInfo simModeInfo) {
            this.file = file;
            this.simModeInfo = simModeInfo;
        }

        private void saveFile(Object data) throws IOException {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(data.toString());
            bufferedWriter.close();
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

    public static class ModeFileLoader {

        private final File file;

        public ModeFileLoader(File file) {
            this.file = file;
        }

        public SimModeInfo loadSimModeInfo() throws IOException {
            SimPoint middlePoint, m1Point, m2Point;
            double m1Horn, m2Horn, mainPole, supportPole, intersection, speedMiddle, speedM1, speedM2;
            double x, y;

            Path filePath = Path.of(file.getAbsolutePath());
            String content = Files.readString(filePath);

            JSONObject jsonObject = new JSONObject(content);
            JSONObject values = jsonObject.getJSONObject("values");
            JSONObject points = values.getJSONObject("points");

            JSONObject jsonMiddle = points.getJSONObject("middlePoint");
            x = Double.parseDouble(jsonMiddle.getString("x"));
            y = Double.parseDouble(jsonMiddle.getString("y"));
            middlePoint = new SimPoint(x, y);

            JSONObject jsonM1 = points.getJSONObject("m1Point");
            x = Double.parseDouble(jsonM1.getString("x"));
            y = Double.parseDouble(jsonM1.getString("y"));
            m1Point = new SimPoint(x, y);

            JSONObject jsonM2 = points.getJSONObject("m2Point");
            x = Double.parseDouble(jsonM2.getString("x"));
            y = Double.parseDouble(jsonM2.getString("y"));
            m2Point = new SimPoint(x, y);

            JSONObject lengths = values.getJSONObject("lengths");

            m1Horn = Double.parseDouble(lengths.getString("m1Horn"));
            m2Horn = Double.parseDouble(lengths.getString("m2Horn"));
            mainPole = Double.parseDouble(lengths.getString("mainPole"));
            supportPole = Double.parseDouble(lengths.getString("supportPole"));
            intersection = Double.parseDouble(lengths.getString("intersection"));

            JSONObject speeds = values.getJSONObject("speeds");
            speedMiddle = Double.parseDouble(speeds.getString("speedMiddle"));
            speedM1 = Double.parseDouble(speeds.getString("speedM1"));
            speedM2 = Double.parseDouble(speeds.getString("speedM2"));

            return new SimModeInfo(new SimRawValues(
                    middlePoint,
                    m1Point,
                    m2Point,
                    m1Horn,
                    m2Horn,
                    mainPole,
                    supportPole,
                    intersection,
                    speedMiddle,
                    speedM1,
                    speedM2
            ));
        }
    }

}
