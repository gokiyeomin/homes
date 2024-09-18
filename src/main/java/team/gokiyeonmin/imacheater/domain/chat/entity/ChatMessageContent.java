package team.gokiyeonmin.imacheater.domain.chat.entity;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import team.gokiyeonmin.imacheater.domain.chat.domain.ChatType;

@Document(collation = "chat_message_content")
public class ChatMessageContent {

    @Id
    private String id;

    private ChatType type;

    private ChatData data;

    public interface ChatData {
    }

    private class Message implements ChatData {
        private String content;
    }

    private class Image implements ChatData {
        private String url;
    }
}
