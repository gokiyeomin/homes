package team.gokiyeonmin.imacheater.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatRoom;
import team.gokiyeonmin.imacheater.domain.item.entity.Item;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT DISTINCT cr FROM ChatRoom cr JOIN FETCH cr.chatRoomUsers cru ON cr.id = cru.chatRoom.id WHERE cru.user.id = :userId")
    List<ChatRoom> findAllByUser_Id(@Param("userId") Long userId);

    List<ChatRoom> findAllByItem(Item item);
}
