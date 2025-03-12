package com.kunnect.KUnnect.service;

import com.kunnect.KUnnect.domain.InterestedUniversity;
import com.kunnect.KUnnect.domain.University;
import com.kunnect.KUnnect.domain.User;
import com.kunnect.KUnnect.repository.InterestedUniversityRepository;
import com.kunnect.KUnnect.repository.UniversityRepository;
import com.kunnect.KUnnect.repository.UserRepository;
import com.kunnect.KUnnect.util.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UniversityRepository universityRepository;
    private final InterestedUniversityRepository interestedUniversityRepository;

    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil, UniversityRepository universityRepository, InterestedUniversityRepository interestedUniversityRepository) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.universityRepository = universityRepository;
        this.interestedUniversityRepository = interestedUniversityRepository;
    }

    public Long signUp(User user) {
        validateDuplicateUser(user);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // 비밀번호 암호화
        userRepository.save(user);
        return user.getId();
    }

    public String login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return jwtUtil.generateToken(email, user.getId()); // JWT 토큰 생성
            }
        }
        throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
    }

    public void validateDuplicateUser(User user) {
        userRepository.findByNickname(user.getNickname())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }


    // 🌟 관심 대학 추가
    public void addInterestedUniversity(Long userId, Long universityId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        University university = universityRepository.findById(universityId)
                .orElseThrow(() -> new IllegalArgumentException("대학을 찾을 수 없습니다."));

        // 이미 추가된 관심 대학인지 확인
        Optional<InterestedUniversity> existing = interestedUniversityRepository.findByUserAndUniversity(user, university);
        if (existing.isPresent()) {
            throw new IllegalStateException("이미 관심 대학으로 추가되었습니다.");
        }

        InterestedUniversity interestedUniversity = new InterestedUniversity(user, university);
        interestedUniversityRepository.save(interestedUniversity);
    }

    // 🌟 관심 대학 조회
    public List<University> getInterestedUniversities(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        return interestedUniversityRepository.findByUser(user)
                .stream()
                .map(InterestedUniversity::getUniversity)
                .collect(Collectors.toList());
    }

    // 🌟 관심 대학 삭제
    public void deleteInterestedUniversity(Long userId, Long universityId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        University university = universityRepository.findById(universityId)
                .orElseThrow(() -> new IllegalArgumentException("대학을 찾을 수 없습니다."));

        // 관심 대학으로 추가되지 않은 경우
        Optional<InterestedUniversity> existing = interestedUniversityRepository.findByUserAndUniversity(user, university);
        if (existing.isEmpty()) {
            throw new IllegalStateException("관심 대학으로 추가되지 않았습니다.");
        }

        interestedUniversityRepository.delete(existing.get());
    }
}
