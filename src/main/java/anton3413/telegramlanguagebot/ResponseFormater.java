package anton3413.telegramlanguagebot;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reverso.data.response.Response;
import reverso.data.response.impl.ConjugationResponse;
import reverso.data.response.impl.ContextResponse;
import reverso.data.response.impl.SynonymResponse;

import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component
@AllArgsConstructor
public class ResponseFormater {

    private final Properties properties;

    public String buildPrettyText(Response response, String inputText){
        if(response instanceof SynonymResponse){
            return buildSynonymResponse((SynonymResponse) response, inputText);
        } else if (response instanceof ContextResponse) {
            return buildContextResponse((ContextResponse) response, inputText);
        } else if (response instanceof ConjugationResponse) {
            return buildConjugationResponse((ConjugationResponse) response, inputText);
        }
        throw new IllegalArgumentException("Unsupported response type: " + response.getClass().getSimpleName());
    }

    private String buildSynonymResponse(SynonymResponse synonymResponse, String inputText){
        StringBuilder result = new StringBuilder();

        result.append(properties.getProperty("bot_result_commonPrefixText"))
                .append(" <b>").append(inputText).append("</b>\n\n");

        for (Map.Entry<String, List<String>> entry : synonymResponse.getSynonyms().entrySet()) {
            String partOfSpeech = entry.getKey();
            List<String> words = entry.getValue();

            result.append("<b>").append(partOfSpeech).append("</b>:\n");
            for (int i = 0; i < words.size(); i++) {
                result.append(i + 1).append(". ").append(words.get(i)).append("\n");
            }
            result.append("\n");
        }
        return result.toString();
    }

    private String buildContextResponse(ContextResponse contextResponse, String inputText){
        StringBuilder result = new StringBuilder();

        result.append(properties.getProperty("bot_result_commonPrefixText"))
                .append("<b>").append(inputText).append("</b>\n\n");

        int counter = 1;
        for (Map.Entry<String, String> entry : contextResponse.getContextResults().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            result.append(counter).append(". ")
                    .append(key).append(" : ")
                    .append(value).append("\n");
            counter++;
        }

        result.append(properties.getProperty("bot_result_context_translations"))
                .append(contextResponse.getTranslationsAsString());

        return result.toString();
    }

    private String buildConjugationResponse(ConjugationResponse conjugationResponse, String inputText){
        StringBuilder result = new StringBuilder();

        result.append(properties.getProperty("bot_result_commonPrefixText"))
                .append("<b>").append(inputText).append("</b>\n\n");

        for (Map.Entry<String, String[]> entry : conjugationResponse.getConjugationData().entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();

            result.append("<b>").append(key).append("</b>:\n");

            result.append("  - ").append(String.join("\n  - ", values)).append("\n\n");
        }
        return result.toString();
    }
}
