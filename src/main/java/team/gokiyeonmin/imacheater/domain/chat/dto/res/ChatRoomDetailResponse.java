package team.gokiyeonmin.imacheater.domain.chat.dto.res;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import team.gokiyeonmin.imacheater.domain.chat.domain.ChatType;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatMessage;
import team.gokiyeonmin.imacheater.domain.chat.entity.ChatMessageContent;
import team.gokiyeonmin.imacheater.domain.item.entity.Item;
import team.gokiyeonmin.imacheater.global.util.Pair;

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
            List<Pair<ChatMessage, ChatMessageContent>> chatMessagePairs
    ) {
        ItemDto itemDto = new ItemDto(item.getId(), item.getTitle());
        List<ChatMessageDto> messages = chatMessagePairs.stream()
                .map(pair -> {
                    ChatMessage chatMessage = pair.first();
                    ChatMessageContent chatMessageContent = pair.second();
                    return new ChatMessageDto(
                            chatMessage.getSender().getId(),
                            chatMessageContent.getType(),
                            chatMessageContent.getContent()
                    );
                })
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

            @Schema(description = "채팅 타입", example = "MESSAGE | IMAGE")
            ChatType type,

            @Schema(description = "채팅 내용", example = "메시지: 안녕하세요. | 이미지: url")
            String content
    ) {
    }
}
