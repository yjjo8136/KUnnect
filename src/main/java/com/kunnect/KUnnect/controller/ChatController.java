package com.kunnect.KUnnect.controller;

import com.kunnect.KUnnect.domain.ChatMessage;
import com.kunnect.KUnnect.repository.ChatMessageRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatMessageRepository chatMessageRepository;

    public ChatController(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    // ✅ 새로운 메시지 전송 및 저장
    @MessageMapping("/chat/{univId}")
    @SendTo("/topic/chat/{univId}")
    public ChatMessage sendMessage(ChatMessage message) {
        chatMessageRepository.save(message); // ✅ 메시지를 DB에 저장
        return message;
    }

    // ✅ 특정 대학의 채팅 기록 조회 API
    @GetMapping("/{univId}")
    public List<ChatMessage> getChatMessages(@PathVariable("univId") Long univId) {
        return chatMessageRepository.findByUnivIdOrderByTimestampAsc(univId);
    }
}
