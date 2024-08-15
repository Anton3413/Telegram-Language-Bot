package anton3413.telegramlanguagebot.service.imp;


import anton3413.telegramlanguagebot.Model.User;
import anton3413.telegramlanguagebot.ResponseFormater;
import anton3413.telegramlanguagebot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import reverso.Reverso;
import reverso.data.response.impl.ContextResponse;
import reverso.data.response.impl.SynonymResponse;
import reverso.language.Language;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReversoService {

    private final UserService userService;
    private final Reverso reverso;
    private final ResponseFormater formatter;


    public SendMessage handleMessage(Update update) {

        SendMessage sendMessage = constructMessage(update);

        String currentCommand = null;
        User user = null;

        if (!userService.isUserReadyToUseBot(update.getMessage().getChatId())) {
            sendMessage.setText("You are not ready to use bot");
            return sendMessage;
        }
        else {
            user = userService.getUserByChatId(update.getMessage().getChatId());

            currentCommand = user.getCurrentCommand();
        }

        return switch (currentCommand) {
            case "/translate" -> getContext(user.getSourceLanguage(),user.getTargetLanguage(),sendMessage,update.getMessage().getText());
            case "/synonyms" -> getSynonyms(user.getSourceLanguage(),sendMessage,update.getMessage().getText());
            default -> throw new IllegalStateException("Unexpected value: " + currentCommand);
        };
    }

    private SendMessage constructMessage(Update update) {
        SendMessage blank = new SendMessage();
        blank.setChatId(update.getMessage().getChatId());
        return blank;
    }

    private SendMessage getContext(Language source, Language target, SendMessage message, String text){
        ContextResponse response = reverso.getContext(source, target, text);


       /* message.setText(response.getContextResults().);*/

        return null ;
    }

    private SendMessage getSynonyms(Language source, SendMessage message, String text) {
        SynonymResponse response = reverso.getSynonyms(source, text);

        if(!response.isOK()) {
            message.setText(response.getErrorMessage());
        }
        else {
            message.setText("Synonyms for the word " + text +"\n" + getResponseAsString(response.getSynonyms()));
        }

        return message;
    }

    private String getResponseAsString(Map<String, List<String>> map) {

        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();

            result.append(key)
                    .append(" : ")
                    .append(String.join(", ", values))
                    .append("\n");
            }
            return result.toString();
        }
}
