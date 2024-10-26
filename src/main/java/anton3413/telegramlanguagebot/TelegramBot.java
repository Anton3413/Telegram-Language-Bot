package anton3413.telegramlanguagebot;

import anton3413.telegramlanguagebot.handler.TelegramUpdateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;

    @Autowired
    private TelegramUpdateHandler telegramUpdateHandler;

    public TelegramBot() {
        telegramClient = new OkHttpTelegramClient(getBotToken());
        setBotCommands();
    }

    @Override
    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {

        try {
            telegramUpdateHandler.handleUpdate(update,telegramClient);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    private void setBotCommands () {
        List<BotCommand> commands = new ArrayList<>();
        commands.add(new BotCommand("/language", "Select the required languages"));
        commands.add(new BotCommand("/translate", "Translation mode"));
        commands.add(new BotCommand("/synonyms", "Synonyms mode"));
        commands.add(new BotCommand("/conjugation", "Conjugation mode"));
        commands.add(new BotCommand("/help", "Full information about this bot"));
        commands.add(new BotCommand("/info", "Additional information about this bot"));

        SetMyCommands setMyCommands = new SetMyCommands(commands);
        try {
            telegramClient.execute(setMyCommands);
        } catch (TelegramApiException e) {
            System.out.println(" ");
        }
    }
}