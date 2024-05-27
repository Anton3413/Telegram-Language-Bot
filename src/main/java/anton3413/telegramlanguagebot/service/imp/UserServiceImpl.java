package anton3413.telegramlanguagebot.service.imp;

import anton3413.telegramlanguagebot.Model.TranslateLanguage;
import anton3413.telegramlanguagebot.Model.User;
import anton3413.telegramlanguagebot.repository.UserRepository;
import anton3413.telegramlanguagebot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public void registerNewUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

}
