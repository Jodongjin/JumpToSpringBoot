package com.example.sbb.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class QuestionForm {
    @NotEmpty(message = "제목은 필수항목입니다.")
    @Size(max = 200)
    private String subject;

    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;

    @Builder
    public QuestionForm(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    public void editSubject(String subject) {
        this.subject = subject;
    }

    public void editContent(String content) {
        this.content = content;
    }
}
