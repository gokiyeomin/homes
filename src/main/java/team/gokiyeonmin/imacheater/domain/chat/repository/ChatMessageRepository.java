package team.gokiyeonmin.imacheater.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByChatRoom_Id(Long chatRoomId);
}
