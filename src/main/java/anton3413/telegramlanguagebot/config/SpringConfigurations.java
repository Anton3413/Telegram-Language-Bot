package anton3413.telegramlanguagebot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reverso.Reverso;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Configuration
public class SpringConfigurations {
    @Bean
    public Properties properties() {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getResourceAsStream("/properties/bot_messages.properties");
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
             properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException("Problem during properties file initialization. Possibly the path is incorrect", e);
        }
        return properties;
    }
    @Bean
    public Reverso reverso() {
        return new Reverso();
    }

}
