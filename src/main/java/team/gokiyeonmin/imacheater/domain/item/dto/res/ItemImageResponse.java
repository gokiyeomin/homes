package team.gokiyeonmin.imacheater.domain.item.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import team.gokiyeonmin.imacheater.domain.item.entity.Item;
import team.gokiyeonmin.imacheater.domain.item.entity.ItemImage;

@Schema(description = "아이템 이미지 응답")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record ItemImageResponse(
        @Schema(description = "아이템 이미지 URL", example = "https://example.com/image.jpg")
        @JsonProperty("url")
        String url,

        @Schema(description = "썸네일 여부", example = "true")
        @JsonProperty("isThumbnail")
        Boolean isThumbnail
) {
    public static ItemImageResponse fromEntity(ItemImage itemImage) {
        return new ItemImageResponse(
                itemImage.getUrl(),
                itemImage.getIsThumbnail()
        );
    }
}