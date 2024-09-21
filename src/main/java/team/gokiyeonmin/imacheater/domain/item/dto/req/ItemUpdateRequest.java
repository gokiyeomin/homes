package team.gokiyeonmin.imacheater.domain.item.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import team.gokiyeonmin.imacheater.domain.Direction;
import team.gokiyeonmin.imacheater.domain.item.Door;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "매물 정보 수정 요청")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record ItemUpdateRequest(
        @JsonProperty("title")
        @Schema(description = "매물 제목", example = "서울 강남구 아파트")
        @NotNull(message = "매물 제목은 필수입니다.")
        @NotBlank(message = "매물 제목은 필수입니다.")
        String title,

        @JsonProperty("content")
        @Schema(description = "매물 설명", example = "서울 강남구에 위치한 넓고 쾌적한 아파트입니다.")
        @NotNull(message = "매물 설명은 필수입니다.")
        @NotBlank(message = "매물 설명은 필수입니다.")
        String content,

        @JsonProperty("address")
        @Schema(description = "매물 주소", example = "서울특별시 강남구 테헤란로 123")
        @NotNull(message = "매물 주소는 필수입니다.")
        @NotBlank(message = "매물 주소는 필수입니다.")
        String address,

        @JsonProperty("deposit")
        @Schema(description = "보증금", example = "50000000")
        @NotNull(message = "보증금은 필수입니다.")
        Long deposit,

        @JsonProperty("rent")
        @Schema(description = "월세", example = "1500000")
        @NotNull(message = "월세는 필수입니다.")
        Long rent,

        @JsonProperty("maintenanceFeeIncluded")
        @Schema(description = "관리비 포함 여부", example = "true")
        @NotNull(message = "관리비 포함 여부는 필수입니다.")
        Boolean maintenanceFeeIncluded,

        @JsonProperty("moveInDate")
        @Schema(description = "입주 가능 날짜", example = "2024-01-01")
        @NotNull(message = "입주 가능 날짜는 필수입니다.")
        LocalDate moveInDate,

        @JsonProperty("expirationDate")
        @Schema(description = "계약 완료 날짜", example = "2025-01-01")
        @NotNull(message = "계약 완료 날짜는 필수입니다.")
        LocalDate expirationDate,

        @JsonProperty("door")
        @Schema(description = "가까운 문", example = "EAST")
        @NotNull(message = "가까운 문은 필수입니다.")
        Door door,

        @JsonProperty("floor")
        @Schema(description = "층수", example = "10")
        @NotNull(message = "층수는 필수입니다.")
        Long floor,

        @JsonProperty("roomCount")
        @Schema(description = "방 개수", example = "3")
        @NotNull(message = "방 개수는 필수입니다.")
        Long roomCount,

        @JsonProperty("windowDirection")
        @Schema(description = "창문 방향", example = "SOUTH")
        @NotNull(message = "창문 방향은 필수입니다.")
        Direction windowDirection,

        @JsonProperty("deletedImageUrls")
        @Schema(description = "삭제된 이미지 Url들", example = "[https://imageCdnAddress.com/folder/imageName.jpg]")
        List<Long> deletedImageIds
) {
}