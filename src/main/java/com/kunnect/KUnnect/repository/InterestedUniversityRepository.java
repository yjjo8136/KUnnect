package com.kunnect.KUnnect.repository;

import com.kunnect.KUnnect.domain.InterestedUniversity;
import com.kunnect.KUnnect.domain.User;
import com.kunnect.KUnnect.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterestedUniversityRepository {

    List<InterestedUniversity> findByUser(User user); // 특정 유저의 관심 대학 목록 조회

    Optional<InterestedUniversity> findByUserAndUniversity(User user, University university); // 특정 유저가 특정 대학을 관심 대학으로 추가했는지 확인

    void save(InterestedUniversity interestedUniversity);

    void delete(InterestedUniversity interestedUniversity);
}
