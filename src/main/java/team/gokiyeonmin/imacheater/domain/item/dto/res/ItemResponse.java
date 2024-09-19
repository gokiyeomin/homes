package team.gokiyeonmin.imacheater.domain.item.dto.res;

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
public record ItemResponse(
        @Schema(description = "매물 id", example = "52")
        @JsonProperty("id")
        Long id,

        @Schema(description = "글 제목", example = "서울 강남구 아파트")
        @JsonProperty("title")
        String title,

        @Schema(description = "글 본문", example = "서울 강남구에 위치한 넓고 쾌적한 아파트입니다.")
        @JsonProperty("content")
        String content,

        @Schema(description = "게시글 썸네일 이미지", example = "https://example.com/thumbnail.jpg")
        @JsonProperty("thumbnail")
        String thumbnail,

        @Schema(description = "유저 이미지", example = "https://example.com/profile.jpg")
        @JsonProperty("userImage")
        String userImageUrl,

        @Schema(description = "이사 가능 날짜", example = "2024-01-01")
        @JsonProperty("moveInDate")
        LocalDate moveInDate,

        @Schema(description = "거래 완료 여부", example = "true")
        @JsonProperty("isSold")
        Boolean isSold,

        @Schema(description = "글 작성 시간", example = "2024-01-01T12:34:56")
        @JsonProperty("createdAt")
        LocalDateTime createdAt
) {

    public static ItemResponse fromEntity(Item item) {
        String thumbnailUrl = item.getItemImages().stream()
                .filter(ItemImage::getIsThumbnail)  // isThumbnail이 true인 이미지 필터링
                .findFirst()  // 첫 번째 이미지 찾기
                .map(ItemImage::getUrl)  // 이미지 URL 추출
                .orElse(null);  // 없다면 null

        // 유저 프로필 이미지 URL
        String userImageUrl = (item.getUser().getUserImage() != null)
                ? item.getUser().getUserImage().getUrl()
                : null;

        return new ItemResponse(
                item.getId(),
                item.getTitle(),
                item.getContent(),
                thumbnailUrl,
                userImageUrl,
                item.getMoveInDate(),
                item.getIsSold(),
                item.getCreatedAt()
        );
    }
}