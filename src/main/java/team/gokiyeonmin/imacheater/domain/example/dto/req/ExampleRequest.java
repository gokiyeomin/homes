package team.gokiyeonmin.imacheater.domain.example.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import team.gokiyeonmin.imacheater.domain.example.entity.Example;

@Schema(description = "예제 요청 DTO")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record ExampleRequest(
        @Schema(description = "이름", example = "고기여민")
        @JsonProperty("name")
        @NotNull(message = "이름은 필수입니다.")
        String name,

        @Schema(description = "나이", example = "20")
        @JsonProperty("age")
        @NotNull(message = "나이는 필수입니다.")
        Integer age
) {

    public Example toEntity() {
        return Example.builder()
                .name(name)
                .age(age)
                .build();
    }
}
