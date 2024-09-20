package team.gokiyeonmin.imacheater.domain.chat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gokiyeonmin.imacheater.domain.item.entity.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "chat_rooms")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY)
    private List<ChatRoomUser> chatRoomUsers = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /* -------------------------------------------- */
    /* ----------------- Functions ---------------- */
    /* -------------------------------------------- */
    @Builder
    public ChatRoom(Item item) {
        this.item = item;
    }

    public Optional<ChatMessage> getLastMessage() {
        return this.chatMessages.stream()
                .max(Comparator.comparing(ChatMessage::getSendAt));
    }

    public String getTitle() {
        return this.item.getTitle();
    }
}
