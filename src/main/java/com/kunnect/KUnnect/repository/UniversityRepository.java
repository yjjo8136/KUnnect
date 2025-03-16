package com.kunnect.KUnnect.repository;

import com.kunnect.KUnnect.domain.University;

import java.util.List;
import java.util.Optional;

public interface UniversityRepository {
    University save(University university);

    List<University> filterByCountry(String country);

    List<University> findAll();

    Optional<University> findById(Long id);



}
