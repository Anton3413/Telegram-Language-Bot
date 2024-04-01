package anton3413.telegramlanguagebot.Model;

public enum BotCommand {

    HELP_COMMAND("/help"),
    START_COMMAND("/start"),
    REGISTER_COMMAND("/register"),
    ALL_COMMANDS("/commands");

    private final String command;

    BotCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
