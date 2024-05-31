package anton3413.telegramlanguagebot.client;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TranslationResponse {
    private Data data;


   @Setter
   @Getter
   public static class Data {
        private java.util.List<Translation> translations;
    }
    @Setter
    @Getter
   public static class Translation {
        private String translatedText;
        private String detectedSourceLanguage;

    }
}