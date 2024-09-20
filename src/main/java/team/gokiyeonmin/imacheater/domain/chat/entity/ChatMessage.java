package team.gokiyeonmin.imacheater.domain.chat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gokiyeonmin.imacheater.domain.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "message_id", nullable = false)
    private String messageId;

    @Column(name = "send_at", nullable = false)
    private LocalDateTime sendAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false, updatable = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false, updatable = false)
    private User sender;

    public Boolean isRead(LocalDateTime lastReadAt) {
        return this.sendAt.isBefore(lastReadAt);
    }
}
