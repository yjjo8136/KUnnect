package com.kunnect.KUnnect.repository;

import com.kunnect.KUnnect.domain.InterestedUniversity;
import com.kunnect.KUnnect.domain.University;
import com.kunnect.KUnnect.domain.User;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaInterestedUniversityRepository implements InterestedUniversityRepository {

    private final EntityManager em;

    public JpaInterestedUniversityRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<InterestedUniversity> findByUser(User user) {
        return em.createQuery("select iu from InterestedUniversity iu where iu.user = :user", InterestedUniversity.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public void save(InterestedUniversity interestedUniversity) {
        em.persist(interestedUniversity);
    }

    @Override
    public Optional<InterestedUniversity> findByUserAndUniversity(User user, University university) {
        return em.createQuery("select iu from InterestedUniversity iu where iu.user = :user and iu.university = :university", InterestedUniversity.class)
                .setParameter("user", user)
                .setParameter("university", university)
                .getResultList()
                .stream()
                .findAny();
    }

    @Override
    public void delete(InterestedUniversity interestedUniversity) {
        em.remove(interestedUniversity);
    }

}
