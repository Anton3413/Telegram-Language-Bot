package anton3413.telegramlanguagebot.handler;

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
    private final String BOLD_START_TAG = "<b>";
    private final String BOLD_END_TAG = "</b>";
    private final String NEW_LINE = "\n";

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

    public String buildFailureText(){
        return properties.getProperty("bot_error_use_language_command");
    }

    private String buildSynonymResponse(SynonymResponse synonymResponse, String inputText){
        StringBuilder result = new StringBuilder();

        result.append(properties.getProperty("bot_result_commonPrefixText"))
                .append(BOLD_START_TAG).append(inputText).append(BOLD_END_TAG + NEW_LINE + NEW_LINE);

        for (Map.Entry<String, List<String>> entry : synonymResponse.getSynonyms().entrySet()) {
            String partOfSpeech = entry.getKey();
            List<String> words = entry.getValue();

            result.append(BOLD_START_TAG).append(partOfSpeech).append(BOLD_END_TAG + ":" + NEW_LINE );
            for (int i = 0; i < words.size(); i++) {
                result.append(i + 1).append(". ").append(words.get(i)).append(NEW_LINE);
            }
            result.append(NEW_LINE);
        }
        return result.toString();
    }

    private String buildContextResponse(ContextResponse contextResponse, String inputText){
        StringBuilder result = new StringBuilder();

        result.append(properties.getProperty("bot_result_commonPrefixText"))
                .append(BOLD_START_TAG).append(inputText).append(BOLD_END_TAG + NEW_LINE + NEW_LINE);

        int counter = 1;
        for (Map.Entry<String, String> entry : contextResponse.getContextResults().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            result.append(counter).append(". ")
                    .append(key).append(" : ")
                    .append(value).append(NEW_LINE);
            counter++;
        }

        result.append(properties.getProperty("bot_result_context_translations"))
                .append(contextResponse.getTranslationsAsString());

        return result.toString();
    }

    private String buildConjugationResponse(ConjugationResponse conjugationResponse, String inputText){
        StringBuilder result = new StringBuilder();

        result.append(properties.getProperty("bot_result_commonPrefixText"))
                .append(BOLD_START_TAG).append(inputText).append(BOLD_END_TAG + NEW_LINE + NEW_LINE);

        for (Map.Entry<String, String[]> entry : conjugationResponse.getConjugationData().entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();

            result.append(BOLD_START_TAG).append(key).append(BOLD_END_TAG + ":" + NEW_LINE);

            result.append("  - ").append(String.join("\n  - ", values)).append(NEW_LINE + NEW_LINE);
        }
        return result.toString();
    }
}
