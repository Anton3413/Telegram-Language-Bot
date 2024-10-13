package anton3413.telegramlanguagebot.handler;


import anton3413.telegramlanguagebot.Model.User;
import anton3413.telegramlanguagebot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import reverso.language.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class CommandHandler {

    private final UserService userService;

    private final Properties properties;

    public SendMessage startCommand(Update update) {

        SendMessage message = constructMessage(update);
        Long chatId = update.getMessage().getChatId();

        if (!userService.isUserAlreadyRegistered(chatId)) {
            userService.registerUser(update);
            message.setText(String.format(properties.getProperty("bot_start_command_message"),
                    userService.getUserByChatId(chatId).getFirstName()));
            return message;
        }
        message.setText(String.format(properties.getProperty("bot_start_familiar_user"),
                userService.getUserByChatId(chatId).getFirstName()));
        return message;
    }

    public SendMessage languageCommand(Update update) {
        SendMessage message = constructMessage(update);
        setSourceLanguageButtons(message);
        return message;
    }

    public SendMessage changeModeCommand(Update update) {
        SendMessage message = constructMessage(update);

        User user = userService.getUserByChatId(update.getMessage().getChatId());


        String newMode = update.getMessage().getText();

        if(user.getCurrentCommand().equals(newMode)){
            message.setText(String.format(properties.getProperty("bot_mode_already_set"),newMode));
            message.setParseMode(ParseMode.HTML);
            return message;
        }else {
            user.setCurrentCommand(newMode);
            message.setText(String.format(properties.getProperty("bot_mode_change_success"),newMode));
            message.setParseMode(ParseMode.HTML);
            userService.updateUser(user);
            return message;
        }
    }
    public SendMessage unsupportedCommand(Update update) {
        SendMessage message = constructMessage(update);
        message.setText(properties.getProperty("bot_unsupported_command_message"));
        return message;
    }

    public void setSourceLanguageButtons(SendMessage sendMessage) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> line = new ArrayList<>();

        for (Language language : Language.values()) {
            InlineKeyboardButton button = new InlineKeyboardButton(language.toString());
            button.setCallbackData("src_lang" +language.name());
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

        sendMessage.setReplyMarkup(keyboardMarkup);
        sendMessage.setText(properties.getProperty("bot_choose_sourceLanguage"));
    }

    private SendMessage constructMessage(Update update) {
        SendMessage blank = new SendMessage();
        blank.setChatId(update.getMessage().getChatId());
        return blank;
    }
}

