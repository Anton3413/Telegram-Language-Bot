package anton3413.telegramlanguagebot.handler;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
@Component
@PropertySource("classpath:bot-messages.properties")
public class CommandHandler {

    public SendMessage defineCommand(Message message){
      String command = message.getText();

        return switch (command) {
            case "/start" -> handleStartCommand(message);
            case "/help" ->
            default -> new SendMessage();
        };

    }

    private SendMessage handleStartCommand(Message message){
        SendMessage newMessage = constructMessage(message);
        newMessage.setText();
    }

    private SendMessage constructMessage(Message message){
        SendMessage blank = new SendMessage();
        blank.setChatId(message.getChatId());
        return blank;
    }

    private SendMessage handleAllCommandsCommand(Message message){
        SendMessage newMessage = constructMessage(message);
        newMessage.setText();
    }


}
