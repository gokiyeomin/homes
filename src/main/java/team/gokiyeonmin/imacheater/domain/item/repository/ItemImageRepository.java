package team.gokiyeonmin.imacheater.domain.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gokiyeonmin.imacheater.domain.item.entity.ItemImage;

import java.util.Optional;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
    Optional<ItemImage> findByImageUrl(String imageUrl);
}
