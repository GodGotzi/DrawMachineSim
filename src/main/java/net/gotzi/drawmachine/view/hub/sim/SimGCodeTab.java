package net.gotzi.drawmachine.view.hub.sim;

import net.gotzi.drawmachine.api.sim.SimProgramInfo;
import net.gotzi.drawmachine.control.UnderLayPanel;
import net.gotzi.drawmachine.sim.gcode.GCode;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;

public class SimGCodeTab extends JPanel {

    private final RSyntaxTextArea gCodeEditor;

    public SimGCodeTab(SimProgramInfo programInfo) {
        this.gCodeEditor = new RSyntaxTextArea(20, 60);

        setup(programInfo);
    }

    private void setup(SimProgramInfo programInfo) {
        gCodeEditor.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_ACTIONSCRIPT);
        gCodeEditor.setBackground(Color.WHITE);
        gCodeEditor.setSelectedTextColor(Color.GRAY);
        gCodeEditor.setSelectionColor(Color.GRAY);
        gCodeEditor.setCurrentLineHighlightColor(Color.LIGHT_GRAY);
        gCodeEditor.setCodeFoldingEnabled(true);

        String source = String.join(System.lineSeparator(), programInfo.gcode().getSource());
        gCodeEditor.setText(source);

        RTextScrollPane scrollPane = new RTextScrollPane(gCodeEditor);
        UnderLayPanel underLayPanel = new UnderLayPanel(scrollPane);
        underLayPanel.setNorthBorderThickness(5);
        underLayPanel.setWestBorderThickness(5);
        underLayPanel.setSouthBorderThickness(5);
        underLayPanel.setEastBorderThickness(5);

        this.setLayout(new BorderLayout());
        this.add(underLayPanel, BorderLayout.CENTER);
    }

    public GCode loadGCode(boolean filterCommands) {
        String text = gCodeEditor.getText();

        if (filterCommands)
            text = text.replaceAll("/\\*(?:[^*]|\\*+[^*/])*\\*+/|//.*","");

        String[] source = text.split("\\r?\\n");

        return new GCode(source);
    }

}
