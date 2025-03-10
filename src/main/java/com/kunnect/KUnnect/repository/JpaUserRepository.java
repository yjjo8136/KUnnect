package com.kunnect.KUnnect.repository;

import com.kunnect.KUnnect.domain.User;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaUserRepository implements UserRepository {

    private final EntityManager em;

    public JpaUserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
