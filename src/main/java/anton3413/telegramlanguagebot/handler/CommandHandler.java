package anton3413.telegramlanguagebot.handler;


import anton3413.telegramlanguagebot.Model.User;
import anton3413.telegramlanguagebot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
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
            message.setText(properties.getProperty("bot_start_command_message"));
            return message;
        }

        message.setText("Hello" + userService.getUserByChatId(chatId).getUserName());
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
        user.setCurrentCommand(newMode);

        message.setText("You have successfully changed the mode! Now the bot works in the mode " + newMode );
        userService.updateUser(user);
        return message;
    }

    public SendMessage setSourceLanguageButtons(SendMessage sendMessage) {
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
        sendMessage.setText("Choose source language");
        return sendMessage;
    }

    private SendMessage constructMessage(Update update) {
        SendMessage blank = new SendMessage();
        blank.setChatId(update.getMessage().getChatId());
        return blank;
    }
}

