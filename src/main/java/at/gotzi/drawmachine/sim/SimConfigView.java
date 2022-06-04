package at.gotzi.drawmachine.sim;

import at.gotzi.drawmachine.DrawMachineCA;
import at.gotzi.drawmachine.error.UnsupportedValue;
import at.gotzi.drawmachine.sim.SimMonitor;
import at.gotzi.drawmachine.sim.Simulation;
import at.gotzi.drawmachine.utils.Helper;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class SimConfigView implements SimMonitor {

    private Simulation simulation;

    private JPanel view;
    private JSlider simSpeedSlider;
    private JLabel simSpeedValueLabel;
    private JProgressBar progressBar;
    private JButton runButton;
    private JLabel speedLabel;
    private JLabel stepLabel;
    private JSpinner simStepSpinner;

    private final AtomicInteger atomicSimSpeed;
    private final AtomicInteger atomicSimSteps;

    public SimConfigView(Simulation simulation) {
        this.simulation = simulation;
        this.atomicSimSpeed = new AtomicInteger();
        this.atomicSimSteps = new AtomicInteger();

        runButton.setText("Run");
        speedLabel.setText("Simulation Speed");
        stepLabel.setText("Simulation Steps");

        simSpeedSlider.setMinimum(10);
        simSpeedSlider.setValue(10);
        simSpeedSlider.setMaximum(1000);
        atomicSimSpeed.set(10);

        simStepSpinner.setValue(10000);
        atomicSimSteps.set(10000);

        progressBar.setMinimum(0);
        progressBar.setValue(50);
        progressBar.setMaximum(100);

        simSpeedValueLabel.setText(Helper.swingFormat(8, 1.0f, 1) + "x");
        addListeners();
    }

    private void addListeners() {
        simSpeedSlider.addChangeListener(this::updateSimSpeed);

        runButton.addActionListener(this::run);

        simStepSpinner.addChangeListener(cl -> {
            try {
                updateSimSteps(cl);
            } catch (UnsupportedValue e) {
                e.showErrorInfo(DrawMachineCA.getInstance().getWindow().getFrame());
            }
        });
    }

    private void updateSimSteps(ChangeEvent ignored) throws UnsupportedValue {
        NumberFormat nf = DecimalFormat.getInstance(new Locale("en", "US"));
        int maxAllowed = Integer.parseInt(DrawMachineCA.getInstance().getConfig().get("max_simulation_steps"));
        int value;

        value = Integer.parseInt(simStepSpinner.getValue().toString());
        if (maxAllowed < value) throw new UnsupportedValue("Value is too high Max: " + nf.format(maxAllowed));

        atomicSimSteps.set(value);
    }

    private void updateSimSpeed(ChangeEvent changeEvent) {
        String formattedValue = Helper.swingFormat(8, this.simSpeedSlider.getValue() * Math.pow(10, -1), 1) + "x";

        atomicSimSpeed.set(this.simSpeedSlider.getValue());
        simSpeedValueLabel.setText(formattedValue);
    }

    private synchronized JProgressBar getProgressBar() {
        return this.progressBar;
    }

    private void run(ActionEvent actionEvent) {
        if (this.simulation.isRunning()) return;
        this.simulation.run(this);

        Thread thread = new Thread(() -> {
            while (true) {
                for (int i = 0; i < atomicSimSteps.get(); i++) {
                    getProgressBar().setValue((int) ((float)i/(float) atomicSimSteps.get() * 100));

                    try {
                        Thread.sleep(100/simSpeedSlider.getValue());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (int i = atomicSimSteps.get(); i > 0; i--) {
                    getProgressBar().setValue((int) ((float)i/(float) atomicSimSteps.get() * 100));
                    try {
                        Thread.sleep(100/simSpeedSlider.getValue());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }

    public JPanel getView() {
        return view;
    }

    @Override
    public AtomicInteger getSimulationSpeed() {
        return atomicSimSpeed;
    }

    @Override
    public AtomicInteger getSimulationSteps() {
        return atomicSimSteps;
    }

    @Override
    public void updateProgress(int progress) {
        this.progressBar.setValue(progress);
    }
}
