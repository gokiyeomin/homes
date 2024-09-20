package team.gokiyeonmin.imacheater.domain.chat.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatRoom;

@Schema(description = "채팅방 생성 응답")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record ChatRoomCreateResponse(
        @Schema(description = "채팅방 ID", example = "1")
        @JsonProperty("chatRoomId")
        Long chatRoomId
) {

    public static ChatRoomCreateResponse fromEntity(ChatRoom chatRoom) {
        return new ChatRoomCreateResponse(chatRoom.getId());
    }
}
