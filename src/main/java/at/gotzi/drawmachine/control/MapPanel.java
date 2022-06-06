package at.gotzi.drawmachine.control;

import javax.swing.*;
import java.awt.*;

public class MapPanel<T extends Component> extends JPanel {

    private T component;
    private final Dimension dimension;
    private final IMapLayout mapControlLayout;

    public MapPanel(Dimension dimension, int border) {
        this.dimension = dimension;
        MapLayout mapLayout = new MapLayout(this, border);

        setBackground(Color.BLACK);
        setLayout(mapLayout);
        addMouseListener(mapLayout);
        addMouseWheelListener(mapLayout);

        this.mapControlLayout = mapLayout;
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

    public IMapLayout getMapControlLayout() {
        return mapControlLayout;
    }
}