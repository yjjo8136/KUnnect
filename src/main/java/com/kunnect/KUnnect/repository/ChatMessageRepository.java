package com.kunnect.KUnnect.repository;

import com.kunnect.KUnnect.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByUnivIdOrderByTimestampAsc(Long univId); // ✅ 특정 대학 채팅 메시지 조회
}
