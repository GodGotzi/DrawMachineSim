package at.gotzi.drawmachine.api;

public abstract class Process<T> {
    private boolean successful = false;

    public abstract T getResult();

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
