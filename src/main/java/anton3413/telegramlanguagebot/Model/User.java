package anton3413.telegramlanguagebot.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "client")
public class User {

    @Id
    private long chatId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "tranlste_language")
    @Enumerated(EnumType.STRING)
    private TranslateLanguage translateLanguage;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;


}
