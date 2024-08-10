package anton3413.telegramlanguagebot.service.imp;


import anton3413.telegramlanguagebot.Model.User;
import anton3413.telegramlanguagebot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Properties;

@Service
@RequiredArgsConstructor
public class TranslateService {

    private final Properties properties;
    private final UserService userService;


    public SendMessage handleMessage(Update update) {

        SendMessage sendMessage = constructMessage(update);

        if (!userService.isUserReadyToUseBot(update.getMessage().getChatId())) {
            sendMessage.setText("You are not ready to use bot");
            return sendMessage;
        }
        else
    }

    private SendMessage constructMessage(Update update) {
        SendMessage blank = new SendMessage();
        blank.setChatId(update.getMessage().getChatId());
        return blank;
    }
}
