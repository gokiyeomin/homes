package team.gokiyeonmin.imacheater.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.gokiyeonmin.imacheater.domain.chat.dto.req.ChatMessageSendRequest;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatMessage;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatRoom;
import team.gokiyeonmin.imacheater.domain.chat.repository.ChatMessageRepository;
import team.gokiyeonmin.imacheater.domain.chat.repository.ChatRoomRepository;
import team.gokiyeonmin.imacheater.domain.user.entity.User;
import team.gokiyeonmin.imacheater.domain.user.repository.UserRepository;
import team.gokiyeonmin.imacheater.global.exception.BusinessException;
import team.gokiyeonmin.imacheater.global.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public void createMessage(Long roomId, ChatMessageSendRequest request) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CHAT_ROOM));

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        chatMessageRepository.save(ChatMessage.builder()
                .content(request.content())
                .chatRoom(chatRoom)
                .user(user)
                .build());
    }
}
