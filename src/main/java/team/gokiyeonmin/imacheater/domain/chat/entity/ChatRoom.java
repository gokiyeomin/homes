package team.gokiyeonmin.imacheater.domain.chat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.gokiyeonmin.imacheater.domain.item.entity.Item;
import team.gokiyeonmin.imacheater.domain.user.entity.User;
import team.gokiyeonmin.imacheater.global.exception.BusinessException;
import team.gokiyeonmin.imacheater.global.exception.ErrorCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        this.createdAt = LocalDateTime.now();
    }

    public String getPreview() {
        return this.chatMessages.stream()
                .max(Comparator.comparing(ChatMessage::getCreatedAt))
                .map(ChatMessage::getContent)
                .orElse("");
    }

    public String getTitle() {
        return this.item.getTitle();
    }

    public Integer getUnreadCount(User user) {
        LocalDateTime lastReadAt = this.getMyChatRoomUser(user.getId()).getLastReadAt();

        return this.chatMessages.stream()
                .filter(chatMessage -> chatMessage.isRead(lastReadAt))
                .toList()
                .size();
    }

    public void enter(User user) {
        ChatRoomUser chatRoomUser = this.getMyChatRoomUser(user.getId());
        chatRoomUser.updateLastReadAt();
    }

    private ChatRoomUser getMyChatRoomUser(Long userId) {
        return this.chatRoomUsers.stream()
                .filter(chatRoomUser -> chatRoomUser.getUser().getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CHAT_ROOM));
    }
}
