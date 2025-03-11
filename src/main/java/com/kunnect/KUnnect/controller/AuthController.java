package com.kunnect.KUnnect.controller;

import com.kunnect.KUnnect.domain.User;
import com.kunnect.KUnnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody Map<String, String> request) {
        String nickname = request.get("nickname");
        String email = request.get("email");
        String password = request.get("password");

        User newUser = new User();
        newUser.setNickname(nickname);
        newUser.setEmail(email);
        newUser.setPassword(password);

        Long userId = userService.signUp(newUser);
        return ResponseEntity.ok(Map.of("message", "회원가입 성공!", "userId", userId));
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        String token = userService.login(email, password);
        return Map.of("token", token);
    }
}
