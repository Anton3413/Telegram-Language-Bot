package anton3413.telegramlanguagebot.service.imp;

import anton3413.telegramlanguagebot.Model.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MessageService {

     public static SendMessage requestHandlder(Update update){
        if(update.getMessage().isCommand()){
            commandResolver
        }
    }
    private static  SendMessage commandResolver(Message message){

    }
}
