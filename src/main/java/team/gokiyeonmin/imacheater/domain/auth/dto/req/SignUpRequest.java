package team.gokiyeonmin.imacheater.domain.auth.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import team.gokiyeonmin.imacheater.domain.auth.event.SignUpEvent;

@Schema(description = "회원가입 요청 DTO")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record SignUpRequest(
        @JsonProperty("username")
        @Schema(description = "아이디", example = "gokiyeonmin")
        @NotNull(message = "아이디는 필수입니다.")
        @NotBlank(message = "아이디는 필수입니다.")
        String username,

        @JsonProperty("password")
        @Schema(description = "비밀번호", example = "password")
        @NotNull(message = "비밀번호는 필수입니다.")
        @NotBlank(message = "비밀번호는 필수입니다.")
        String password,

        @JsonProperty("nickname")
        @Schema(description = "닉네임", example = "고기여민")
        String nickname,

        @JsonProperty("department")
        @Schema(description = "학부/학과", example = "컴퓨터학부")
        String department
) {

    public SignUpEvent toEvent() {
        return SignUpEvent.builder()
                .username(username())
                .password(password())
                .nickname(nickname())
                .department(department())
                .build();
    }
}
