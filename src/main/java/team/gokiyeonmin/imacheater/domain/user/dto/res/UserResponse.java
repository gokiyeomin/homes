package team.gokiyeonmin.imacheater.domain.user.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import team.gokiyeonmin.imacheater.domain.user.entity.User;

@Schema(description = "사용자 정보 응답")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record UserResponse(
        @Schema(description = "닉네임", example = "고기여민")
        @JsonProperty("nickname")
        String nickname,

        @Schema(description = "아이디", example = "gokiyeonmin")
        @JsonProperty("username")
        String username,

        @Schema(description = "학부/학과", example = "컴퓨터학부")
        @JsonProperty("department")
        String department
) {

    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getNickname(),
                user.getUsername(),
                user.getDepartment()
        );
    }
}
