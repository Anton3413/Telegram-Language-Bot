package anton3413.telegramlanguagebot.handler;

import anton3413.telegramlanguagebot.Model.User;
import anton3413.telegramlanguagebot.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
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

        Language language =  Language.valueOf(update.getCallbackQuery().getData().substring(prefix.length()).toUpperCase());

        User user = userService.getUserByChatId(update.getCallbackQuery().getMessage().getChatId());
        user.setSourceLanguage(language);
        userService.updateUser(user);

        setTargetLanguageButtons(editMessage);
        editMessage.setText(properties.getProperty("bot_choose_targetLanguage"));
        return editMessage;
    }

    public EditMessageText changeTargetLanguage(Update update){
        EditMessageText editMessage = constructMessage(update);

        String prefix = "trg_lang";
        User user = userService.getUserByChatId(update.getCallbackQuery().getMessage().getChatId());

        Language language =  Language.valueOf(update.getCallbackQuery().getData().substring(prefix.length()).toUpperCase());

        user.setTargetLanguage(language);
        userService.updateUser(user);

        editMessage.setText("🎉 Well done! 🌟\n" +
                "📚 **Source Language**: " + user.getSourceLanguage().name() + "\n" +
                "🎯 **Target Language**: " + user.getTargetLanguage().name() + "\n" +
                "Let's start translating! 🌍");

        return editMessage;
    }

    private void setTargetLanguageButtons(EditMessageText editMessage) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> line = new ArrayList<>();

        for (Language language : Language.values()) {
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
        editMessage.setText(properties.getProperty("bot_choose_sourceLanguage"));
    }

    private EditMessageText constructMessage(Update update){
        EditMessageText message = new EditMessageText();

        message.setChatId(update.getCallbackQuery().getMessage().getChatId());
        message.setMessageId(Math.toIntExact(update.getCallbackQuery().getMessage().getMessageId()));

        return message;
    }
}
