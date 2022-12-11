package net.gotzi.drawmachine.sim.editor;

import java.util.ArrayList;
import java.util.List;

public class GCode extends ArrayList<String> {

    public GCode() {
    }

    public GCode(List<String> list) {
        addAll(list);
    }

}
