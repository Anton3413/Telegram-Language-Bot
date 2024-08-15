package anton3413.telegramlanguagebot.config;

import anton3413.telegramlanguagebot.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import reverso.Reverso;

import java.io.IOException;
import java.util.Properties;

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

    @Bean
    public Properties properties() {
        Properties properties = new Properties();
        try {
            properties.load(SpringConfigurations.class.getResourceAsStream(botCredentials.getBOT_PROPERTIES()));
        } catch (IOException e) {
            throw new RuntimeException("Problem during properties file initialization. Possibly the path is incorrect");
        }
        return properties;
    }

    @Bean
    public Reverso reverso() {
        return new Reverso();
    }
}
