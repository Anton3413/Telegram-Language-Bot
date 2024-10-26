package anton3413.telegramlanguagebot.handler;

import anton3413.telegramlanguagebot.Model.User;
import anton3413.telegramlanguagebot.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import reverso.language.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
@AllArgsConstructor
public class InteractionHandler {

    private final UserService userService;
    private final Properties properties;

    public EditMessageText identifyCallback(Update update) {

        String callBackData = update.getCallbackQuery().getData();

        if(callBackData.startsWith("src_lang")){
            return changeSourceLanguage(update);
        }
        else if(callBackData.startsWith("trg_lang")){
            return changeTargetLanguage(update);
        }
        return null;
    }

    public SendMessage handleUnsupportedMessage(Update update){
        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(properties.getProperty("bot_unsupportedOperation_message"))
                .build();
    }

    public SendMessage botReadyToStartMessage(Update update){
        return SendMessage.builder()
                .chatId(update.getMessage().getChatId())
                .text(properties.getProperty("bot_ready_to_use"))
                .build();
    }

    private EditMessageText changeSourceLanguage(Update update){
        EditMessageText editMessage = constructEditMessage(update);

        String prefix = "src_lang";
        Language sourceLanguage =  Language.valueOf(update.getCallbackQuery().getData().substring(prefix.length()).toUpperCase());

        User user = userService.getUserByChatId(update.getCallbackQuery().getMessage().getChatId());
        user.setSourceLanguage(sourceLanguage);
        userService.updateUser(user);

        editMessage.setReplyMarkup(setTargetLanguageButtons(sourceLanguage));
        editMessage.setText(properties.getProperty("bot_choose_targetLanguage"));
        return editMessage;
    }

    private EditMessageText changeTargetLanguage(Update update){
        EditMessageText editMessage = constructEditMessage(update);

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

    private InlineKeyboardMarkup setTargetLanguageButtons(Language sourceLanguage) {
        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup.builder().build();
        InlineKeyboardRow row = new InlineKeyboardRow();
        List<InlineKeyboardRow> rows = new ArrayList<>();

        for (Language language : Language.values()) {
            if (language.equals(sourceLanguage)) {
                continue;
            }
            InlineKeyboardButton button = new InlineKeyboardButton(language.toString());
            button.setCallbackData("trg_lang" + language.name());
            row.add(button);

            if (row.size() == 3) {
                rows.add(row);
                row = new InlineKeyboardRow();
            }
        }
        if (!row.isEmpty()) {
            rows.add(row);
        }

        keyboardMarkup.setKeyboard(rows);
        return keyboardMarkup;
    }
    private EditMessageText constructEditMessage(Update update){
        return EditMessageText.builder()
                .chatId(update.getCallbackQuery().getMessage().getChatId())
                .text("")
                .messageId(Math.toIntExact(update.getCallbackQuery().getMessage().getMessageId()))
                .build();
    }
}
