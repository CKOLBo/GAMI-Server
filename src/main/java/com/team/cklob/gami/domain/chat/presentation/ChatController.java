package com.team.cklob.gami.domain.chat.presentation;

import com.team.cklob.gami.domain.chat.presentation.request.ChatMessageRequest;
import com.team.cklob.gami.domain.chat.presentation.response.ChatMessagePageResponse;
import com.team.cklob.gami.domain.chat.presentation.response.GetChatRoomListResponse;
import com.team.cklob.gami.domain.chat.presentation.response.GetChatRoomResponse;
import com.team.cklob.gami.domain.chat.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final GetChatRoomListService getChatRoomListService;
    private final GetChatRoomService getChatRoomService;
    private final ChatMessageService chatMessageService;
    private final GetChatRoomMessageService getChatRoomMessageService;
    private final LeaveChatRoomService  leaveChatRoomService;

    @GetMapping("/rooms")
    public ResponseEntity<List<GetChatRoomListResponse>> findAllRooms() {
        List<GetChatRoomListResponse> response = getChatRoomListService.execute();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<GetChatRoomResponse> findRoomDetail(@PathVariable Long roomId) {
        GetChatRoomResponse response = getChatRoomService.execute(roomId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/rooms/{roomId}/messages")
    public ResponseEntity<ChatMessagePageResponse> getChatRoomMessages(
            @PathVariable Long roomId,
            @RequestParam(required = false) Long cursor
    ) {
        ChatMessagePageResponse response = getChatRoomMessageService.execute(roomId, cursor);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/rooms/{roomId}/leave")
    public ResponseEntity<Void> leaveChatRoom(@PathVariable Long roomId) {
        leaveChatRoomService.execute(roomId);
        return ResponseEntity.noContent().build();
    }


    @MessageMapping("/rooms/{roomId}/send")
    public void sendMessage(
            @DestinationVariable Long roomId,
            @Payload ChatMessageRequest request,
            @Header("simpUser") Principal principal,
            SimpMessageHeaderAccessor headerAccessor) {

        Principal actualPrincipal = principal != null ? principal : headerAccessor.getUser();

        if (actualPrincipal == null) {
            return;
        }
        chatMessageService.execute(roomId, request, actualPrincipal);
    }
}
