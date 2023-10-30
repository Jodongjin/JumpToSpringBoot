package com.example.sbb.question;

import com.example.sbb.answer.Answer;
import com.example.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200) // 테이블 컬럼으로 인식하고 싶지 않을 때, @Transient 사용
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate; // 테이블 컬럼에는 create_date로 적용

    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) // mappedBy: 참조한 속성명
    private List<Answer> answerList;
    @ManyToOne
    private SiteUser author;
    @ManyToMany
    Set<SiteUser> voter;

    @Builder
    public Question(String subject, String content, SiteUser author) {
        this.subject = subject;
        this.content = content;
        this.author = author;
        this.createDate = LocalDateTime.now();
    }

    public void editSubject(String subject) {
        this.subject = subject;
    }

    public void editContent(String content) {
        this.content = content;
    }

    public void setModifyDate() {
        this.modifyDate = LocalDateTime.now();
    }
}
