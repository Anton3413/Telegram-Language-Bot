package anton3413.telegramlanguagebot.service;


import anton3413.telegramlanguagebot.Model.User;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import reverso.language.Language;

public interface UserService {

    User getUserByChatId(long chatId);

    boolean isUserAlreadyRegistered(long chatId);

    void registerUser(Update update);

    void updateUser(User user);

    boolean isUserReadyToUseBot(long chatId);

}
