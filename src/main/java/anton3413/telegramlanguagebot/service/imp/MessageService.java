package anton3413.telegramlanguagebot.service.imp;

import anton3413.telegramlanguagebot.handler.CommandHandler;
import anton3413.telegramlanguagebot.handler.ReplyButtonHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


@Service
@RequiredArgsConstructor
public class MessageService {

    private final CommandHandler commandHandler;

    private final ReplyButtonHandler buttonHandler;

    public SendMessage requestHandlder(Update update){
        if(update.getMessage().isCommand()){
           return commandHandler.startCommand(update.getMessage());
        }else if(update.getMessage().isReply()){
            return replyResolver(update.getMessage());
        }
          else return null;
        }*/
    /*private SendMessage commandResolver(Message message){
      String text = message.getText();

      switch (text){
          case "/start" -> {
            return CommandHandler.startCommandHandler(message);
          }
          case "/register" ->{
              return
          }
      }
    }*/
    /*private SendMessage replyResolver(Message message){

    }

    private SendMessage startCommand(Message message){

    }*/


}
