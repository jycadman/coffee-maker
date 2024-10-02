public enum PowerBlockState {
    UNPLUGGED(false),
    PLUGGED(true);

    private final boolean state;

    PowerBlockState(boolean b) {
        this.state = b;
    }

    public boolean getState() {
        return state;
    }
}
