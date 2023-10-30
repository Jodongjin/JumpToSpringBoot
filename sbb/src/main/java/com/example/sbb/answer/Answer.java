package com.example.sbb.answer;

import com.example.sbb.question.Question;
import com.example.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private Question question;
    @ManyToOne
    private SiteUser author;
    @ManyToMany
    Set<SiteUser> voter;

    private LocalDateTime createDate;

    private LocalDateTime modityDate;

    @Builder // AllArgsConstructor 사용 지양하기 위해 생성자에 @Builder 붙이기
    public Answer(String content, Question question, SiteUser author) {
        this.content = content;
        this.question = question;
        this.author = author;
        this.createDate = LocalDateTime.now();
    }

    public void editContent(String content) {
        this.content = content;
    }

    public void setModityDate() {
        this.modityDate = LocalDateTime.now();
    }
}
