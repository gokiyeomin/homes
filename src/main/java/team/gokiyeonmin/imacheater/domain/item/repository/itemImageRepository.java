package team.gokiyeonmin.imacheater.domain.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gokiyeonmin.imacheater.domain.item.entity.ItemImage;

public interface itemImageRepository extends JpaRepository<ItemImage, Long> {
}
