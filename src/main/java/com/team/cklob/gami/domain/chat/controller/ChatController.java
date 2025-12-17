package com.team.cklob.gami.domain.chat.controller;

import com.team.cklob.gami.domain.chat.dto.request.ChatMessageRequest;
import com.team.cklob.gami.domain.chat.dto.response.ChatMessagePageResponse;
import com.team.cklob.gami.domain.chat.dto.response.GetChatRoomListResponse;
import com.team.cklob.gami.domain.chat.dto.response.GetChatRoomResponse;
import com.team.cklob.gami.domain.chat.service.*;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Chat", description = "채팅 REST API")
@SecurityRequirement(name = "Bearer")
public class ChatController {

    private final GetChatRoomListService getChatRoomListService;
    private final GetChatRoomService getChatRoomService;
    private final ChatMessageService chatMessageService;
    private final GetChatRoomMessageService getChatRoomMessageService;
    private final LeaveChatRoomService leaveChatRoomService;

    @Operation(
            summary = "채팅방 목록 조회",
            description = "사용자가 참여 중인 채팅방 목록을 조회한다"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "채팅방 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = GetChatRoomListResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content)
    })
    @GetMapping("/rooms")
    public ResponseEntity<List<GetChatRoomListResponse>> findAllRooms() {
        List<GetChatRoomListResponse> response = getChatRoomListService.execute();
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "채팅방 상세 조회",
            description = "채팅방 ID로 채팅방 상세 정보를 조회한다"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "채팅방 상세 조회 성공",
                    content = @Content(schema = @Schema(implementation = GetChatRoomResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content),
            @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없음", content = @Content)
    })
    @GetMapping("/{roomId}")
    public ResponseEntity<GetChatRoomResponse> findRoomDetail(
            @Parameter(description = "채팅방 ID", example = "1")
            @PathVariable Long roomId
    ) {
        GetChatRoomResponse response = getChatRoomService.execute(roomId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "채팅 메시지 조회",
            description = "채팅방의 메시지를 커서 기반 페이징으로 조회한다"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "채팅 메시지 조회 성공",
                    content = @Content(schema = @Schema(implementation = ChatMessagePageResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content),
            @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없음", content = @Content)
    })
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<ChatMessagePageResponse> getChatRoomMessages(
            @Parameter(description = "채팅방 ID", example = "1")
            @PathVariable Long roomId,
            @Parameter(description = "커서 ID (마지막 메시지 ID)", example = "100")
            @RequestParam(required = false) Long cursor
    ) {
        ChatMessagePageResponse response = getChatRoomMessageService.execute(roomId, cursor);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "채팅방 나가기",
            description = "채팅방에서 나간다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "채팅방 나가기 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content),
            @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없음", content = @Content)
    })
    @DeleteMapping("/rooms/{roomId}/leave")
    public ResponseEntity<Void> leaveChatRoom(
            @Parameter(description = "채팅방 ID", example = "1")
            @PathVariable Long roomId
    ) {
        leaveChatRoomService.execute(roomId);
        return ResponseEntity.noContent().build();
    }

    @Hidden
    @MessageMapping("/rooms/{roomId}/send")
    public void sendMessage(
            @DestinationVariable Long roomId,
            @Payload ChatMessageRequest request,
            @Header("simpUser") Principal principal,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        Principal actualPrincipal = principal != null ? principal : headerAccessor.getUser();

        if (actualPrincipal == null) {
            return;
        }

        chatMessageService.execute(roomId, request, actualPrincipal);
    }
}
