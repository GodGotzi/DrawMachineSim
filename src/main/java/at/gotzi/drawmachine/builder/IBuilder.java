package at.gotzi.drawmachine.builder;

public abstract class IBuilder<T> {

    private boolean successful = false;

    public abstract T getResult();

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
