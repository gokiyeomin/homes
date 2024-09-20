package team.gokiyeonmin.imacheater.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gokiyeonmin.imacheater.domain.chat.dto.res.ChatRoomListResponse;
import team.gokiyeonmin.imacheater.domain.chat.dto.res.ChatRoomResponse;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatMessage;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatMessageContent;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatRoom;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatRoomUser;
import team.gokiyeonmin.imacheater.domain.chat.repository.ChatMessageContentRepository;
import team.gokiyeonmin.imacheater.domain.chat.repository.ChatMessageRepository;
import team.gokiyeonmin.imacheater.domain.chat.repository.ChatRoomRepository;
import team.gokiyeonmin.imacheater.domain.item.repository.ItemRepository;
import team.gokiyeonmin.imacheater.domain.user.entity.User;
import team.gokiyeonmin.imacheater.domain.user.repository.UserRepository;
import team.gokiyeonmin.imacheater.global.exception.BusinessException;
import team.gokiyeonmin.imacheater.global.exception.ErrorCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageContentRepository chatMessageContentRepository;

    @Transactional(readOnly = true)
    public ChatRoomListResponse getAllChatRooms(Long userId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByUser_Id(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        // TODO: 리팩토링 필수
        // 사유: 너무 잠이 와서 코드 집중력이 떨어짐
        List<ChatRoomResponse> responses = new ArrayList<>();
        for (ChatRoom chatRoom : chatRooms) {
            String preview = chatRoom.getLastMessage()
                    .map(ChatMessage::getMessageId)
                    .flatMap(chatMessageContentRepository::findById)
                    .map(ChatMessageContent::getPreview)
                    .orElse("");

            Integer unreadCounter = chatRoom.getChatMessages().stream()
                    .filter(chatMessage -> {
                        User sender = chatMessage.getSender();
                        LocalDateTime lastReadAt = chatRoom.getChatRoomUsers().stream()
                                .filter(chatRoomUser -> chatRoomUser.getUser() == sender)
                                .map(ChatRoomUser::getLastReadAt)
                                .findFirst()
                                .orElse(LocalDateTime.MIN);
                        return !sender.getId().equals(userId) && !chatMessage.isRead(lastReadAt);
                    }).toList().size();

            ChatRoomResponse response = ChatRoomResponse.of(
                    chatRoom.getId(),
                    chatRoom.getTitle(),
                    preview,
                    unreadCounter,
                    user
            );

            responses.add(response);
        }

        return ChatRoomListResponse.of(responses);
    }
}
