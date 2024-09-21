package team.gokiyeonmin.imacheater.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import team.gokiyeonmin.imacheater.domain.chat.dto.req.ChatMessageSendRequest;
import team.gokiyeonmin.imacheater.domain.chat.service.ChatMessageService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/receive/chat/{roomId}")
    public void sendMessage(
            @DestinationVariable Long roomId,
            ChatMessageSendRequest request
    ) {
        chatMessageService.createMessage(roomId, request);
    }
}
