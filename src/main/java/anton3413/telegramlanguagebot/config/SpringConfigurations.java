package anton3413.telegramlanguagebot.config;

import anton3413.telegramlanguagebot.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class SpringConfigurations {

    private final BotCredentials botCredentials;

    @Bean
    public TelegramBot telegramBot(){
        return new TelegramBot(botCredentials.getBOT_TOKEN(),botCredentials.getBOT_NAME());
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
