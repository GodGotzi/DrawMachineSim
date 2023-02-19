package net.gotzi.drawmachine.json;

import net.gotzi.drawmachine.api.sim.SimPoint;
import net.gotzi.drawmachine.api.sim.SimProgramInfo;
import net.gotzi.drawmachine.api.sim.SimRawValues;
import net.gotzi.drawmachine.sim.gcode.GCode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SimProgramLoader {

    public String unload(SimProgramInfo simProgramInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        JSONWriter jsonWriter = new JSONWriter(stringBuilder);

        jsonWriter.object()
                .key("values")
                .object()

                .key("points")
                .object()

                .key("middlePoint")
                .object()
                .key("x")
                .value(simProgramInfo.saved().middlePoint().x())
                .key("y")
                .value(simProgramInfo.saved().middlePoint().y())
                .endObject()

                .key("m1Point")
                .object()
                .key("x")
                .value(simProgramInfo.saved().m1Point().x())
                .key("y")
                .value(simProgramInfo.saved().m1Point().y())
                .endObject()

                .key("m2Point")
                .object()
                .key("x")
                .value(simProgramInfo.saved().m2Point().x())
                .key("y")
                .value(simProgramInfo.saved().m2Point().y())
                .endObject()

                .endObject()

                .key("lengths")
                .object()
                .key("m1Horn")
                .value(simProgramInfo.saved().m1Horn())
                .key("m2Horn")
                .value(simProgramInfo.saved().m2Horn())
                .key("mainPole")
                .value(simProgramInfo.saved().mainPole())
                .key("supportPole")
                .value(simProgramInfo.saved().supportPole())
                .key("intersection")
                .value(simProgramInfo.saved().intersection())
                .endObject()
                .endObject()
                .key("gCode")
                .array();





        for (String line : simProgramInfo.saved().gcode().getSource())
            jsonWriter.value(line);

        jsonWriter
                .endArray()
                .endObject();


        return new JSONObject(stringBuilder.toString()).toString(4);
    }

    public SimProgramInfo load(String source) {
        SimPoint middlePoint, m1Point, m2Point;
        double m1Horn, m2Horn, mainPole, supportPole, intersection;
        double x, y;

        JSONObject jsonObject = new JSONObject(source);
        JSONObject values = jsonObject.getJSONObject("values");
        JSONObject points = values.getJSONObject("points");

        JSONObject jsonMiddle = points.getJSONObject("middlePoint");
        x = jsonMiddle.getDouble("x");
        y = jsonMiddle.getDouble("y");
        middlePoint = new SimPoint(x, y);

        JSONObject jsonM1 = points.getJSONObject("m1Point");
        x = jsonM1.getDouble("x");
        y = jsonM1.getDouble("y");
        m1Point = new SimPoint(x, y);

        JSONObject jsonM2 = points.getJSONObject("m2Point");
        x = jsonM2.getDouble("x");
        y = jsonM2.getDouble("y");
        m2Point = new SimPoint(x, y);

        JSONObject lengths = values.getJSONObject("lengths");

        m1Horn = lengths.getDouble("m1Horn");
        m2Horn = lengths.getDouble("m2Horn");
        mainPole = lengths.getDouble("mainPole");
        supportPole = lengths.getDouble("supportPole");
        intersection = lengths.getDouble("intersection");

        JSONArray array = jsonObject.getJSONArray("gCode");

        String[] lines = array.toList().stream().map(Object::toString).toList().toArray(new String[0]);
        GCode gCode = new GCode(lines);

        System.out.println(Arrays.toString(gCode.getSource()));

        return new SimProgramInfo(new SimRawValues(
                middlePoint,
                m1Point,
                m2Point,
                m1Horn,
                m2Horn,
                mainPole,
                supportPole,
                intersection,
                gCode
        ));
    }

    /*

    all numbers can be written as non-whole Numbers

    G0S -> begin einer Sequence (Argument T -> die Zeit in der der Block bearbeitet werden muss)
    all commands in between this sequence should running / started at the same time
    G0E -> end einer Sequence

    G0 -> (Argument (endless) motors degree, Argument T -> die Zeit in der der Befehl ausgeführt werden muss)



     */

    public SimProgramInfo getDefault() {
        String[] source = new String[]{
                "G0S T10000",
                "G0 A4320 T10000",
                "G0 B4320 T10000",
                "G0E",
                "G1 A4320 M4320 S5 P20 T10000",
                "G2 B4320 M4320 S5 P20 E5000 T10000"
        };

        GCode gCode = new GCode(source);

        return new SimProgramInfo(new SimRawValues(
                new SimPoint(1050, 1050),
                new SimPoint(1050-700, 1050-2400),
                new SimPoint(1050+700, 1050-2400),
                200,
                200,
                2200,
                1400,
                1100,
                gCode
        ));
    }
}
