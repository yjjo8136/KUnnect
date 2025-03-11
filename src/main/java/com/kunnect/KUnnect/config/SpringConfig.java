package com.kunnect.KUnnect.config;

import com.kunnect.KUnnect.repository.*;
import com.kunnect.KUnnect.service.UniversityService;
import com.kunnect.KUnnect.service.UserService;
import com.kunnect.KUnnect.util.JwtUtil;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringConfig {

    private final EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository(), jwtUtil(), universityRepository(), interestedUniversityRepository());
    }

    @Bean
    public InterestedUniversityRepository interestedUniversityRepository() {
        return new JpaInterestedUniversityRepository(em);
    }

    @Bean
    public UserRepository userRepository() {
        return new JpaUserRepository(em);
    }

    @Bean
    public UniversityService universityService() {
        return new UniversityService(universityRepository());
    }

    @Bean
    public UniversityRepository universityRepository() {
        return new JpaUniversityRepository(em);
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }


}
