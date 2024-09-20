package team.gokiyeonmin.imacheater.domain.chat.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import team.gokiyeonmin.imacheater.domain.user.entity.User;

@Schema(description = "채팅방 조회 응답")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record ChatRoomResponse(
        @Schema(description = "채팅방 ID")
        @JsonProperty("chatRoomId")
        Long id,

        @Schema(description = "매물 이름")
        @JsonProperty("title")
        String name,

        @Schema(description = "채팅방 미리보기")
        @JsonProperty("preview")
        String preview,

        @Schema(description = "안 읽은 채팅 개수")
        @JsonProperty("unreadCount")
        Integer unreadCount,

        @Schema(description = "상대방 정보")
        @JsonProperty("user")
        UserInfo user
) {

    public static ChatRoomResponse of(
            Long chatRoomId,
            String name,
            String preview,
            Integer unreadCount,
            User user
    ) {
        return new ChatRoomResponse(
                chatRoomId,
                name,
                preview,
                unreadCount,
                UserInfo.fromEntity(user)
        );
    }

    private record UserInfo(
            Long id,
            String nickname,
            String imageUrl
    ) {

        public static UserInfo fromEntity(User user) {
            return new UserInfo(
                    user.getId(),
                    user.getNickname(),
                    user.getUserImage().getUrl()
            );
        }
    }
}
