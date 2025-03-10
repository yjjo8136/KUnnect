package com.kunnect.KUnnect.service;

import com.kunnect.KUnnect.domain.University;
import com.kunnect.KUnnect.repository.UniversityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Transactional
public class UniversityService {

    private final UniversityRepository universityRepository;

    @Autowired
    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    public List<University> findUniversity() {
        return universityRepository.findAll();
    }

}
