package team.gokiyeonmin.imacheater.domain.chat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatMessageContent;

public interface ChatMessageContentRepository extends MongoRepository<ChatMessageContent, String> {

}
