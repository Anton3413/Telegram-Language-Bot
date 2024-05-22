package anton3413.telegramlanguagebot.handler;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CommandHandler {


    public SendMessage defineCommand(Update update){
      String command = update.getMessage().getText();

        return switch (command) {
            case "/start" -> startCommand(update);
            case "/help" -> helpCommand(update);
            default -> new SendMessage();

        };
    }

    private SendMessage startCommand(Update update){
        SendMessage newMessage = constructMessage(message);
        newMessage.setText();
    }

    private SendMessage helpCommand(Update update){
        SendMessage newMessage = constructMessage(message);
        newMessage.setText();
    }

    private SendMessage constructMessage(Update update){
        SendMessage blank = new SendMessage();
        blank.setChatId(message.getChatId());
        return blank;
    }

}
