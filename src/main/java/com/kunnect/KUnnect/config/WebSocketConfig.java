package com.kunnect.KUnnect.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws") // ✅ WebSocket STOMP 엔드포인트
                .setAllowedOrigins("http://localhost:3000") // ✅ React 프론트엔드 도메인만 허용
                .withSockJS(); // ✅ SockJS 지원 (React 클라이언트와 호환)
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // ✅ 구독용 브로커 설정 (클라이언트가 메시지를 받을 경로)
        registry.setApplicationDestinationPrefixes("/app"); // ✅ 클라이언트가 메시지를 보낼 경로
    }
}
