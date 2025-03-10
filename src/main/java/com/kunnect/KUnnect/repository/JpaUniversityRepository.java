package com.kunnect.KUnnect.repository;

import com.kunnect.KUnnect.domain.University;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaUniversityRepository implements UniversityRepository {

    private final EntityManager em;

    public JpaUniversityRepository(EntityManager em) {
        this.em = em;
    }
    @Override
    public University save(University university) {
        em.persist(university);
        return university;
    }

    @Override
    public List<University> filterByCountry(String country) {
        return null;
    }

    @Override
    public List<University> findAll() {
        return em.createQuery("select u from University u", University.class)
                .getResultList();
    }
}
