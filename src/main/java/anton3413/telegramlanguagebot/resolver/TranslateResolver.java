package anton3413.telegramlanguagebot.resolver;

import anton3413.telegramlanguagebot.service.UserService;
import anton3413.telegramlanguagebot.service.imp.TranslateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class TranslateResolver {
    private final TranslateService translateService;
    private final UserService userService;


    public SendMessage translateText(Update update){
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId());

        String targetLanguage = userService.getUserByChatId(update
                        .getMessage().getChatId())
                .getTranslateLanguage().getShortName();

        message.setText(translateService.translateText(update.getMessage().getText(),targetLanguage));

        return message;
    }

}
