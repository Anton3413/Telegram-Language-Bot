package anton3413.telegramlanguagebot.service.imp;

import anton3413.telegramlanguagebot.client.TranslationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class TranslateService {

    private final RestTemplate restTemplate;

    private final String API_URL = System.getProperty("API_URL");
    private final String API_KEY= System.getProperty("API_KEY");

    public String translateText(String text,String targetLanguage) {
        // Заголовки HTTP-запроса
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", API_KEY);
        headers.set("x-rapidapi-host", "google-translator9.p.rapidapi.com");
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Тело запроса
        String requestBody = String.format("{\"q\":\"%s\",\"source\":\"\",\"target\":\"%s\",\"format\":\"text\"}", text, targetLanguage);

        // Создание объекта HttpEntity, который включает заголовки и тело запроса
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Выполнение POST-запроса и автоматическое преобразование JSON-ответа в объект TranslationResponse
        TranslationResponse translationResponse = restTemplate.postForObject(API_URL, entity, TranslationResponse.class);

        // Проверка и возврат переведенного текста
        if (translationResponse != null && translationResponse.getData() != null && !translationResponse.getData().getTranslations().isEmpty()) {
            return translationResponse.getData().getTranslations().get(0).getTranslatedText();
        } else {
            throw new RuntimeException("Translation failed or no translation found.");
        }
    }

}
