package anton3413.telegramlanguagebot.handler;

import anton3413.telegramlanguagebot.Model.User;
import anton3413.telegramlanguagebot.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import reverso.language.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
@AllArgsConstructor
public class CallbackHandler {

    private final UserService userService;
    private final Properties properties;

    public EditMessageText changeSourceLanguage(Update update){
        EditMessageText editMessage = constructMessage(update);

        String prefix = "src_lang";
        Language sourceLanguage =  Language.valueOf(update.getCallbackQuery().getData().substring(prefix.length()).toUpperCase());

        User user = userService.getUserByChatId(update.getCallbackQuery().getMessage().getChatId());
        user.setSourceLanguage(sourceLanguage);
        userService.updateUser(user);

        setTargetLanguageButtons(editMessage,sourceLanguage);
        editMessage.setText(properties.getProperty("bot_choose_targetLanguage"));
        return editMessage;
    }

    public EditMessageText changeTargetLanguage(Update update){
        EditMessageText editMessage = constructMessage(update);

        String prefix = "trg_lang";
        User user = userService.getUserByChatId(update.getCallbackQuery().getMessage().getChatId());

        Language targetLanguage =  Language.valueOf(update.getCallbackQuery().getData().substring(prefix.length()).toUpperCase());

        user.setTargetLanguage(targetLanguage);
        userService.updateUser(user);

        editMessage.setText(String.format(properties.getProperty("bot_translation_ready"),
                user.getSourceLanguage().name(),
                user.getTargetLanguage().name()));

        editMessage.setParseMode(ParseMode.HTML);
        return editMessage;
    }

    private void setTargetLanguageButtons(EditMessageText editMessage, Language sourceLanguage) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> line = new ArrayList<>();

        for (Language language : Language.values()) {
            if (language.equals(sourceLanguage)) {
                continue;
            }
            InlineKeyboardButton button = new InlineKeyboardButton(language.toString());
            button.setCallbackData("trg_lang" + language.name());
            line.add(button);

            if (line.size() == 3) {
                rows.add(line);
                line = new ArrayList<>();
            }
        }
        if (!line.isEmpty()) {
            rows.add(line);
        }

        keyboardMarkup.setKeyboard(rows);

        editMessage.setReplyMarkup(keyboardMarkup);
        editMessage.setText(properties.getProperty("bot_choose_targetLanguage"));
    }

    private EditMessageText constructMessage(Update update){
        EditMessageText message = new EditMessageText();

        message.setChatId(update.getCallbackQuery().getMessage().getChatId());
        message.setMessageId(Math.toIntExact(update.getCallbackQuery().getMessage().getMessageId()));

        return message;
    }
}
