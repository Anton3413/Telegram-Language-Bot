package anton3413.telegramlanguagebot;


import anton3413.telegramlanguagebot.service.imp.MessageService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TelegramBot extends TelegramLongPollingBot {

    private final String botName;

    public TelegramBot(String botToken, String botName) {
        super(botToken);
        this.botName = botName;
    }
    @Override
    public String getBotUsername() {
        return botName;
    }
    @Override
    public void onUpdateReceived(Update update) {

        MessageService.
        try {
            execute(new SendMessage());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }



}
