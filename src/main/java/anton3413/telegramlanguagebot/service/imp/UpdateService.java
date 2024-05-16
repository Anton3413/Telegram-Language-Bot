package anton3413.telegramlanguagebot.service.imp;

import anton3413.telegramlanguagebot.handler.CallbackHandler;
import anton3413.telegramlanguagebot.handler.CommandHandler;
import anton3413.telegramlanguagebot.handler.ReplyButtonHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Service
@RequiredArgsConstructor
public class UpdateService {

    private final CommandHandler commandHandler;

    private final CallbackHandler callbackHandler;

    public SendMessage handleUpdate(Update update){
        if(update.getMessage().isCommand()){
           return commandHandler.defineCommand(update);
        }else if(update.hasCallbackQuery()){
            return callbackHandler(update);
        }
          else return null;
        }
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
