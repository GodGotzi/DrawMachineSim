/*
	Author: Elias (Gotzi) Gottsbacher
	Copyright (c) 2023 Elias Gottsbacher
*/

package net.gotzi.drawmachine.view.hub;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.control.DimensionConstants;
import net.gotzi.drawmachine.control.UnderLayPanel;
import net.gotzi.drawmachine.error.UnsupportedAction;
import net.gotzi.drawmachine.json.SimProgramLoader;
import net.gotzi.drawmachine.view.hub.sim.SimProgramCoordinator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileHubView extends JTabbedPane implements FileHub {

    private final Map<String, Coordinator<?, ?>> coordinatorMap;

    public FileHubView() {
        this.coordinatorMap = new HashMap<>();

        this.initCoordinators();
    }

    public void initCoordinators() {
        SimProgramLoader simProgramLoader = new SimProgramLoader();
        SimProgramCoordinator simProgramCoordinator = new SimProgramCoordinator(simProgramLoader, this);
        coordinatorMap.put("dmsp", simProgramCoordinator);
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

        JPanel pnlTab = createTabComponent(title, (FileView<?>) component);
        this.setTabComponentAt(index, pnlTab);
    }

    public int indexOfTabFileName(String title) {
        for (int i = 0; i < getTabCount(); i++) {
            FileView<?> view = (FileView<?>) getComponentAt(i);

            if (view.getFile().getName().equals(title)) {
                return i;
            }
        }


        return -1;
    }

    public void openFilePage(File file) {
        String fileType = getFileEnding(file.getName());
        Coordinator<?, ?> coordinator = this.coordinatorMap.get(fileType);

        FileView<?> fileView;

        try {
            fileView = coordinator.load(file);
        } catch (Exception e) {
            new UnsupportedAction(DrawMachineSim.getInstance().getView(),
                    "Couldn't load json from file");
            e.printStackTrace();

            return;
        }

        if (fileView == null) {
            setSelectedIndex(
                    indexOfTab(file.getName())
            );

            return;
        }

        addTab(fileView.getName(), fileView);
    }

    public void createFilePage(String name) {
        String fileType = getFileEnding(name);
        System.out.println(fileType);

        Coordinator<?, ?> coordinator = this.coordinatorMap.get(fileType);

        FileView<?> fileView;

        try {
            fileView = coordinator.create(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (fileView == null) {
            setSelectedIndex(
                    indexOfTab(name)
            );

            return;
        }

        addTab(fileView.getName(), fileView);
    }

    /**
     * It creates a JPanel with a JLabel and a JButton, and returns the JPanel
     *
     * @param title The title of the tab
     * @return A JPanel with a JLabel and a JButton.
     */
    private JPanel createTabComponent(String title, FileView<?> component) {
        JLabel lblTitle = new JLabel(title);
        FileView.ExitButton exitButton = new FileView.ExitButton();

        exitButton.setClickAction(e -> removeFileTab(e, component));

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

    private void removeFileTab(MouseEvent mouseEvent, FileView<?> fileView) {
        Coordinator<?, ?> coordinator = coordinatorMap.get(fileView.getFileType());
        try {
            coordinator.save(new File(fileView.getAbsolutePath()), fileView.getObjectToSave());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        removeTabAt(
                indexOfComponent(fileView)
        );

        int amount = 0;
        FileView<?> rename = null;

        for (int i = 0; i < getTabCount(); i++) {
            FileView<?> view = (FileView<?>) getComponentAt(i);

            if (view.getFile().getName().equals(fileView.getFile().getName())) {
                rename = view;
                amount++;
            }
        }

        if (amount == 1) {
            setTitleAt(indexOfComponent(rename), rename.getFile().getName());
        }

        System.gc();
    }

    @Override
    public String getTitleAt(int index) {
        FileView<?> view = (FileView<?>) getComponentAt(index);
        return view.getName();
    }

    @Override
    public void setTitleAt(int index, String title) {
        FileView<?> view = (FileView<?>) getComponentAt(index);
        view.setName(title);
        JPanel pnlTab = createTabComponent(title, view);
        this.setTabComponentAt(index, pnlTab);
    }

    public void save() throws Exception {
        if (getSelectedComponent() instanceof FileView<?> fileView) {
            Coordinator<?, ?> coordinator = coordinatorMap.get(fileView.getFileType());
            coordinator.save(new File(fileView.getAbsolutePath()), fileView.getObjectToSave());
        }
    }

    public void saveAll() throws Exception {
        for (Component component : getComponents()) {
            if (component instanceof FileView<?> fileView) {
                Coordinator<?, ?> coordinator = coordinatorMap.get(fileView.getFileType());
                coordinator.save(new File(fileView.getAbsolutePath()), fileView.getObjectToSave());
            }
        }
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

    private String getFileEnding(String file) {
        String[] split = file.split("\\.");
        return split[split.length-1];
    }
}
