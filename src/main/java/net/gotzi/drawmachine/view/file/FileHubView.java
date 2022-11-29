package net.gotzi.drawmachine.view.file;

import net.gotzi.drawmachine.api.sim.SimModeInfo;
import net.gotzi.drawmachine.control.DimensionConstants;
import net.gotzi.drawmachine.control.UnderLayPanel;
import net.gotzi.drawmachine.data.ModeLoader;
import net.gotzi.drawmachine.handler.design.DesignColor;
import net.gotzi.drawmachine.handler.design.DesignHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHubView extends JTabbedPane implements FileHub {

    public void openFilePage(FileView fileView) {
        addTab(fileView.getName(), fileView);
    }

    public void openFilePage(File file) {

        String name;

        if (this.indexOfTab(file.getName()) != -1) {
            int index = this.indexOfTab(file.getName());

            if (this.getComponentAt(index) instanceof FileView fileView) {
                if (file.getAbsolutePath().equals(fileView.getAbsolutePath()))
                    return;
                else
                    this.setTitleAt(index, fileView.getAbsolutePath());
            }

            name = file.getAbsolutePath();
        } else
            name = file.getName();

        SimModeInfo simModeInfo = null;

        try {
            Path filePath = Path.of(file.getAbsolutePath());
            String content = Files.readString(filePath);
            ModeLoader modeLoader = new ModeLoader(content);
            modeLoader.load();

            simModeInfo = modeLoader.getResult();
        } catch (Exception e) {
            //TODO
        }

        if (simModeInfo == null) {
            return;
        }


        ModeFileView modeFileView = new ModeFileView(simModeInfo, name, file);
        addTab(modeFileView.getName(), modeFileView);
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

        exitButton.setPreferredSize(
                DimensionConstants.getConstantDimension("filehub.view.tabcomponent.exitbutton.default")
        );

        UnderLayPanel underLayPanel = new UnderLayPanel(exitButton);
        underLayPanel.setWestBorderThickness(20);
        underLayPanel.setNorthBorderThickness(2);
        underLayPanel.setSouthBorderThickness(2);
        underLayPanel.setEastBorderThickness(0);
        underLayPanel.setOpaqueAll(false);

        underLayPanel.setPreferredSize(
                DimensionConstants.getConstantDimension("filehub.view.tabcomponent.default")
        );

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
