package team.gokiyeonmin.imacheater.global.interceptor.handler;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import team.gokiyeonmin.imacheater.global.security.domain.CustomUserDetail;
import team.gokiyeonmin.imacheater.global.security.service.CustomUserDetailService;
import team.gokiyeonmin.imacheater.global.util.JwtUtil;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketInterceptor implements ChannelInterceptor {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;

    @Override
    public Message<?> preSend(
            final Message<?> message,
            final MessageChannel channel
    ) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && accessor.getCommand() == StompCommand.CONNECT) {
            String authorizationHeader = accessor.getFirstNativeHeader(JwtUtil.AUTHORIZATION);
            log.info("WebSocketInterceptor Authorization Header: {}", authorizationHeader);
            String token = jwtUtil.extractToken(authorizationHeader);
            Claims claims = jwtUtil.parse(token);

            String username = claims.get(JwtUtil.USERNAME, String.class);

            CustomUserDetail customUserDetail = customUserDetailService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    customUserDetail,
                    null,
                    customUserDetail.getAuthorities());

            accessor.setUser(authenticationToken);
        }

        return message;
    }
}
