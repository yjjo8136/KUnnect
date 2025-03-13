package com.kunnect.KUnnect.config;

import com.kunnect.KUnnect.util.JwtUtil;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.filter.GenericFilterBean;

import java.util.Collections;
import java.util.Map;

public class WebSocketAuthenticator implements HandshakeInterceptor {

    private final JwtUtil jwtUtil;

    public WebSocketAuthenticator(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        //logger.debug("beforeHandshake");
        if (request instanceof ServletServerHttpRequest servletRequest) {
            String authHeader = servletRequest.getServletRequest().getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7); // "Bearer " 제거 후 토큰만 추출

                if (jwtUtil.validateToken(token)) {
                    String email = jwtUtil.extractEmail(token);
                    Long userId = jwtUtil.extractUserId(token);

                    // Spring Security Authentication 설정
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            email, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    attributes.put("userId", userId);
                    return true; // WebSocket 연결 허용
                }
            }
        }
        return false; // 인증 실패 시 WebSocket 연결 거부
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        // WebSocket 핸드셰이크 이후 특별한 작업 필요 없음
    }
}
