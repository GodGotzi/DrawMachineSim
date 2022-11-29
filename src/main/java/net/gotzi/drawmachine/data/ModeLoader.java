package net.gotzi.drawmachine.data;

import net.gotzi.drawmachine.api.sim.SimModeInfo;
import net.gotzi.drawmachine.api.sim.SimPoint;
import net.gotzi.drawmachine.api.sim.SimRawValues;
import org.json.JSONObject;

public class ModeLoader extends Loader<SimModeInfo> {

    private final String modeStr;

    private SimModeInfo simModeInfo;

    public ModeLoader(String modeStr) {
        this.modeStr = modeStr;
    }

    @Override
    // Loading the json string into an object.
    public void load() {
        //TODO load json String to SimValues
        SimPoint middlePoint, m1Point, m2Point;
        double m1Horn, m2Horn, mainPole, supportPole, intersection, speedMiddle, speedM1, speedM2;
        double x, y;

        JSONObject jsonObject = new JSONObject(modeStr);
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

        this.simModeInfo = new SimModeInfo(new SimRawValues(
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

    @Override
    public SimModeInfo getResult() {
        return this.simModeInfo;
    }
}
