package net.gotzi.drawmachine.view.file;

import net.gotzi.drawmachine.control.UnderLayPanel;
import net.gotzi.drawmachine.handler.design.DesignColor;
import net.gotzi.drawmachine.handler.design.DesignHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class FileHubView extends JTabbedPane implements FileHub {

    public void openFilePage(FileView fileView) {
        addTab(fileView.getName(), fileView);
    }

    /**
     * "When a tab is added, create a new tab component and add it to the tabbed pane."
     *
     * The first line of the function calls the super class's addTab function. This is important because it adds the tab to
     * the tabbed pane. The next line gets the index of the tab that was just added. The third line creates a new tab
     * component. The last line adds the tab component to the tabbed pane
     *
     * @param title The title of the tab
     * @param component The component to be displayed when this tab is clicked.
     */
    @Override
    public void addTab(String title, Component component) {
        super.addTab(title, component);
        int index = indexOfTab(title);

        JPanel pnlTab = createTabComponent(title);
        this.setTabComponentAt(index, pnlTab);
    }

    /**
     * It creates a JPanel with a JLabel and a JButton, and returns the JPanel
     *
     * @param title The title of the tab
     * @return A JPanel with a JLabel and a JButton.
     */
    private JPanel createTabComponent(String title) {
        JLabel lblTitle = new JLabel(title);
        ExitButton exitButton = new ExitButton();

        exitButton.setClickAction(e -> {
            removeTabAt(indexOfTab(title));
            System.gc();
        });

        exitButton.setPreferredSize(new Dimension(15, 15));

        UnderLayPanel underLayPanel = new UnderLayPanel(exitButton);
        underLayPanel.setWestBorderThickness(20);
        underLayPanel.setNorthBorderThickness(2);
        underLayPanel.setSouthBorderThickness(2);
        underLayPanel.setEastBorderThickness(0);
        underLayPanel.setOpaqueAll(false);

        underLayPanel.setPreferredSize(new Dimension(35, 19));

        return buildMainTabPanel(underLayPanel, lblTitle);
    }

    /**
     * Build a panel with a label and an exit button.
     *
     * @param exitPanel The panel that contains the exit button.
     * @param lblTitle The title of the tab
     * @return A JPanel with a GridBagLayout.
     */
    private JPanel buildMainTabPanel(JPanel exitPanel, JLabel lblTitle) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        panel.add(lblTitle, gbc);

        gbc.gridx++;
        gbc.weightx = 2;
        panel.add(exitPanel, gbc);

        return panel;
    }
}
