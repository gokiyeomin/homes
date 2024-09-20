package team.gokiyeonmin.imacheater.domain.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.gokiyeonmin.imacheater.domain.chat.dto.req.ChatRoomCreateRequest;
import team.gokiyeonmin.imacheater.domain.chat.dto.res.ChatRoomCreateResponse;
import team.gokiyeonmin.imacheater.domain.chat.dto.res.ChatRoomListResponse;
import team.gokiyeonmin.imacheater.domain.chat.service.ChatRoomService;
import team.gokiyeonmin.imacheater.global.interceptor.annotation.UserId;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Operation(summary = "유저의 전체 채팅방 조회", description = "유저의 전체 채팅방을 조회합니다.")
    @GetMapping("/api/chat/rooms")
    public ResponseEntity<ChatRoomListResponse> getAllChatRooms(
            @UserId Long userId
    ) {
        ChatRoomListResponse response = chatRoomService.getAllChatRooms(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/chat/rooms")
    public ResponseEntity<ChatRoomCreateResponse> createChatRoom(
            @Parameter(hidden = true) @UserId Long userId,
            @Valid @RequestBody ChatRoomCreateRequest request
    ) {
        ChatRoomCreateResponse response = chatRoomService.createChatRoom(userId, request);
        return ResponseEntity.created(URI.create("/api/chat/rooms")).body(response);
    }
}
