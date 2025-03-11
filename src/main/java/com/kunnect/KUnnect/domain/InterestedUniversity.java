package com.kunnect.KUnnect.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "interested_univ") // 테이블 이름을 "interested_univ"로 설정
public class InterestedUniversity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // user_id를 외래키로 설정
    private User user;

    @ManyToOne
    @JoinColumn(name = "univ_id", nullable = false) // university_id를 외래키로 설정
    private University university;

    public InterestedUniversity() {}

    public InterestedUniversity(User user, University university) {
        this.user = user;
        this.university = university;
    }

    public Long getId() { return id; }

    public User getUser() { return user; }

    public University getUniversity() { return university; }
}
