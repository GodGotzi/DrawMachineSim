package at.gotzi.drawmachineapp.builder;

import at.gotzi.drawmachineapp.control.ButtonMouseAdapter;
import at.gotzi.drawmachineapp.control.ControlComponent;

import javax.swing.*;
import java.awt.*;

public class GButton extends JButton implements ControlComponent {

    // These are the variables that are used in the GButton class.
    private boolean over;
    private Color color;
    private Color colorOver;
    private Color colorClick;
    private Color borderColor;
    private int radius = 0;

    // The constructor for the GButton class. It sets the color, colorOver, colorClick, and borderColor variables
    // to the values passed in as parameters. It then calls the addListener function, which adds a MouseListener to the
    // button.
    // It then sets the content area filled to false.
    public GButton(Color mainColor, Color colorOver, Color colorClick, Color borderColor) {
        setColor(mainColor);
        setColorOver(colorOver);
        setColorClick(colorClick);
        setBorderColor(borderColor);
        addListener();
        setContentAreaFilled(false);
    }

    /**
     * When the mouse is pressed, the button's background color is set to the colorClick variable. When the mouse is
     * released, the background color is set to the colorOver variable if the mouse is over the button, or the color
     * variable if it is not. When the mouse enters the button, the border color is set to the colorOver variable, and when
     * the mouse exits the button, the border color is set to the color variable
     */
    private void addListener() {
        addMouseListener(new ButtonMouseAdapter(this, colorClick, colorOver, color));
    }

    /**
     * Returns true if the game is over, false otherwise.
     *
     * @return The boolean value of the variable over.
     */
    public boolean isOver() {
        return over;
    }

    /**
     * This function returns the color of the object.
     *
     * @return The color of the car.
     */
    public Color getColor() {
        return color;
    }

    /**
     * This function returns the colorOver variable
     *
     * @return The colorOver variable is being returned.
     */
    public Color getColorOver() {
        return colorOver;
    }

    /**
     * This function returns the colorClick variable
     *
     * @return The colorClick variable is being returned.
     */
    public Color getColorClick() {
        return colorClick;
    }

    /**
     * This function returns the border color of the object.
     *
     * @return The borderColor variable.
     */
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * This function returns the radius of the circle.
     *
     * @return The radius of the circle.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * This function sets the over variable to the value of the over parameter.
     *
     * @param over This is a boolean value that determines whether the game is over or not.
     */
    public void setOver(boolean over) {
        this.over = over;
    }

    /**
     * This function sets the color of the button to the color passed in as a parameter.
     *
     * @param color The color of the button
     */
    public void setColor(Color color) {
        this.color = color;
        setBackground(color);
    }

    /**
     * This function sets the colorOver variable to the colorOver parameter.
     *
     * @param colorOver The color of the button when the mouse is over it.
     */
    public void setColorOver(Color colorOver) {
        this.colorOver = colorOver;
    }

    /**
     * This function sets the color of the button when it is clicked
     *
     * @param colorClick The color of the button when it is clicked.
     */
    public void setColorClick(Color colorClick) {
        this.colorClick = colorClick;
    }

    /**
     * This function sets the border color of the object to the color passed in as a parameter.
     *
     * @param borderColor The color of the border.
     */
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    /**
     * This function sets the radius of the circle to the value of the parameter radius.
     *
     * @param radius The radius of the circle.
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }


    /**
     * "Draw a rounded rectangle with a border, then draw the text on top of it."
     *
     * The first line of the function is a cast. We're casting the Graphics object to a Graphics2D object. This is
     * necessary because we want to use the Graphics2D object's setRenderingHint() function
     *
     * @param g The Graphics object to draw on.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBorderColor());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.setColor(getBorderColor());
        g2.fillRoundRect(2, 2, getWidth()-4, getHeight()-4, radius, radius);
        g2.setColor(getBorderColor());
        g2.fillRoundRect(4, 4, getWidth()-8, getHeight()-8, radius, radius);

        super.paintComponent(g);
    }

    /**
     * A function that is called when component got placed into parent frame, so we need to correctly size the button.
     *
     * @param groupLayout The GroupLayout object that you want to add the component to.
     */
    @Override
    public void sizing(GroupLayout groupLayout) {

        


    }
}
