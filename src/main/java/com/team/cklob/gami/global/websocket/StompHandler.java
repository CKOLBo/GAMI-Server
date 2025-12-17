package com.team.cklob.gami.global.websocket;

import com.team.cklob.gami.global.security.exception.InvalidTokenException;
import com.team.cklob.gami.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            authenticateStompUser(accessor);
        }

        if (StompCommand.SEND.equals(accessor.getCommand())) {
            authenticateStompUser(accessor);
        }

        return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
    }

    private void authenticateStompUser(StompHeaderAccessor accessor) {
        String token = accessor.getFirstNativeHeader("Authorization");

        if (!jwtProvider.validateAccessToken(token)) {
            throw new InvalidTokenException();
        }

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String email = jwtProvider.getEmail(token);
            accessor.setUser(new StompPrincipal(email));
        } else {
            throw new InvalidTokenException();
        }
    }
}

