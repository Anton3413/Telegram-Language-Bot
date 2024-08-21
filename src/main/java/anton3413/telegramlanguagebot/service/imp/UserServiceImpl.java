package anton3413.telegramlanguagebot.service.imp;

import anton3413.telegramlanguagebot.Model.User;
import anton3413.telegramlanguagebot.repository.UserRepository;
import anton3413.telegramlanguagebot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByChatId(long chatId) {
       return userRepository.findById(chatId).orElseThrow(RuntimeException::new);
    }

    @Override
    public boolean isUserAlreadyRegistered(long chatId) {
      return userRepository.existsById(chatId);
    }

    @Override
    public boolean isUserReadyToUseBot(long chatId) {
        if(!isUserAlreadyRegistered(chatId)){
            return false;
        }
        User user = getUserByChatId(chatId);
        return  user.getSourceLanguage() != null &&
                user.getTargetLanguage() != null;
    }

    @Override
    public void registerUser(Update update) {
        Chat chat = update.getMessage().getChat();

        User user = new User();
        user.setChatId(update.getMessage().getChatId());
        user.setUserName("@"+ chat.getUserName());
        user.setFirstName(chat.getFirstName());
        user.setLastName(chat.getLastName());
        user.setRegisteredAt(LocalDateTime.now());
        user.setCurrentCommand("/translate");

        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }
}
