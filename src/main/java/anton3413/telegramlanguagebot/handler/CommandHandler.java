package anton3413.telegramlanguagebot.handler;


import anton3413.telegramlanguagebot.Model.User;
import anton3413.telegramlanguagebot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.LinkPreviewOptions;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import reverso.language.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
@RequiredArgsConstructor
public class CommandHandler {

    private final UserService userService;

    private final Properties properties;

    public SendMessage identifyCommand(Update update){
        String command = update.getMessage().getText();

        return switch (command) {
            case "/start" -> startCommand(update);
            case "/language","/languages","/lang" -> languageCommand(update);
            case "/translate", "/conjugation", "/synonyms" -> changeModeCommand(update);
            case "/help" -> helpCommand(update);
            case "/info" -> infoCommand(update);
            default -> unsupportedCommand(update);
        };
    }

    private SendMessage startCommand(Update update) {

        SendMessage newMessage = constructMessage(update);
        Long chatId = update.getMessage().getChatId();

        if (userService.isUserAlreadyRegistered(chatId)) {
            userService.registerUser(update);
            newMessage.setText(String.format(properties.getProperty("bot_command_start_newUser_message"),
                    userService.getUserByChatId(chatId).getFirstName()));
            return newMessage;
        }
        newMessage.setText((String.format(properties.getProperty("bot_command_start_familiar_user"),
                userService.getUserByChatId(chatId).getFirstName())));
        return newMessage;
    }

    public SendMessage languageCommand(Update update) {
        SendMessage newMessage = constructMessage(update);
        newMessage.setText(properties.getProperty("bot_choose_sourceLanguage"));
        newMessage.setReplyMarkup(getSourceLanguageMarkup());
        return newMessage;
    }

    private SendMessage changeModeCommand(Update update) {
        SendMessage message = constructMessage(update);

        User user = userService.getUserByChatId(update.getMessage().getChatId());

        String newMode = update.getMessage().getText();

        if(user.getCurrentCommand().equals(newMode)){
            message.setText(String.format(properties.getProperty("bot_mode_already_set"),newMode));
        } else {
            user.setCurrentCommand(newMode);
            message.setText(String.format(properties.getProperty("bot_mode_change_success"),newMode));
            userService.updateUser(user);
        }
        return message;
    }

    private SendMessage helpCommand(Update update) {
        SendMessage message = constructMessage(update);
        message.setText(properties.getProperty("bot_command_help"));
        return message;
    }

    private SendMessage infoCommand(Update update) {
        SendMessage message = constructMessage(update);
        message.setText(properties.getProperty("bot_command_info"));
        LinkPreviewOptions lpo = new LinkPreviewOptions();
        lpo.setIsDisabled(true);
        message.setLinkPreviewOptions(lpo);
        return message;
    }

    private SendMessage unsupportedCommand(Update update) {
        SendMessage message = constructMessage(update);
        message.setText(properties.getProperty("bot_unsupported_command_message"));
        return message;
    }

    private InlineKeyboardMarkup getSourceLanguageMarkup() {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        InlineKeyboardRow row = new InlineKeyboardRow();

        for (Language language : Language.values()) {
            InlineKeyboardButton button = new InlineKeyboardButton(language.toString());
            button.setCallbackData("src_lang" +language.name());
            row.add(button);

            if (row.size() == 3) {
                rows.add(row);
                row = new InlineKeyboardRow();
            }
        }
        if (!row.isEmpty()) {
            rows.add(row);
        }
        return InlineKeyboardMarkup.builder().keyboard(rows).build();
    }

    private SendMessage constructMessage(Update update) {
        return SendMessage.builder().
                chatId(update.getMessage().getChatId())
                .text("")
                .parseMode(ParseMode.HTML)
                .build();
    }
}