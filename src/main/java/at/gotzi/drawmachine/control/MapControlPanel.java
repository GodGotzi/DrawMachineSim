package at.gotzi.drawmachine.control;

import javax.swing.*;
import java.awt.*;

public class MapControlPanel<T extends Component> extends JPanel {

    private T component;
    private final Dimension dimension;
    private final MapControlLayout mapControlLayout;

    public MapControlPanel(Dimension dimension) {
        this.dimension = dimension;
        this.mapControlLayout = new MapControlLayout(this);

        setBackground(Color.BLACK);
        setLayout(mapControlLayout);
        addMouseListener(mapControlLayout);
        addMouseWheelListener(mapControlLayout);
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
}