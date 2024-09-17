package team.gokiyeonmin.imacheater.domain.auth.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import team.gokiyeonmin.imacheater.domain.auth.event.SignInEvent;

@Schema(description = "로그인 요청 DTO")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record SignInRequest(
        @JsonProperty("username")
        @Schema(description = "아이디", example = "gokiyeonmin")
        @NotNull(message = "아이디는 필수입니다.")
        @NotBlank(message = "아이디는 필수입니다.")
        String username,

        @JsonProperty("password")
        @Schema(description = "비밀번호", example = "password")
        @NotNull(message = "비밀번호는 필수입니다.")
        @NotBlank(message = "비밀번호는 필수입니다.")
        String password
) {
    public SignInEvent toEvent() {
        return SignInEvent.builder()
                .username(username())
                .password(password())
                .build();
    }
}
