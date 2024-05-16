package anton3413.telegramlanguagebot;

import anton3413.telegramlanguagebot.service.imp.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


public class TelegramBot extends TelegramLongPollingBot {

    private UpdateService messageService;
    private final String botName;

    public TelegramBot(String botToken, String botName) {
        super(botToken);
        this.botName = botName;
    }
    @Override
    public String getBotUsername() {
        return this.botName;
    }
    @Override
    public void onUpdateReceived(Update update) {

    }

    @Autowired
    public void setSomeOtherService(UpdateService messageService) {
        this.messageService = messageService;
    }

}
