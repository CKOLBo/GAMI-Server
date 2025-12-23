package com.team.cklob.gami.global.websocket;

import com.team.cklob.gami.global.security.exception.InvalidTokenException;
import com.team.cklob.gami.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtProvider jwtProvider;
    private static final String USER_EMAIL_KEY = "USER_EMAIL";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();

        try {
            if (StompCommand.CONNECT.equals(command)) {
                String email = authenticateAndGetEmail(accessor);

                accessor.setUser(new StompPrincipal(email));

                accessor.getSessionAttributes().put(USER_EMAIL_KEY, email);

            }
            else if (StompCommand.SUBSCRIBE.equals(command) ||
                    StompCommand.SEND.equals(command)) {

                String email = (String) accessor.getSessionAttributes().get(USER_EMAIL_KEY);

                if (email == null) {
                    throw new InvalidTokenException();
                }

                if (accessor.getUser() == null) {
                    accessor.setUser(new StompPrincipal(email));
                }

            }
        } catch (InvalidTokenException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidTokenException();
        }

        return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
    }

    private String authenticateAndGetEmail(StompHeaderAccessor accessor) {
        String authHeader = accessor.getFirstNativeHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException();
        }

        String token = authHeader.substring(7);

        if (!jwtProvider.validateAccessToken(token)) {
            throw new InvalidTokenException();
        }

        String email = jwtProvider.getEmail(token);
        return email;
    }
}