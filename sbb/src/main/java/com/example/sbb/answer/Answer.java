package com.example.sbb.answer;

import com.example.sbb.question.Question;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    private LocalDateTime createDate;

    @Builder // AllArgsConstructor 사용 지양하기 위해 생성자에 @Builder 붙이기
    public Answer(String content, Question question) {
        this.content = content;
        this.question = question;
        this.createDate = LocalDateTime.now();
    }
}
