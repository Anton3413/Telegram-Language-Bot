package anton3413.telegramlanguagebot.repository;


import anton3413.telegramlanguagebot.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
