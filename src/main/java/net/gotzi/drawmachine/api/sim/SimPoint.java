package net.gotzi.drawmachine.api.sim;

import java.util.Objects;

public record SimPoint(double x, double y) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimPoint simPoint = (SimPoint) o;
        return Double.compare(simPoint.x, x) == 0 && Double.compare(simPoint.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "SimPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
