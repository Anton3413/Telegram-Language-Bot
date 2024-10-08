package anton3413.telegramlanguagebot.resolver;

import anton3413.telegramlanguagebot.handler.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class CommandResolver {

    final private CommandHandler commandHandler;

    public SendMessage defineCommand(Update update){
      String command = update.getMessage().getText();

        return switch (command) {
            case "/start" -> commandHandler.startCommand(update);
            case "/language","/languages","/lang" -> commandHandler.languageCommand(update);
            case "/translate", "/conjugation", "/synonyms" -> commandHandler.changeModeCommand(update);
            default -> commandHandler.unsupportedCommand(update);
        };
    }

}
