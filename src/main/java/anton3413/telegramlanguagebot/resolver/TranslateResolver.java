package anton3413.telegramlanguagebot.resolver;

import anton3413.telegramlanguagebot.service.imp.TranslateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class TranslateResolver {
    private final TranslateService translateService;


    public SendMessage translateText(Update update){
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());
        message.setText(translateService.translateText(update.getMessage().getText()));

        return message;
    }

}
