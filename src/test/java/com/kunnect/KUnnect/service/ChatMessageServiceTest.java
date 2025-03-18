package com.kunnect.KUnnect.service;

import com.kunnect.KUnnect.domain.ChatMessage;
import com.kunnect.KUnnect.repository.ChatMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatMessageServiceTest {

    @Mock
    private ChatMessageRepository chatMessageRepository;

    private ChatMessageService chatMessageService;

    @BeforeEach
    void setUp() {
        chatMessageService = new ChatMessageService(chatMessageRepository);
    }

    @Test
    void saveMessage_ShouldSaveAndReturnMessage() {
        // Given
        ChatMessage message = createChatMessage(1L, 1L, "user1", "Hello!");
        when(chatMessageRepository.save(any(ChatMessage.class))).thenReturn(message);

        // When
        ChatMessage savedMessage = chatMessageService.saveMessage(message);

        // Then
        assertNotNull(savedMessage);
        assertEquals(message.getContent(), savedMessage.getContent());
        verify(chatMessageRepository).save(message);
    }

    @Test
    void getMessagesByUnivId_ShouldReturnMessagesForUniversity() {
        // Given
        Long univId = 1L;
        ChatMessage message1 = createChatMessage(1L, univId, "user1", "Hello!");
        ChatMessage message2 = createChatMessage(2L, univId, "user2", "Hi there!");
        List<ChatMessage> messages = Arrays.asList(message1, message2);

        when(chatMessageRepository.findByUnivIdOrderByTimestampAsc(univId)).thenReturn(messages);

        // When
        List<ChatMessage> foundMessages = chatMessageService.getMessagesByUnivId(univId);

        // Then
        assertEquals(2, foundMessages.size());
        assertEquals("Hello!", foundMessages.get(0).getContent());
        assertEquals("Hi there!", foundMessages.get(1).getContent());
    }

    @Test
    void deleteMessage_ShouldDeleteMessage() {
        // Given
        Long messageId = 1L;

        // When
        chatMessageService.deleteMessage(messageId);

        // Then
        verify(chatMessageRepository).deleteById(messageId);
    }

    private ChatMessage createChatMessage(Long id, Long univId, String sender, String content) {
        ChatMessage message = new ChatMessage(univId, sender, content, String.valueOf(System.currentTimeMillis()));
        message.setId(id);
        return message;
    }
} 