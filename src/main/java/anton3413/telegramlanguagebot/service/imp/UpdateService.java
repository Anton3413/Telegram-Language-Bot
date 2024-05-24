package anton3413.telegramlanguagebot.service.imp;

import anton3413.telegramlanguagebot.resolver.CallbackResolver;
import anton3413.telegramlanguagebot.resolver.CommandResolver;
import anton3413.telegramlanguagebot.resolver.TranslateResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;


@Service
@RequiredArgsConstructor
public class UpdateService {

    private final TranslateResolver translateResolver;

    private final CommandResolver commandHandler;

    private final CallbackResolver callbackHandler;

    public Object handleUpdate(Update update) {

        if (update.getMessage() == null) {
            if (update.hasCallbackQuery()) {
                return callbackHandler.defineCallback(update);
            }
        } else if (update.getMessage().isCommand()) {
            return commandHandler.defineCommand(update);
        }
        return translateResolver.translateText(update);
    }
}
