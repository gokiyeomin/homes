package team.gokiyeonmin.imacheater.domain.chat.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import team.gokiyeonmin.imacheater.domain.chat.dto.req.ChatMessageSendRequest;
import team.gokiyeonmin.imacheater.domain.chat.service.ChatMessageService;
import team.gokiyeonmin.imacheater.global.security.domain.CustomUserDetail;

import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/receive/chat/{roomId}")
    public void sendMessage(
            @Parameter(hidden = true) StompHeaderAccessor accessor,
            @DestinationVariable Long roomId,
            ChatMessageSendRequest request
    ) {
        Long userId = getUserId(accessor);
        chatMessageService.createMessage(roomId, request, userId);
    }

    private Long getUserId(StompHeaderAccessor accessor) {
        CustomUserDetail user = (CustomUserDetail) ((UsernamePasswordAuthenticationToken) Objects.requireNonNull(accessor.getUser())).getPrincipal();
        return user.getUserId();
    }
}
