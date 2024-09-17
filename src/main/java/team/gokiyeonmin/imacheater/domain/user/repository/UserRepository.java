package team.gokiyeonmin.imacheater.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.gokiyeonmin.imacheater.domain.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);

    Boolean existsByNickname(String nickname);
}
