package com.kunnect.KUnnect.controller;

import com.kunnect.KUnnect.domain.University;
import com.kunnect.KUnnect.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/universities")
public class UniversityController {

    @Autowired
    private UniversityService universityService;

    @GetMapping
    public List<University> getUniversities() {
        return universityService.findUniversity(); // ✅ JSON 반환
    }
}