package team.gokiyeonmin.imacheater.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.gokiyeonmin.imacheater.domain.chat.dto.req.ChatRoomIdRequest;
import team.gokiyeonmin.imacheater.domain.chat.dto.res.ChatRoomDetailResponse;
import team.gokiyeonmin.imacheater.domain.chat.dto.res.ChatRoomIdResponse;
import team.gokiyeonmin.imacheater.domain.chat.dto.res.ChatRoomsResponse;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatMessage;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatRoom;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatRoomUser;
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

    @Transactional
    public ChatRoomIdResponse getChatRoomId(Long customerId, ChatRoomIdRequest request) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));
        Item item = itemRepository.findById(request.itemId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));
        User seller = item.getUser();

        List<ChatRoom> chatRooms = chatRoomRepository.findAllByItem(item);

        ChatRoom chatRoom = chatRooms.stream()
                .filter(_chatRoom -> isChatRoomExist(_chatRoom, customer, seller))
                .findFirst()
                .orElseGet(() -> createChatRoom(item, customer, seller));

        return ChatRoomIdResponse.fromEntity(chatRoom);
    }

    @Transactional(readOnly = true)
    public ChatRoomsResponse getAllChatRooms(Long userId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllByUser_Id(userId);
        List<User> users = chatRooms.stream().map(
                        chatRoom -> chatRoom.getChatRoomUsers().stream()
                                .filter(chatRoomUser -> chatRoomUser.getUser().getId() != userId)
                                .findFirst()
                                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CHAT_ROOM))
                ).map(ChatRoomUser::getUser)
                .toList();

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

        return ChatRoomDetailResponse.fromEntity(chatRoom.getItem(), chatMessages, userId);
    }

    private Boolean isChatRoomExist(ChatRoom chatRoom, User customer, User seller) {
        List<ChatRoomUser> chatRoomUsers = chatRoom.getChatRoomUsers();
        return chatRoomUsers.stream().anyMatch(chatRoomUser -> chatRoomUser.getUser() == customer)
                && chatRoomUsers.stream().anyMatch(chatRoomUser -> chatRoomUser.getUser() == seller);
    }

    @Transactional
    protected ChatRoom createChatRoom(Item item, User customer, User seller) {
        ChatRoom chatRoom = ChatRoom.builder()
                .item(item)
                .build();
        chatRoomRepository.save(chatRoom);

        ChatRoomUser customerChatRoomUser = ChatRoomUser.builder()
                .chatRoom(chatRoom)
                .user(customer)
                .build();
        chatRoomUserRepository.save(customerChatRoomUser);

        ChatRoomUser sellerChatRoomUser = ChatRoomUser.builder()
                .chatRoom(chatRoom)
                .user(seller)
                .build();
        chatRoomUserRepository.save(sellerChatRoomUser);

        return chatRoom;
    }
}
