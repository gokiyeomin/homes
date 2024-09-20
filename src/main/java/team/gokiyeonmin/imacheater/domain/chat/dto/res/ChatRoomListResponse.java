package team.gokiyeonmin.imacheater.domain.chat.dto.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "채팅방 목록 조회 응답")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record ChatRoomListResponse(
        List<ChatRoomResponse> chatRooms
) {
    public static ChatRoomListResponse of(List<ChatRoomResponse> chatRooms) {
        return new ChatRoomListResponse(chatRooms);
    }
}
