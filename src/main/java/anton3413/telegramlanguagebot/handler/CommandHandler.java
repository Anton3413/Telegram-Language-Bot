package anton3413.telegramlanguagebot.handler;

import anton3413.telegramlanguagebot.Model.TranslateLanguage;
import anton3413.telegramlanguagebot.Model.User;
import anton3413.telegramlanguagebot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommandHandler {
    private final UserService userService;

    /*private SendMessage startCommand(Update update){
        SendMessage newMessage = constructMessage(update);
        newMessage.setText();
    }

    private SendMessage helpCommand(Update update){
        SendMessage newMessage = constructMessage(update);
        newMessage.setText();
    }*/

    public SendMessage startCommand(Update update){
        SendMessage message = constructMessage(update);

        Long chatId = update.getMessage().getChatId();
        Chat chat = update.getMessage().getChat();

        if(!userService.isUserAlreadyRegistered(chatId)) {
            User user = new User();
            user.setChatId(chatId);
            user.setUserName(chat.getUserName());
            user.setFirstName(chat.getFirstName());
            user.setLastName(user.getLastName());
            user.setRegisteredAt(LocalDateTime.now());

            userService.registerNewUser(user);

            message.setText("You have successfully registered!");
        } else message.setText("You don't need to do this since you are already registered");
        return message;
    }

    public SendMessage changeLanguageCommand(Update update) {
        SendMessage newMessage = constructMessage(update);
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> line = new ArrayList<>();

        for (TranslateLanguage language : TranslateLanguage.values()) {
            InlineKeyboardButton button = new InlineKeyboardButton(language.getFullName());
            button.setCallbackData(language.toString());
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

        newMessage.setReplyMarkup(keyboardMarkup);
        newMessage.setText("Choose your language");
        return newMessage;
    }

    private SendMessage constructMessage(Update update){
        SendMessage blank = new SendMessage();
        blank.setChatId(update.getMessage().getChatId());
        return blank;
    }
}
