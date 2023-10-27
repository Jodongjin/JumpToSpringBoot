package com.example.sbb.question;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    // findById, findAll 등은 기본 제공  findBySubject 등 다른 필드는 직접 정의
    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject); // 특정 문자열 포함 검색
    Page<Question> findAll(Pageable pageable); // 페이징 처리
}
