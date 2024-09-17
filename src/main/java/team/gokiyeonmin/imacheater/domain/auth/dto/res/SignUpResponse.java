package team.gokiyeonmin.imacheater.domain.auth.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 응답 DTO")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record SignUpResponse(
        @JsonProperty("username")
        @Schema(description = "아이디", example = "gokiyeonmin")
        String username,

        @JsonProperty("nickname")
        @Schema(description = "닉네임", example = "고기여민")
        String nickname
) {
}
