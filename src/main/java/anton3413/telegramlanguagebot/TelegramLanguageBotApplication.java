package anton3413.telegramlanguagebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class TelegramLanguageBotApplication {

    public static void main(String[] args) throws TelegramApiException {
        SpringApplication.run(TelegramLanguageBotApplication.class, args);

    }
}
