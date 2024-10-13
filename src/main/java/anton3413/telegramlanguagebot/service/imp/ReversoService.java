package anton3413.telegramlanguagebot.service.imp;


import anton3413.telegramlanguagebot.Model.User;
import anton3413.telegramlanguagebot.ResponseFormater;
import anton3413.telegramlanguagebot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import reverso.Reverso;
import reverso.data.response.impl.ConjugationResponse;
import reverso.data.response.impl.ContextResponse;
import reverso.data.response.impl.SynonymResponse;
import reverso.language.Language;


import java.util.Properties;

@Service
@RequiredArgsConstructor
public class ReversoService {

    private final UserService userService;
    private final Reverso reverso;
    private final ResponseFormater formatter;
    private final Properties properties;

    public SendMessage handleMessage(Update update) {

        SendMessage sendMessage = constructMessage(update);

        String currentCommand = null;
        User user = null;

        if (!userService.isUserReadyToUseBot(update.getMessage().getChatId())) {
            sendMessage.setText(formatter.buildNoAccessText());
            return sendMessage;
        }
        else {
            user = userService.getUserByChatId(update.getMessage().getChatId());

            currentCommand = user.getCurrentCommand();
        }

        return switch (currentCommand) {
            case "/translate","trans"
                    -> getContext(user.getSourceLanguage(),user.getTargetLanguage(),sendMessage,update.getMessage().getText());
            case "/synonyms","syn"
                    -> getSynonyms(user.getSourceLanguage(),sendMessage,update.getMessage().getText());
            case "/conjugation", "conj"
                -> getConjugation(user.getSourceLanguage(),sendMessage,update.getMessage().getText());
            default -> throw new IllegalStateException("Unexpected value: " + currentCommand);
        };
    }

    private SendMessage constructMessage(Update update) {
        SendMessage blank = new SendMessage();
        blank.setChatId(update.getMessage().getChatId());
        blank.enableHtml(true);
        return blank;
    }

    private SendMessage getContext(Language source, Language target, SendMessage message, String text){
        ContextResponse contextResponse = reverso.getContext(source, target, text);

        if(!contextResponse.isOK()) {
            message.setText(contextResponse.getErrorMessage());
        }
        else {
            message.setText(formatter.buildPrettyText(contextResponse,text));
        }
        return message;
    }

    private SendMessage getSynonyms(Language source, SendMessage message, String text) {
        SynonymResponse synonymResponse = reverso.getSynonyms(source, text);

        if(!synonymResponse.isOK()) {
            message.setText("⚠️ " + synonymResponse.getErrorMessage() + " \uD83E\uDD7A");
        }
        else {
            message.setText((formatter.buildPrettyText(synonymResponse, text)));
        }

        return message;
    }

    private SendMessage getConjugation(Language language, SendMessage message, String text){
        ConjugationResponse conjugationResponse = reverso.getWordConjugation(language, text);

        if(!conjugationResponse.isOK()){
            message.setText("⚠️ " + conjugationResponse.getErrorMessage() + " \uD83E\uDD7A");
        }
        else {
            message.setText((formatter.buildPrettyText(conjugationResponse, text)));
        }
        return message;
    }

   public SendMessage handleVoiceMessage(Update update){
       SendMessage message = constructMessage(update);
       message.setText(properties.getProperty("bot_unsupportedOperation_message"));
       return message;
   }
}
