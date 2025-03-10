package com.kunnect.KUnnect.service;

import com.kunnect.KUnnect.domain.User;
import com.kunnect.KUnnect.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long signUp(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    public void validateDuplicateUser(User user) {
        userRepository.findByNickname(user.getNickname())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId).get();
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

}
