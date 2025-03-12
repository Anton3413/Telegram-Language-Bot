package anton3413.telegramlanguagebot.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.pinnedmessages.PinChatMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;


@Service
@RequiredArgsConstructor
public class TelegramUpdateHandler {

    private final ReversoApiAdapter reversoApiAdapter;

    private final CommandHandler commandHandler;

    private final InteractionHandler interactionHandler;

    public void handleUpdate(Update update, TelegramClient client) throws TelegramApiException {

        if (update.getMessage() == null) {
            if (update.hasCallbackQuery()) {
                messageSenderFilter(interactionHandler.identifyCallback(update), client);
            }
        } else if (update.getMessage().isCommand()) {
            messageSenderFilter(commandHandler.identifyCommand(update),update,client);
        } else if(hasUnsupportedMedia(update)){
            messageSenderFilter(interactionHandler.handleUnsupportedMessage(update),update,client);
        } else if (update.getMessage().getPinnedMessage()!= null){
             handlePinnedMessage(update,client);
        } else {
            messageSenderFilter(reversoApiAdapter.handleMessage(update),update,client);
        }

    }
    private void messageSenderFilter(SendMessage message, Update update, TelegramClient client) throws TelegramApiException {
        client.execute(message);

        if(message.getText().startsWith("Hello")){
            client.execute(commandHandler.languageCommand(update));
        }
    }
    private void messageSenderFilter(EditMessageText message, TelegramClient client) throws TelegramApiException {
        client.execute(message);

        if(message.getText().startsWith("\uD83E\uDDE9 Source Language")){
            client.execute(PinChatMessage.builder()
                    .chatId(message.getChatId())
                    .messageId(message.getMessageId())
                    .build());
        }
    }

    private boolean hasUnsupportedMedia(Update update){

        Message message = update.getMessage();

        return message.hasPhoto() || message.hasVideo() || message.hasAudio() ||
                message.hasDocument() || message.hasVoice() || message.hasAnimation();
    }

    private void handlePinnedMessage(Update update, TelegramClient client) throws TelegramApiException {
        client.execute(interactionHandler.botReadyToStartMessage(update));
    }

}
