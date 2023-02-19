package net.gotzi.drawmachine.sim.gcode;

public enum Motor {
    A,
    B,
    M;

    public char toChar() {
        return this.name().charAt(0);
    }
}
