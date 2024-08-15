package anton3413.telegramlanguagebot.service.imp;

import anton3413.telegramlanguagebot.resolver.CallbackResolver;
import anton3413.telegramlanguagebot.resolver.CommandResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;


@Service
@RequiredArgsConstructor
public class UpdateService {

    private final ReversoService translateService;

    private final CommandResolver commandResolver;

    private final CallbackResolver callbackResolver;

    public Object handleUpdate(Update update) {

        if (update.getMessage() == null) {
            if (update.hasCallbackQuery()) {
                return callbackResolver.defineCallback(update);
            }
        } else if (update.getMessage().isCommand()) {
            return commandResolver.defineCommand(update);
        }
        return translateService.handleMessage(update);
    }




}
