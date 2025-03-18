package com.kunnect.KUnnect.service;

import com.kunnect.KUnnect.domain.University;
import com.kunnect.KUnnect.repository.UniversityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniversityServiceTest {

    @Mock
    private UniversityRepository universityRepository;

    private UniversityService universityService;

    @BeforeEach
    void setUp() {
        universityService = new UniversityService(universityRepository);
    }

    @Test
    void findUniversity_ShouldReturnAllUniversities() {
        // Given
        University univ1 = createUniversity(1L, "Asia", "Korea", "Korea University");
        University univ2 = createUniversity(2L, "Europe", "France", "Sorbonne University");
        when(universityRepository.findAll()).thenReturn(Arrays.asList(univ1, univ2));

        // When
        List<University> universities = universityService.findUniversity();

        // Then
        assertEquals(2, universities.size());
        verify(universityRepository).findAll();
    }

    @Test
    void filterUniversity_WithContinent_ShouldFilterByContinent() {
        // Given
        University univ1 = createUniversity(1L, "Asia", "Korea", "Korea University");
        University univ2 = createUniversity(2L, "Europe", "France", "Sorbonne University");
        when(universityRepository.findAll()).thenReturn(Arrays.asList(univ1, univ2));

        // When
        List<University> filteredUniversities = universityService.filterUniversity("Asia", null, null);

        // Then
        assertEquals(1, filteredUniversities.size());
        assertEquals("Asia", filteredUniversities.get(0).getContinent());
    }

    @Test
    void filterUniversity_WithCountry_ShouldFilterByCountry() {
        // Given
        University univ1 = createUniversity(1L, "Asia", "Korea", "Korea University");
        University univ2 = createUniversity(2L, "Asia", "Japan", "Tokyo University");
        when(universityRepository.findAll()).thenReturn(Arrays.asList(univ1, univ2));

        // When
        List<University> filteredUniversities = universityService.filterUniversity(null, "Korea", null);

        // Then
        assertEquals(1, filteredUniversities.size());
        assertEquals("Korea", filteredUniversities.get(0).getCountry());
    }

    @Test
    void filterUniversity_WithSearch_ShouldFilterByName() {
        // Given
        University univ1 = createUniversity(1L, "Asia", "Korea", "Korea University");
        University univ2 = createUniversity(2L, "Europe", "France", "Sorbonne University");
        when(universityRepository.findAll()).thenReturn(Arrays.asList(univ1, univ2));

        // When
        List<University> filteredUniversities = universityService.filterUniversity(null, null, "Korea");

        // Then
        assertEquals(1, filteredUniversities.size());
        assertTrue(filteredUniversities.get(0).getUnivName().toLowerCase().contains("korea"));
    }

    @Test
    void filterUniversity_WithMultipleFilters_ShouldApplyAllFilters() {
        // Given
        University univ1 = createUniversity(1L, "Asia", "Korea", "Korea University");
        University univ2 = createUniversity(2L, "Asia", "Korea", "Seoul National University");
        University univ3 = createUniversity(3L, "Asia", "Japan", "Tokyo University");
        when(universityRepository.findAll()).thenReturn(Arrays.asList(univ1, univ2, univ3));

        // When
        List<University> filteredUniversities = universityService.filterUniversity("Asia", "Korea", "Korea");

        // Then
        assertEquals(1, filteredUniversities.size());
        assertEquals("Korea University", filteredUniversities.get(0).getUnivName());
    }

    @Test
    void getUniversityById_WithValidId_ShouldReturnUniversity() {
        // Given
        Long univId = 1L;
        University university = createUniversity(univId, "Asia", "Korea", "Korea University");
        when(universityRepository.findById(univId)).thenReturn(Optional.of(university));

        // When
        Optional<University> foundUniversity = universityService.getUniversityById(univId);

        // Then
        assertTrue(foundUniversity.isPresent());
        assertEquals(univId, foundUniversity.get().getUnivId());
    }

    @Test
    void getUniversityById_WithInvalidId_ShouldReturnEmpty() {
        // Given
        Long univId = 999L;
        when(universityRepository.findById(univId)).thenReturn(Optional.empty());

        // When
        Optional<University> foundUniversity = universityService.getUniversityById(univId);

        // Then
        assertFalse(foundUniversity.isPresent());
    }

    private University createUniversity(Long id, String continent, String country, String name) {
        University university = new University();
        university.setUnivId(id);
        university.setContinent(continent);
        university.setCountry(country);
        university.setUnivName(name);
        return university;
    }
} 