package team.gokiyeonmin.imacheater.domain.chat.dto.req;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "채팅 메시지 전송 요청")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record ChatMessageSendRequest(
        @Schema(description = "유저 ID", example = "1")
        @NotNull(message = "유저 ID는 필수입니다.")
        Long userId,

        @Schema(description = "채팅 내용", example = "안녕하세요")
        @NotNull(message = "채팅 내용은 필수입니다.")
        String content
) {
}
