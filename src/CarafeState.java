public enum CarafeState {
    C0("C00"),
    C25("C25"),
    C50("C50%"),
    C75("C75"),
    C100("CI1");

    private final String level;

    CarafeState(String s) {
        this.level = s;
    }

    public String getLevel() {
        return this.level;
    }
}

