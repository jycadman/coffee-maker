public enum CarafeState {
    C0("Carafe empty"),
    C25("Carafe 25%"),
    C50("Carafe 50%"),
    C75("Carafe 75%"),
    C100("Carafe Full");

    private final String level;

    CarafeState(String s) {
        this.level = s;
    }

    public String getLevel() {
        return this.level;
    }
}

