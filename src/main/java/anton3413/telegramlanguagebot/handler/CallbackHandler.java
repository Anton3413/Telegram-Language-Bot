package anton3413.telegramlanguagebot.handler;

import anton3413.telegramlanguagebot.Model.TranslateLanguage;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CallbackHandler {

    public EditMessageText changeTranslateLanguage(Update update, String callBackData){
        EditMessageText editMessage = constructMessage(update);

        String pattern = "You have successfully changed the translation language!\n" +
                "Now your language - ";

        String languageFullName = TranslateLanguage.valueOf(callBackData).getFullName();

        editMessage.setText(pattern + languageFullName);
        return editMessage;
    }

    private EditMessageText constructMessage(Update update){
        EditMessageText message = new EditMessageText();

        message.setChatId(update.getCallbackQuery().getMessage().getChatId());
        message.setMessageId(Math.toIntExact(update.getCallbackQuery().getMessage().getMessageId()));

        return message;
    }
}
