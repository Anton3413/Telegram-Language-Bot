package anton3413.telegramlanguagebot.config;

import anton3413.telegramlanguagebot.Model.BotCommand;
import anton3413.telegramlanguagebot.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotInitializer {

    @Autowired
    BotCredentials botCredentials;
    @EventListener(ContextRefreshedEvent.class)
    public void init(){
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(new TelegramBot(botCredentials.getBOT_TOKEN(), botCredentials.getBOT_NAME()));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
