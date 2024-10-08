package team.gokiyeonmin.imacheater.domain.chat.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatRoom;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatRoomUser;
import team.gokiyeonmin.imacheater.domain.user.entity.User;
import team.gokiyeonmin.imacheater.global.exception.BusinessException;
import team.gokiyeonmin.imacheater.global.exception.ErrorCode;
import team.gokiyeonmin.imacheater.global.util.Pair;

import java.util.List;

@Schema(description = "채팅방 목록 조회 응답")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record ChatRoomsResponse(
        List<ChatRoomDto> chatRooms
) {
    public static ChatRoomsResponse of(
            List<Pair<ChatRoom, User>> pairs
    ) {
        return new ChatRoomsResponse(pairs.stream()
                .map(pair -> {
                    ChatRoom chatRoom = pair.first();
                    User me = pair.second();

                    ChatRoomUser _chatRoomUser = chatRoom.getChatRoomUsers().stream()
                            .filter(chatRoomUser -> chatRoomUser.getUser().getId() != me.getId())
                            .findFirst()
                            .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CHAT_ROOM));

                    User user = _chatRoomUser.getUser();

                    return ChatRoomDto.of(
                            chatRoom.getId(),
                            chatRoom.getTitle(),
                            chatRoom.getItem().getName(),
                            chatRoom.getPreview(),
                            chatRoom.getUnreadCount(user),
                            user
                    );
                })
                .toList()
        );
    }

    @Schema(description = "채팅방 조회 응답")
    @JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
    private record ChatRoomDto(
            @Schema(description = "채팅방 ID")
            @JsonProperty("chatRoomId")
            Long id,

            @Schema(description = "매물 제목")
            @JsonProperty("title")
            String title,

            @Schema(description = "매물 이름")
            @JsonProperty("name")
            String name,

            @Schema(description = "채팅방 미리보기")
            @JsonProperty("preview")
            String preview,

            @Schema(description = "안 읽은 채팅 개수")
            @JsonProperty("unreadCount")
            Integer unreadCount,

            @Schema(description = "상대방 닉네임")
            @JsonProperty("nickname")
            String nickname,

            @Schema(description = "상대방 이미지 URL")
            @JsonProperty("imageUrl")
            String imageUrl
    ) {

        static ChatRoomDto of(
                Long chatRoomId,
                String title,
                String name,
                String preview,
                Integer unreadCount,
                User user
        ) {
            return new ChatRoomDto(
                    chatRoomId,
                    title,
                    name,
                    preview,
                    unreadCount,
                    user.getNickname(),
                    user.getUserImage().getUrl()
            );
        }
    }

}
