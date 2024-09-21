package team.gokiyeonmin.imacheater.domain.item.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import team.gokiyeonmin.imacheater.domain.item.entity.ItemImage;

import java.util.List;

@Schema(description = "아이템 이미지 응답")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record ItemImagesResponse(
        @Schema(description = "아이템 이미지 URL", example = "https://example.com/image.jpg")
        @JsonProperty("images")
        List<String> images
) {
    public static ItemImagesResponse fromEntity(List<ItemImage> itemImage) {
        return new ItemImagesResponse(itemImage.stream()
                .map(ItemImage::getUrl)
                .toList());
    }
}