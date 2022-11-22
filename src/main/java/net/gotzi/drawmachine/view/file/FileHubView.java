package net.gotzi.drawmachine.view.file;

import net.gotzi.drawmachine.handler.design.DesignColor;
import net.gotzi.drawmachine.handler.design.DesignHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class FileHubView extends JTabbedPane implements FileHub {

    private final DesignHandler designHandler;

    public FileHubView(DesignHandler designHandler) {
        this.designHandler = designHandler;
    }

    public void openFilePage(FileView fileView) {
        addTab(fileView.getName(), fileView);
    }

    @Override
    public void addTab(String title, Component component) {
        super.addTab(title, component);

        int index = indexOfTab(title);
        JPanel pnlTab = new JPanel(new GridBagLayout());
        pnlTab.setOpaque(false);
        JLabel lblTitle = new JLabel(title);

        ExitButton exitButton = new ExitButton(e -> {
            removeTabAt(indexOfTab(title));
            System.gc();
        });

        exitButton.setPreferredSize(new Dimension(15, 15));

        JPanel exitPanel = new JPanel(new GridBagLayout());
        exitPanel.setOpaque(false);
        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.gridx = 30;
        gbcButton.gridwidth = 10;
        exitPanel.add(exitButton, gbcButton);
        exitPanel.setPreferredSize(new Dimension(30, 19));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;

        pnlTab.add(lblTitle, gbc);

        gbc.gridx++;
        gbc.weightx = 20;
        pnlTab.add(exitPanel, gbc);

        this.setTabComponentAt(index, pnlTab);

        /*
        this.designHandler.getDesignColorChanges(DesignColor.SECONDARY)
                .registerPossibleChange(this::setBackground);

        this.designHandler.getDesignColorChanges(DesignColor.SECONDARY)
                .registerPossibleChange(pnlTab::setBackground);
        */
    }


    @Override
    public synchronized void addMouseListener(MouseListener l) {
        super.addMouseListener(l);
    }
}
