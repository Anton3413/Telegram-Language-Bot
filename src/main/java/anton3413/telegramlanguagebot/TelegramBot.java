package anton3413.telegramlanguagebot;

import anton3413.telegramlanguagebot.handler.CommandHandler;
import anton3413.telegramlanguagebot.service.imp.UpdateService;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private ApplicationContext context;
    private UpdateService updateService;
    private final String botName;

    @Autowired
    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }
    public TelegramBot(String botToken, String botName) {
        super(botToken);
        this.botName = botName;
        setBotCommands();
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
                if (((SendMessage)result).getText().startsWith("Hello")) {
                    execute(context.getBean(CommandHandler.class).languageCommand(update));
                }
            }
            else {
                execute((EditMessageText)result);
            }

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    private void setBotCommands(){
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand("/language", "Select the required languages"));
        commands.add(new BotCommand("/translate", "Translation mode"));
        commands.add(new BotCommand("/synonyms", "Synonyms mode"));
        commands.add(new BotCommand("/conjugation", "Conjugation mode"));
        commands.add(new BotCommand("/help", "Full information about this bot"));
        commands.add(new BotCommand("/info", "Additional information about this bot"));

        SetMyCommands setMyCommands = new SetMyCommands();
        setMyCommands.setCommands(commands);
        try {
            execute(setMyCommands);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
