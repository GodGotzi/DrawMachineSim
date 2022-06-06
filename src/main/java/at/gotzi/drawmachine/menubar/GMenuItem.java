package at.gotzi.drawmachine.menubar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

public abstract class GMenuItem extends JMenuItem {

    private final List<AbstractAction> actionEvents;
    private boolean active;

    public GMenuItem(String text, boolean activeNative) {
        this.active = activeNative;
        this.actionEvents = new LinkedList<>();

        setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performAction(e);
                actionEvents.forEach(action -> action.actionPerformed(e));
            }
        });

        actionEvents.add(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active = !active;
            }
        });

        setText(text);
    }

    public GMenuItem addActionEvent(AbstractAction abstractAction) {
        actionEvents.add(abstractAction);

        return this;
    }

    public boolean isActivated() {
        return active;
    }

    public abstract void performAction(ActionEvent actionEvent);
}
