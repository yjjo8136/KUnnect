package com.kunnect.KUnnect;

import com.kunnect.KUnnect.domain.InterestedUniversity;
import com.kunnect.KUnnect.domain.University;
import com.kunnect.KUnnect.domain.User;
import com.kunnect.KUnnect.repository.InterestedUniversityRepository;
import com.kunnect.KUnnect.repository.UniversityRepository;
import com.kunnect.KUnnect.repository.UserRepository;
import com.kunnect.KUnnect.service.UserService;
import com.kunnect.KUnnect.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UniversityRepository universityRepository;
    @Mock
    private InterestedUniversityRepository interestedUniversityRepository;
    @Mock
    private JwtUtil jwtUtil;

    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepository, jwtUtil, universityRepository, interestedUniversityRepository);
    }

    @Test
    void signUp_WithValidUser_ShouldCreateUser() {
        // Given
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setNickname("testUser");
        
        when(userRepository.findByNickname(any())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L); // Set ID to simulate DB save
            return savedUser;
        });

        // When
        Long userId = userService.signUp(user);

        // Then
        assertNotNull(userId);
        assertEquals(2L, userId);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void signUp_WithDuplicateNickname_ShouldThrowException() {
        // Given
        User user = new User();
        user.setNickname("existingUser");
        
        when(userRepository.findByNickname("existingUser")).thenReturn(Optional.of(new User()));

        // When & Then
        assertThrows(IllegalStateException.class, () -> userService.signUp(user));
    }

    @Test
    void login_WithValidCredentials_ShouldReturnToken() {
        // Given
        String email = "test@example.com";
        String password = "password123";
        String hashedPassword = passwordEncoder.encode(password);
        String expectedToken = "dummy.jwt.token";

        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setId(1L);
        user.setNickname("testUser");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(eq(email), eq(1L), eq("testUser"))).thenReturn(expectedToken);

        // When
        String token = userService.login(email, password);

        // Then
        assertEquals(expectedToken, token);
    }

    @Test
    void login_WithInvalidCredentials_ShouldThrowException() {
        // Given
        String email = "test@example.com";
        String password = "wrongPassword";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> userService.login(email, password));
    }

    @Test
    void findUser_WithValidId_ShouldReturnUser() {
        // Given
        Long userId = 1L;
        User expectedUser = new User();
        expectedUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));

        // When
        User foundUser = userService.findUser(userId);

        // Then
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
    }

    @Test
    void findUser_WithInvalidId_ShouldThrowException() {
        // Given
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> userService.findUser(userId));
    }

    @Test
    void addInterestedUniversity_ShouldAddUniversity() {
        // Given
        Long userId = 1L;
        Long univId = 1L;
        
        User user = new User();
        user.setId(userId);
        
        University university = new University();
        university.setUnivId(univId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(universityRepository.findById(univId)).thenReturn(Optional.of(university));
        when(interestedUniversityRepository.findByUserAndUniversity(user, university))
            .thenReturn(Optional.empty());

        // When
        userService.addInterestedUniversity(userId, univId);

        // Then
        verify(interestedUniversityRepository).save(any(InterestedUniversity.class));
    }

    @Test
    void addInterestedUniversity_WithDuplicate_ShouldThrowException() {
        // Given
        Long userId = 1L;
        Long univId = 1L;
        
        User user = new User();
        user.setId(userId);
        
        University university = new University();
        university.setUnivId(univId);

        InterestedUniversity existingInterest = new InterestedUniversity(user, university);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(universityRepository.findById(univId)).thenReturn(Optional.of(university));
        when(interestedUniversityRepository.findByUserAndUniversity(user, university))
            .thenReturn(Optional.of(existingInterest));

        // When & Then
        assertThrows(IllegalStateException.class, 
            () -> userService.addInterestedUniversity(userId, univId));
    }

    @Test
    void getInterestedUniversities_ShouldReturnList() {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        University univ1 = new University();
        University univ2 = new University();
        
        InterestedUniversity interest1 = new InterestedUniversity(user, univ1);
        InterestedUniversity interest2 = new InterestedUniversity(user, univ2);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(interestedUniversityRepository.findByUser(user))
            .thenReturn(Arrays.asList(interest1, interest2));

        // When
        List<University> universities = userService.getInterestedUniversities(userId);

        // Then
        assertEquals(2, universities.size());
    }

    @Test
    void deleteInterestedUniversity_ShouldDeleteUniversity() {
        // Given
        Long userId = 1L;
        Long univId = 1L;
        
        User user = new User();
        user.setId(userId);
        
        University university = new University();
        university.setUnivId(univId);

        InterestedUniversity existingInterest = new InterestedUniversity(user, university);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(universityRepository.findById(univId)).thenReturn(Optional.of(university));
        when(interestedUniversityRepository.findByUserAndUniversity(user, university))
            .thenReturn(Optional.of(existingInterest));

        // When
        userService.deleteInterestedUniversity(userId, univId);

        // Then
        verify(interestedUniversityRepository).delete(existingInterest);
    }
} 