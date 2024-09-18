package team.gokiyeonmin.imacheater.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import team.gokiyeonmin.imacheater.global.interceptor.annotation.UserId;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    @PostMapping("/api/chat/room")
    public ResponseEntity<ChatRoomCreateResponse> createChatRoom(
            @UserId Long userId
    ) {

    }
}
