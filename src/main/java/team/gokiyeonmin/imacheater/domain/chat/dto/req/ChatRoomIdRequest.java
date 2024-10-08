package team.gokiyeonmin.imacheater.domain.chat.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "채팅방 ID 요청")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record ChatRoomIdRequest(
        @Schema(description = "상품 ID", example = "1")
        @JsonProperty("itemId")
        Long itemId
) {
}
