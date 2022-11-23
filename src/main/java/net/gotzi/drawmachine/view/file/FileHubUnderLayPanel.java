package net.gotzi.drawmachine.view.file;

import net.gotzi.drawmachine.control.UnderLayPanel;

import java.awt.*;

public class FileHubUnderLayPanel extends UnderLayPanel {

    public FileHubUnderLayPanel(Component component) {
        super(component);

        setNorthBorderThickness(0);
        setEastBorderThickness(2);
        setSouthBorderThickness(2);
        setWestBorderThickness(2);
    }

    /**
     * This function paints the background of the JPanel.
     *
     * @param g The Graphics object that is passed in.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(getColor());
        graphics2D.fillRect(0, 0, getWidth(), 27);
    }
}