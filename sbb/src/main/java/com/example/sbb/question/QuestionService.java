package com.example.sbb.question;

import com.example.sbb.DataNotFoundException;
import com.example.sbb.answer.Answer;
import com.example.sbb.user.SiteUser;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Page<Question> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate")); // 정렬 조건 추가
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // page: 페이지 번호, 10: 노출할 개수, Sort.by(sorts): 정렬을 위한 Sort 객체
        Specification<Question> spec = search(kw);
        return this.questionRepository.findAllByKeyword(kw, pageable);
    }

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, SiteUser user) {
        Question question = Question.builder()
                .subject(subject)
                .content(content)
                .author(user)
                .build();
        this.questionRepository.save(question);
    }

    public void modify(Question question, String subject, String content) {
        question.editSubject(subject);
        question.editContent(content);
        question.setModifyDate();
        this.questionRepository.save(question);
    }

    public void delete(Question question) {
        this.questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }

    private Specification<Question> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"),
                        cb.like(q.get("content"), "%" + kw + "%"),
                        cb.like(u1.get("username"), "%" + kw + "%"),
                        cb.like(a.get("content"), "%" + kw + "%"),
                        cb.like(u2.get("username"), "%" + kw + "%"));
            }
        };
    }
}
