package team.gokiyeonmin.imacheater.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.gokiyeonmin.imacheater.domain.user.entity.UserImage;

import java.util.Optional;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage, Long> {

    Optional<UserImage> findByUser_Id(Long userId);
}
