package anton3413.telegramlanguagebot.Model;

public enum Command {

    HELP_COMMAND("/help"),
    START_COMMAND("/start"),
    REGISTER_COMMAND("/register"),
    ALL_COMMANDS("/commands");

    private final String name;

    Command(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
