package team.gokiyeonmin.imacheater.domain.chat.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatRoom;
import team.gokiyeonmin.imacheater.domain.user.entity.User;
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
                    User user = pair.second();
                    return ChatRoomDto.of(
                            chatRoom.getId(),
                            chatRoom.getTitle(),
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

            @Schema(description = "매물 이름")
            @JsonProperty("title")
            String title,

            @Schema(description = "채팅방 미리보기")
            @JsonProperty("preview")
            String preview,

            @Schema(description = "안 읽은 채팅 개수")
            @JsonProperty("unreadCount")
            Integer unreadCount,

            @Schema(description = "상대방 정보")
            @JsonProperty("user")
            UserDto user
    ) {

        static ChatRoomDto of(
                Long chatRoomId,
                String name,
                String preview,
                Integer unreadCount,
                User user
        ) {
            return new ChatRoomDto(
                    chatRoomId,
                    name,
                    preview,
                    unreadCount,
                    UserDto.fromEntity(user)
            );
        }

        private record UserDto(
                Long id,
                String nickname,
                String imageUrl
        ) {

            static UserDto fromEntity(User user) {
                return new UserDto(
                        user.getId(),
                        user.getNickname(),
                        user.getUserImage().getUrl()
                );
            }
        }
    }

}
