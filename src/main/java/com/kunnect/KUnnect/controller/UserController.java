package com.kunnect.KUnnect.controller;

import com.kunnect.KUnnect.domain.User;
import com.kunnect.KUnnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/signup")
    public String signupForm() {
        return "users/signupForm";
    }

    @PostMapping("/users/signup")
    public String signup(UserForm form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        user.setNickname(form.getNickname());

        userService.signUp(user);

        return "redirect:/";
    }

}
