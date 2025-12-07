package com.team.cklob.gami.global.websocket;

import com.team.cklob.gami.global.security.exception.InvalidTokenException;
import com.team.cklob.gami.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (accessor.getCommand() == StompCommand.CONNECT) {
            if (!jwtProvider.validateAccessToken(accessor.getFirstNativeHeader("Authorization"))) {
                throw new InvalidTokenException();
            }
        }
        ChannelInterceptor.super.postSend(message, channel, sent);
    }
}
