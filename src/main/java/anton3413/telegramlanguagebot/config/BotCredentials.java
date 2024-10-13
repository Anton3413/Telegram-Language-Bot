package anton3413.telegramlanguagebot.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class BotCredentials {

    private final String BOT_NAME = System.getenv("BOT_NAME");

    private final String BOT_TOKEN = System.getenv("BOT_TOKEN");

    private final String BOT_PROPERTIES = "/properties/bot_messages.properties";

}
