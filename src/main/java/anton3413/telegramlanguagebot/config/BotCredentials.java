package anton3413.telegramlanguagebot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class BotCredentials {

    private final String BOT_TOKEN = System.getProperty("BOT_TOKEN");

    private final String BOT_NAME = System.getProperty("BOT_NAME");
}
