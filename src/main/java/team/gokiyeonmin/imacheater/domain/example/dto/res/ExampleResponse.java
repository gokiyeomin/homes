package team.gokiyeonmin.imacheater.domain.example.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import team.gokiyeonmin.imacheater.domain.example.entity.Example;

@Schema(description = "예시 응답 DTO")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record ExampleResponse(
        @Schema(description = "메시지", example = "Hello, gokiyeonmin!")
        @JsonProperty("message")
        String message
) {

    public ExampleResponse fromEntity(Example example) {
        return new ExampleResponse("Hello, " + example.getName() + "!," + example.getAge());
    }
}
