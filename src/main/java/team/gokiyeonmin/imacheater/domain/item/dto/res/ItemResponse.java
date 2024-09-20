package team.gokiyeonmin.imacheater.domain.item.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import team.gokiyeonmin.imacheater.domain.Direction;
import team.gokiyeonmin.imacheater.domain.item.Door;
import team.gokiyeonmin.imacheater.domain.item.entity.Item;
import team.gokiyeonmin.imacheater.domain.item.entity.ItemImage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "매물 정보 응답")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record ItemResponse(
        @Schema(description = "매물 id", example = "52")
        @JsonProperty("id")
        Long id,

        @Schema(description = "글 작성 시간", example = "2024-01-01T12:34:56")
        @JsonProperty("createdAt")
        LocalDateTime createdAt,

        @Schema(description = "유저 이미지", example = "https://example.com/profile.jpg")
        @JsonProperty("userImage")
        String userImageUrl,

        @Schema(description = "유저 닉네임", example = "고기여민")
        @JsonProperty("nickname")
        String nickname,

        @Schema(description = "게시글 이미지 리스트")
        @JsonProperty("images")
        List<ItemImageResponse> images,

        @Schema(description = "글 제목", example = "서울 강남구 아파트")
        @JsonProperty("title")
        String title,

        @Schema(description = "글 본문", example = "서울 강남구에 위치한 넓고 쾌적한 아파트입니다.")
        @JsonProperty("content")
        String content,

        @Schema(description = "주소", example = "서울특별시 강남구 테헤란로 123")
        @JsonProperty("address")
        String address,

        @Schema(description = "보증금", example = "50000000")
        @JsonProperty("deposit")
        Long deposit,

        @Schema(description = "월세", example = "1500000")
        @JsonProperty("rent")
        Long rent,

        @Schema(description = "관리비 포함 여부", example = "true")
        @JsonProperty("maintenanceFeeIncluded")
        Boolean maintenanceFeeIncluded,

        @Schema(description = "입주 가능 날짜", example = "2024-01-01")
        @JsonProperty("moveInDate")
        LocalDate moveInDate,

        @Schema(description = "계약 완료 날짜", example = "2025-01-01")
        @JsonProperty("expirationDate")
        LocalDate expirationDate,

        @Schema(description = "가까운 문", example = "EAST")
        @JsonProperty("door")
        Door door,

        @Schema(description = "층수", example = "10")
        @JsonProperty("floor")
        Long floor,

        @Schema(description = "방 개수", example = "3")
        @JsonProperty("roomCount")
        Long roomCount,

        @Schema(description = "창문 방향", example = "SOUTH")
        @JsonProperty("windowDirection")
        Direction windowDirection,

        @Schema(description = "거래 완료 여부", example = "true")
        @JsonProperty("isSold")
        Boolean isSold
) {

    public static ItemResponse fromEntity(Item item) {
        List<ItemImageResponse> imageResponses = item.getItemImages().stream()
                .map(ItemImageResponse::fromEntity)
                .toList();

        // 유저 프로필 이미지 URL
        String userImageUrl = (item.getUser().getUserImage() != null)
                ? item.getUser().getUserImage().getUrl()
                : null;

        return new ItemResponse(
                item.getId(),
                item.getCreatedAt(),
                userImageUrl,
                item.getUser().getNickname(),
                imageResponses,
                item.getTitle(),
                item.getContent(),
                item.getAddress(),
                item.getDeposit(),
                item.getRent(),
                item.getMaintenanceFeeIncluded(),
                item.getMoveInDate(),
                item.getExpirationDate(),
                item.getDoor(),
                item.getFloor(),
                item.getRoomCount(),
                item.getWindowDirection(),
                item.getIsSold()
        );
    }
}