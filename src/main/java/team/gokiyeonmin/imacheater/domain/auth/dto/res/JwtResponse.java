package team.gokiyeonmin.imacheater.domain.auth.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "JWT 응답 DTO")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record JwtResponse(
        @Schema(description = "Access Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTYyMzIwNjMwMH0.1")
        @JsonProperty("accessToken")
        String accessToken

        // TODO: Add refresh token
) {

}
