package com.example.sbb.question;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    // findById, findAll 등은 기본 제공  findBySubject 등 다른 필드는 직접 정의
    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject); // 특정 문자열 포함 검색
    Page<Question> findAll(Pageable pageable); // 페이징 처리
    Page<Question> findAll(Specification<Question> spec, Pageable pageable);

    @Query("select distinct q from Question q "
    + "left outer join SiteUser u1 on q.author=u1 "
    + "left outer join Answer a on a.question=q "
    + "left outer join SiteUser u2 on a.author=u2 "
    + "where "
    + "q.subject like %:kw% "
    + "or q.content like %:kw% "
    + "or u1.username like %:kw% "
    + "or a.content like %:kw% "
    + "or u2.username like %:kw% ")
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
