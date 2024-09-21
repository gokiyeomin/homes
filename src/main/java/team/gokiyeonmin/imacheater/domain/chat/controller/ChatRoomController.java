package team.gokiyeonmin.imacheater.domain.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.gokiyeonmin.imacheater.domain.chat.dto.req.ChatRoomIdRequest;
import team.gokiyeonmin.imacheater.domain.chat.dto.res.ChatRoomDetailResponse;
import team.gokiyeonmin.imacheater.domain.chat.dto.res.ChatRoomIdResponse;
import team.gokiyeonmin.imacheater.domain.chat.dto.res.ChatRoomsResponse;
import team.gokiyeonmin.imacheater.domain.chat.service.ChatRoomService;
import team.gokiyeonmin.imacheater.global.interceptor.annotation.UserId;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Operation(summary = "채팅방 ID 조회", description = "채팅방 ID를 조회합니다. 존재히자 읺는다면, 새로 생성합니다.")
    @PostMapping("/api/chat/rooms")
    public ResponseEntity<ChatRoomIdResponse> getChatRoomId(
            @Parameter(hidden = true) @UserId Long customerId,
            @Valid @RequestBody ChatRoomIdRequest request
    ) {
        ChatRoomIdResponse response = chatRoomService.getChatRoomId(customerId, request);
        return ResponseEntity.created(URI.create("/api/chat/rooms")).body(response);
    }

    @Operation(summary = "유저의 전체 채팅방 조회", description = "유저의 전체 채팅방을 조회합니다.")
    @GetMapping("/api/chat/rooms")
    public ResponseEntity<ChatRoomsResponse> getAllChatRooms(
            @UserId Long userId
    ) {
        ChatRoomsResponse response = chatRoomService.getAllChatRooms(userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "채팅방 상세 조회 (입장)", description = "채팅방을 조회합니다.")
    @GetMapping("/api/chat/rooms/{roomId}")
    public ResponseEntity<ChatRoomDetailResponse> getChatRoomDetail(
            @UserId Long userId,
            @PathVariable Long roomId
    ) {
        ChatRoomDetailResponse response = chatRoomService.getChatRoomDetail(userId, roomId);
        return ResponseEntity.ok(response);
    }
}
