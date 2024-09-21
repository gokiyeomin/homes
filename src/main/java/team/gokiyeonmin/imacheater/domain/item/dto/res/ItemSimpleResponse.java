package team.gokiyeonmin.imacheater.domain.item.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import team.gokiyeonmin.imacheater.domain.item.entity.Item;
import team.gokiyeonmin.imacheater.domain.item.entity.ItemImage;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "매물 정보 응답")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record ItemSimpleResponse(
        @Schema(description = "매물 id", example = "52")
        @JsonProperty("id")
        Long id,

        @Schema(description = "글 작성 시간", example = "2024-01-01T12:34:56")
        @JsonProperty("createdAt")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,

        @Schema(description = "글 제목", example = "서울 강남구 아파트")
        @JsonProperty("title")
        String title,

        @Schema(description = "글 본문", example = "서울 강남구에 위치한 넓고 쾌적한 아파트입니다.")
        @JsonProperty("content")
        String content,

        @Schema(description = "게시글 썸네일 이미지")
        @JsonProperty("thumbnail")
        ItemImageResponse thumbnail,

//        @Schema(description = "유저 이미지", example = "https://example.com/profile.jpg")
//        @JsonProperty("userImage")
//        String userImageUrl,

        @Schema(description = "입주 가능 날짜", example = "2024-01-01")
        @JsonProperty("moveInDate")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate moveInDate,

        @Schema(description = "거래 완료 여부", example = "true")
        @JsonProperty("isSold")
        Boolean isSold
) {

    public static ItemSimpleResponse fromEntity(Item item) {
        ItemImageResponse thumbnail = item.getItemImages().stream()
                .filter(ItemImage::getIsThumbnail)
                .findFirst()
                .map(ItemImageResponse::fromEntity)
                .orElse(null);

//        String userImageUrl = (item.getUser().getUserImage() != null)
//                ? item.getUser().getUserImage().getUrl()
//                : null;

        return new ItemSimpleResponse(
                item.getId(),
                item.getCreatedAt(),
                item.getTitle(),
                item.getContent(),
                thumbnail,
//                userImageUrl,
                item.getMoveInDate(),
                item.getIsSold()
        );
    }
}