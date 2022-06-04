package at.gotzi.drawmachine.view;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ResizeHandler extends ComponentAdapter {

    private final Component parent;
    private final ResizeAction resizeAction;

    public ResizeHandler(Component parent, ResizeAction resizeAction) {
        this.parent = parent;
        this.resizeAction = resizeAction;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        resizeAction.resize(parent.getWidth(), parent.getHeight());
    }
}
