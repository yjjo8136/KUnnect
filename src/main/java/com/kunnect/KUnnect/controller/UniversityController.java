package com.kunnect.KUnnect.controller;

import com.kunnect.KUnnect.domain.University;
import com.kunnect.KUnnect.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/universities")
public class UniversityController {

    @Autowired
    private UniversityService universityService;

    @GetMapping
    public List<University> getUniversities(
            @RequestParam(name = "continent", required = false) String continent,
            @RequestParam(name = "country", required = false) String country,
            @RequestParam(name = "search", required = false) String search
    ) {
        return universityService.filterUniversity(continent, country, search);
    }

    // ✅ 특정 대학의 정보를 가져오는 API 추가
    @GetMapping("/{univId}")
    public University getUniversityById(@PathVariable Long univId) {
        return universityService.getUniversityById(univId)
                .orElseThrow(() -> new RuntimeException("해당 대학을 찾을 수 없습니다."));
    }

}