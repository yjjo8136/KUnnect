package com.kunnect.KUnnect;

import com.kunnect.KUnnect.repository.JpaUniversityRepository;
import com.kunnect.KUnnect.repository.JpaUserRepository;
import com.kunnect.KUnnect.repository.UniversityRepository;
import com.kunnect.KUnnect.repository.UserRepository;
import com.kunnect.KUnnect.service.UniversityService;
import com.kunnect.KUnnect.service.UserService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringConfig {

    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository());
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
}

