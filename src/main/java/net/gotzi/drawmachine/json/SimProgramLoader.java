package net.gotzi.drawmachine.json;

import net.gotzi.drawmachine.api.sim.SimPoint;
import net.gotzi.drawmachine.api.sim.SimProgramInfo;
import net.gotzi.drawmachine.api.sim.SimRawValues;
import org.json.JSONObject;
import org.json.JSONWriter;

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

                .key("speeds")
                .object()
                .key("speedMiddle")
                .value(simProgramInfo.saved().speedMiddle())
                .key("speedM1")
                .value(simProgramInfo.saved().speedM1())
                .key("speedM2")
                .value(simProgramInfo.saved().speedM2())
                .endObject()

                .endObject()
                .endObject();


        return new JSONObject(stringBuilder.toString()).toString(4);
    }

    public SimProgramInfo load(String source) {
        SimPoint middlePoint, m1Point, m2Point;
        double m1Horn, m2Horn, mainPole, supportPole, intersection, speedMiddle, speedM1, speedM2;
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

        JSONObject speeds = values.getJSONObject("speeds");
        speedMiddle = speeds.getDouble("speedMiddle");
        speedM1 = speeds.getDouble("speedM1");
        speedM2 = speeds.getDouble("speedM2");

        return new SimProgramInfo(new SimRawValues(
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

    public SimProgramInfo getDefault() {
        return new SimProgramInfo(new SimRawValues(
                new SimPoint(1050, 1050),
                new SimPoint(1050-700, 1050-2400),
                new SimPoint(1050+700, 1050-2400),
                200,
                200,
                2200,
                1400,
                1100,
                360,
                60,
                32
        ));
    }
}
