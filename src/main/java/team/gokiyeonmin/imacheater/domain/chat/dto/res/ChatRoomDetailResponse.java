package team.gokiyeonmin.imacheater.domain.chat.dto.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatMessage;
import team.gokiyeonmin.imacheater.domain.item.entity.Item;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "채팅방 상세 조회 응답")
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record ChatRoomDetailResponse(
        @Schema(description = "매물 정보")
        ItemDto item,

        @Schema(description = "채팅 메시지 리스트")
        List<ChatMessageDto> messages

) {

    public static ChatRoomDetailResponse fromEntity(
            Item item,
            List<ChatMessage> chatMessages
    ) {
        ItemDto itemDto = new ItemDto(item.getId(), item.getTitle());
        List<ChatMessageDto> messages = chatMessages.stream()
                .map(chatMessage -> new ChatMessageDto(
                        chatMessage.getUser().getId(),
                        chatMessage.getContent(),
                        chatMessage.getCreatedAt()
                ))
                .toList();

        return new ChatRoomDetailResponse(itemDto, messages);
    }

    record ItemDto(
            @Schema(description = "매물 ID", example = "1")
            Long id,

            @Schema(description = "매물 제목", example = "[나가는 입장] 경대북문 1분 거리")
            String title
    ) {
    }


    @Schema(description = "채팅 메시지")
    @JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
    record ChatMessageDto(
            @Schema(description = "채팅 보낸 유저 ID", example = "1")
            Long userId,

            @Schema(description = "채팅 내용", example = "메시지: 안녕하세요.")
            String content,

            @Schema(description = "보낸 시각", example = "2021-08-01T00:00:00")
            LocalDateTime createdAt
    ) {
    }
}
