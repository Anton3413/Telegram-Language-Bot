package anton3413.telegramlanguagebot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import reverso.data.response.Response;
import reverso.data.response.impl.ContextResponse;
import reverso.data.response.impl.SynonymResponse;

@Component
public class ResponseFormater {

    public String formatResponse(Response response, String inputText){
        if(response instanceof SynonymResponse){
           return buildSynonymResponse(response,inputText);
        } else if (response instanceof ContextResponse) {
           return buildContextResponse(response,inputText);
        }
    }

    private String buildSynonymResponse(Response response, String inputText){
        SynonymResponse synonymResponse = (SynonymResponse) response;

    }

    private String buildContextResponse(Response response, String inputText){

    }
}
