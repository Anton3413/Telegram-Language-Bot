package anton3413.telegramlanguagebot.service;


import anton3413.telegramlanguagebot.Model.TranslateLanguage;
import anton3413.telegramlanguagebot.Model.User;

public interface UserService {

    User getUserByChatId(long chatId);

    boolean isUserAlreadyRegistered(long chatId);

    void registerNewUser(User user);

    void saveUser(User user);

}
