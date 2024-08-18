package anton3413.telegramlanguagebot.resolver;

import anton3413.telegramlanguagebot.handler.CallbackHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class CallbackResolver {

    private final CallbackHandler callbackHandler;

    public EditMessageText defineCallback(Update update) {

        String callBackData = update.getCallbackQuery().getData();

        if(callBackData.startsWith("src_lang")){
           return callbackHandler.changeSourceLanguage(update);
        }
        else if(callBackData.startsWith("trg_lang")){
            return callbackHandler.changeTargetLanguage(update);
        }

       return null;
    }

}

