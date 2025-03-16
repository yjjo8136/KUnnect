package com.kunnect.KUnnect.service;

import com.kunnect.KUnnect.domain.University;
import com.kunnect.KUnnect.repository.UniversityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<University> filterUniversity(String continent, String country, String search) {
        List<University> universities = universityRepository.findAll();

        // 대륙 필터링
        if (continent != null && !continent.isEmpty()) {
            universities = universities.stream()
                    .filter(u -> u.getContinent().equalsIgnoreCase(continent))
                    .collect(Collectors.toList());
        }

        // 국가 필터링
        if (country != null && !country.isEmpty()) {
            universities = universities.stream()
                    .filter(u -> u.getCountry().equalsIgnoreCase(country))
                    .collect(Collectors.toList());
        }

        // 검색 (대학 이름)
        if (search != null && !search.isEmpty()) {
            universities = universities.stream()
                    .filter(u -> u.getUnivName().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        return universities;
    }

    public Optional<University> getUniversityById(Long univId) {
        return universityRepository.findById(univId);
    }
}
