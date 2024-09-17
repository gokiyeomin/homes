package team.gokiyeonmin.imacheater.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gokiyeonmin.imacheater.domain.user.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
