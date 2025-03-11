package com.kunnect.KUnnect.controller;

import com.kunnect.KUnnect.domain.University;
import com.kunnect.KUnnect.domain.User;
import com.kunnect.KUnnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/{userId}/interests/{universityId}")
    public ResponseEntity<?> addInterestedUniversity(
            @PathVariable("userId") Long userId,  // 변수 이름 명시
            @PathVariable("universityId") Long universityId) { // 변수 이름 명시
        userService.addInterestedUniversity(userId, universityId);
        return ResponseEntity.ok("관심 대학에 추가되었습니다.");
    }

    @GetMapping("/{userId}/interests")
    public ResponseEntity<List<University>> getInterestedUniversities(@PathVariable("userId") Long userId) {
        List<University> universities = userService.getInterestedUniversities(userId);
        return ResponseEntity.ok(universities);
    }

}
