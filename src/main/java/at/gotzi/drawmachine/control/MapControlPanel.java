package at.gotzi.drawmachine.control;

import javax.swing.*;
import java.awt.*;

public class MapControlPanel<T extends Component> extends JPanel {

    private T component;
    private final Dimension dimension;
    private final MapLayout mapControlLayout;

    public MapControlPanel(Dimension dimension, int border) {
        this.dimension = dimension;
        MapControlLayout mapControlLayout = new MapControlLayout(this, border);

        setBackground(Color.BLACK);
        setLayout(mapControlLayout);
        addMouseListener(mapControlLayout);
        addMouseWheelListener(mapControlLayout);

        this.mapControlLayout = mapControlLayout;
    }

    public void setComponent(T t) {
        add(t);
        mapControlLayout.setComponent(t);
        this.component = t;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public T getComponent() {
        return component;
    }

    public MapLayout getMapControlLayout() {
        return mapControlLayout;
    }
}