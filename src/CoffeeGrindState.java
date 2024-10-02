public enum CoffeeGrindState {
    PRESENT(true),
    MISSING(false);

    private final boolean state;

    CoffeeGrindState(boolean b) {
        this.state = b;
    }

    public boolean getState() {
        return state;

    }
}
