package team.gokiyeonmin.imacheater.domain.example.dto.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import team.gokiyeonmin.imacheater.domain.example.entity.Example;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record ExampleResponse(
        String message
) {

    public ExampleResponse fromEntity(Example example) {
        return new ExampleResponse("Hello, " + example.getName() + "!," + example.getAge());
    }
}
