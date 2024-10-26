package anton3413.telegramlanguagebot.Model;

import jakarta.persistence.*;
import lombok.Data;
import reverso.language.Language;

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

    @Column(name = "source_language")
    @Enumerated(EnumType.STRING)
    private Language sourceLanguage;

    @Column(name = "target_Language")
    @Enumerated(EnumType.STRING)
    private Language targetLanguage;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @Column(name = "current_command")
    private String currentCommand;
}
