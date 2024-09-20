package team.gokiyeonmin.imacheater.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gokiyeonmin.imacheater.domain.chat.dto.req.ChatRoomCreateRequest;
import team.gokiyeonmin.imacheater.domain.chat.dto.res.ChatRoomCreateResponse;
import team.gokiyeonmin.imacheater.domain.chat.dto.res.ChatRoomDetailResponse;
import team.gokiyeonmin.imacheater.domain.chat.dto.res.ChatRoomsResponse;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatMessage;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatRoom;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatRoomUser;
import team.gokiyeonmin.imacheater.domain.chat.repository.ChatMessageRepository;
import team.gokiyeonmin.imacheater.domain.chat.repository.ChatRoomRepository;
import team.gokiyeonmin.imacheater.domain.chat.repository.ChatRoomUserRepository;
import team.gokiyeonmin.imacheater.domain.item.entity.Item;
import team.gokiyeonmin.imacheater.domain.item.repository.ItemRepository;
import team.gokiyeonmin.imacheater.domain.user.entity.User;
import team.gokiyeonmin.imacheater.domain.user.repository.UserRepository;
import team.gokiyeonmin.imacheater.global.exception.BusinessException;
import team.gokiyeonmin.imacheater.global.exception.ErrorCode;
import team.gokiyeonmin.imacheater.global.util.Pair;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public ChatRoomCreateResponse createChatRoom(Long userId, ChatRoomCreateRequest request) {
        User customer = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        User seller = userRepository.findById(request.sellerId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        Item item = itemRepository.findById(request.itemId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));

        ChatRoom chatRoom = chatRoomRepository.save(
                ChatRoom.builder()
                        .item(item)
                        .build()
        );

        ChatRoomUser chatRoomCustomer = ChatRoomUser.builder()
                .chatRoom(chatRoom)
                .user(customer)
                .build();

        ChatRoomUser chatRoomSeller = ChatRoomUser.builder()
                .chatRoom(chatRoom)
                .user(seller)
                .build();

        chatRoomUserRepository.save(chatRoomCustomer);
        chatRoomUserRepository.save(chatRoomSeller);

        return ChatRoomCreateResponse.fromEntity(chatRoom);
    }

    @Transactional(readOnly = true)
    public ChatRoomsResponse getAllChatRooms(Long userId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByUser_Id(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        return ChatRoomsResponse.of(
                chatRooms.stream()
                        .map(chatRoom -> Pair.of(chatRoom, user))
                        .toList());
    }

    @Transactional(readOnly = true)
    public ChatRoomDetailResponse getChatRoomDetail(Long userId, Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CHAT_ROOM));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        if (chatRoom.getChatRoomUsers().stream().noneMatch(chatRoomUser -> chatRoomUser.getUser() == user)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_CHAT_ROOM);
        }

        chatRoom.enter(user);

        List<ChatMessage> chatMessages = chatRoom.getChatMessages();

        return ChatRoomDetailResponse.fromEntity(chatRoom.getItem(), chatMessages);
    }
}
