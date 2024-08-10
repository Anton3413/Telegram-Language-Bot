package anton3413.telegramlanguagebot;

import anton3413.telegramlanguagebot.service.imp.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private UpdateService updateService;
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
        Object result = updateService.handleUpdate(update);
        try {
            if(result instanceof SendMessage){
                execute((SendMessage)result);
            }
            else {
                execute((EditMessageText)result);
            }

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

}
