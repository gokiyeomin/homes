package team.gokiyeonmin.imacheater.domain.chat.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import team.gokiyeonmin.imacheater.domain.chat.domain.ChatType;

@Document(collation = "chat_message_content")
@Getter
public class ChatMessageContent {

    @Id
    private String id;

    private ChatType type;

    private ChatData data;

    public String getPreview() {
        if (data instanceof Message) {
            return ((Message) data).getContent();
        } else if (data instanceof Image) {
            return "사진을 보냈습니다.";
        } else {
            return null;
        }
    }

    public String getContent() {
        if (data instanceof Message) {
            return ((Message) data).getContent();
        } else if (data instanceof Image) {
            return ((Image) data).getUrl();
        } else {
            return null;
        }
    }

    public interface ChatData {
    }

    @Getter
    public static class Message implements ChatData {
        private String content;
    }

    @Getter
    public static class Image implements ChatData {
        private String url;
    }
}
