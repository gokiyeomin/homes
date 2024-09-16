package team.gokiyeonmin.imacheater.domain.example.dto.req;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import team.gokiyeonmin.imacheater.domain.example.entity.Example;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record ExampleRequest(
        @NotNull(message = "이름은 필수입니다.")
        String name,

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
