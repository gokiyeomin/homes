package team.gokiyeonmin.imacheater.domain.user.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "유저 이미지 응답")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record UserImageResponse(
        @Schema(description = "유저 이미지 URL", example = "https://example.com/image.jpg")
        @JsonProperty("url")
        String url
) {
}
