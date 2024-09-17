package team.gokiyeonmin.imacheater.domain.user.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 정보 수정 요청")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record UserUpdateRequest(
        @Schema(description = "닉네임", example = "gokiyeonmin")
        @JsonProperty(value = "nickname")
        String nickname
) {
}
