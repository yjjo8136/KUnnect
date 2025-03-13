package com.kunnect.KUnnect.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long univId; // 대학 ID
    private String sender;
    private String content;
    private String timestamp;

    public ChatMessage() {}

    public ChatMessage(Long univId, String sender, String content, String timestamp) {
        this.univId = univId;
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getter & Setter
    public Long getId() { return id; }
    public Long getUnivId() { return univId; }
    public String getSender() { return sender; }
    public String getContent() { return content; }
    public String getTimestamp() { return timestamp; }
    public void setId(Long id) { this.id = id; }
    public void setUnivId(Long univId) { this.univId = univId; }
    public void setSender(String sender) { this.sender = sender; }
    public void setContent(String content) { this.content = content; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
