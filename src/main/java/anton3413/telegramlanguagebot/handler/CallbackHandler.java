package anton3413.telegramlanguagebot.handler;

import anton3413.telegramlanguagebot.Model.TranslateLanguage;
import anton3413.telegramlanguagebot.Model.User;
import anton3413.telegramlanguagebot.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class CallbackHandler {

    private final UserService userService;

    public EditMessageText changeTranslateLanguage(Update update){
        EditMessageText editMessage = constructMessage(update);

        String pattern = "You have successfully changed the translation language!\n" +
                "Now your language - ";

        TranslateLanguage callBackData = TranslateLanguage.valueOf(update.getCallbackQuery().getData());

        User user = userService.getUserByChatId(update.getCallbackQuery().getMessage().getChatId());
        user.setTranslateLanguage(callBackData);
        userService.saveUser(user);

        editMessage.setText(pattern + callBackData.getFullName());
        return editMessage;
    }

    private EditMessageText constructMessage(Update update){
        EditMessageText message = new EditMessageText();

        message.setChatId(update.getCallbackQuery().getMessage().getChatId());
        message.setMessageId(Math.toIntExact(update.getCallbackQuery().getMessage().getMessageId()));

        return message;
    }
}
