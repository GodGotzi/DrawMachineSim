package net.gotzi.drawmachine.sim.monitor;

import net.gotzi.drawmachine.DrawMachineSim;
import net.gotzi.drawmachine.api.sim.SimRenderState;
import net.gotzi.drawmachine.handler.MouseCursorHandler;
import net.gotzi.drawmachine.error.UnsupportedValue;
import net.gotzi.drawmachine.sim.Simulation;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class SimMonitorView implements SimMonitor {

    private final Simulation simulation;
    private final String maxAllowedStepsStr =
            DrawMachineSim.getInstance().getConfig().get("max_simulation_steps");

    private JPanel view;
    private JSlider simSpeedSlider;
    private JLabel simSpeedValueLabel;
    private JProgressBar progressBar;
    private JButton stopButton;
    private JButton runButton;
    private JLabel speedLabel;
    private JLabel stepLabel;
    private JSpinner simAccuracySpinner;
    private JLabel stepProgress;
    private JButton resetCanvasButton;
    private JButton resetViewButton;
    private JCheckBox fastMode;
    private final AtomicInteger atomicSimSpeed;
    private final AtomicInteger atomicAccuracyFactor;

    public SimMonitorView(Simulation simulation) {
        this.simulation = simulation;
        this.atomicSimSpeed = new AtomicInteger();
        this.atomicAccuracyFactor = new AtomicInteger();

        runButton.setText("Run");
        stopButton.setText("Stop");
        resetViewButton.setText("Reset View");
        resetCanvasButton.setText("Reset Canvas");
        speedLabel.setText("Simulation Speed");
        stepLabel.setText("Simulation Accuracy Factor [1% - 100%] ");
        stepProgress.setText(String.format("%0" + maxAllowedStepsStr.length() + "d/%" +
                maxAllowedStepsStr.length() + "d", 0, 10000));
        simSpeedValueLabel.setText(String.format("%.2f x", 10 * Math.pow(10, -1)));

        simSpeedValueLabel.setHorizontalAlignment(JLabel.CENTER);
        simSpeedValueLabel.setVerticalAlignment(JLabel.CENTER);

        stepProgress.setHorizontalAlignment(JLabel.CENTER);
        stepProgress.setVerticalAlignment(JLabel.CENTER);

        simSpeedSlider.setMinimum(10);
        simSpeedSlider.setValue(10);
        simSpeedSlider.setMaximum(1000);
        atomicSimSpeed.set(10);

        simAccuracySpinner.setValue(100);
        atomicAccuracyFactor.set(100);

        progressBar.setMinimum(0);
        progressBar.setValue(0);
        progressBar.setMaximum(100);


        addListeners();
    }

    private void addListeners() {
        simSpeedSlider.addChangeListener(this::updateSimSpeed);
        simSpeedSlider.addMouseListener(new MouseCursorHandler(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR)));

        runButton.addActionListener(this::run);
        runButton.addMouseListener(new MouseCursorHandler(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)));

        stopButton.addActionListener(this::stop);
        stopButton.addMouseListener(new MouseCursorHandler(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)));

        resetViewButton.addActionListener(this::resetView);
        resetViewButton.addMouseListener(new MouseCursorHandler(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)));

        resetCanvasButton.addActionListener(this::resetCanvas);
        resetCanvasButton.addMouseListener(new MouseCursorHandler(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)));

        fastMode.addMouseListener(new MouseCursorHandler(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)));

        simAccuracySpinner.addChangeListener(this::updateSimSteps);
    }

    @Override
    public void updateProgress(int progress) {
        this.progressBar.setValue(progress);
    }

    @Override
    public void updateState(SimRenderState state) {
        int progress = (int) ((float)state.timestamp()/(float) state.time() * 100);
        stepProgress.setText(String.format("%d/%d", state.timestamp(), state.time()));
        updateProgress(progress);
    }

    @Override
    public AtomicInteger getSimulationSpeed() {
        return atomicSimSpeed;
    }

    @Override
    public AtomicInteger getSimulationSteps() {
        return atomicAccuracyFactor;
    }

    @Override
    public boolean isFastMode() {
        return fastMode.isSelected();
    }

    private void updateSimSteps(ChangeEvent ignored) {
        NumberFormat nf = DecimalFormat.getInstance(new Locale("en", "US"));
        int value;

        try {
            value = Integer.parseInt(simAccuracySpinner.getValue().toString());
            if (1000000 < value) {
                simAccuracySpinner.setValue(100);
                new UnsupportedValue(DrawMachineSim.getInstance().getWindow(), "Value is too high Max: " + nf.format(100));
                return;
            } else if (value < 1) {
                simAccuracySpinner.setValue(1);
                new UnsupportedValue(DrawMachineSim.getInstance().getWindow(), "Value is too high Max: " + nf.format(1));
                return;
            }
        } catch (Exception e) {
            return;
        }

        stepProgress.setText(String.format("%0" + maxAllowedStepsStr.length() + "d/%" +
                maxAllowedStepsStr.length() + "d", simulation.getTimestamp(), value));
        atomicAccuracyFactor.set(value);
    }

    private void updateSimSpeed(ChangeEvent changeEvent) {
        atomicSimSpeed.set(this.simSpeedSlider.getValue());
        if (this.simSpeedSlider.getValue() > 999)
            simSpeedValueLabel.setText("∞ x");
        else
            simSpeedValueLabel.setText(String.format("%.2f ", this.simSpeedSlider.getValue() * Math.pow(10, -1)) + "x");
    }

    private void run(ActionEvent actionEvent) {
        if (this.simulation.isRunning()) return;
        this.simulation.run();
    }

    private void stop(ActionEvent actionEvent) {
        if (!this.simulation.isRunning()) return;
        this.simulation.stop();
    }

    private void resetView(ActionEvent actionEvent) {
        this.simulation.resetView();
    }

    private void resetCanvas(ActionEvent actionEvent) {
        if (this.simulation.isRunning()) return;
        this.simulation.resetCanvas();
    }

    public JPanel getView() {
        return view;
    }
}