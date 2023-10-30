package com.example.sbb;

import com.example.sbb.answer.Answer;
import com.example.sbb.answer.AnswerRepository;
import com.example.sbb.question.Question;
import com.example.sbb.question.QuestionRepository;
import com.example.sbb.question.QuestionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class SbbApplicationTests {

	@Autowired // Test의 경우, 생성자를 통한 DI가 불가능하므로 @Autowired 사용
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionService questionService;

	@Test
	void testJpa() {
		Question q1 = Question.builder()
				.subject("sbb가 무엇인가요?")
				.content("sbb에 대해서 알고 싶습니다.")
				.build();
		this.questionRepository.save(q1);

		Question q2 = Question.builder()
				.subject("스프링부트 모델 질문입니다.")
				.content("id는 자동으로 생성되나요?")
				.build();
		this.questionRepository.save(q2);
	}

	@Test
	void testJpa2() {
		List<Question> all = this.questionRepository.findAll();
		Assertions.assertEquals(2, all.size());

		Question q = all.get(0);
		Assertions.assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
 	void testJpa3() {
		Optional<Question> oq = this.questionRepository.findById(1);
		if(oq.isPresent()) {
			Question q = oq.get();
			Assertions.assertEquals("sbb가 무엇인가요?", q.getSubject());
		}
	}

	@Test
	void testJpa4() {
		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		Assertions.assertEquals(1, q.getId());
	}

	@Test
	void testJpa5() {
		Question q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		Assertions.assertEquals(1, q.getId());
	}

	@Test
	void testJpa6() {
		List<Question> questionList = this.questionRepository.findBySubjectLike("sbb%"); // sbb로 시작
		Question q = questionList.get(0);
		Assertions.assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	void testJpa7() {
		Optional<Question> oq = this.questionRepository.findById(1);
		Assertions.assertTrue(oq.isPresent());
		Question q = oq.get();
		q.editSubject("수정된 제목");
		this.questionRepository.save(q);
	}

	@Test
	void testJpa8() {
		Assertions.assertEquals(2, this.questionRepository.count());
		Optional<Question> oq = this.questionRepository.findById(1);
		Assertions.assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		Assertions.assertEquals(1, this.questionRepository.count());
	}

	@Test
	void testJpa9() {
		Optional<Question> oq = this.questionRepository.findById(2);
		Assertions.assertTrue(oq.isPresent());
		Question q = oq.get();

		Answer a = Answer.builder()
				.content("네 자동으로 생성됩니다.")
				.question(q)
				.build();
		this.answerRepository.save(a);
	}

	@Test
	void testJpa10() {
		Optional<Answer> oa = this.answerRepository.findById(1);
		Assertions.assertTrue(oa.isPresent());
		Answer a = oa.get();
		Assertions.assertEquals(2, a.getQuestion().getId());
	}

	@Test
	@Transactional // 메서드 종료까지 DB Session 유지
	void testJpa11() {
		Optional<Question> oq = this.questionRepository.findById(2); // 이 작업이 완료된 후 DB Session이 끊김
		Assertions.assertTrue(oq.isPresent());
		Question q = oq.get();

		List<Answer> answerList = q.getAnswerList(); // 따라서 여기서 Answer를 DB에서 가져오려고 할 때 에러

		Assertions.assertEquals(1, answerList.size());
		Assertions.assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());

		/**
		 * 필요한 시점에 데이터 가져오는 방식: LAZY
		 * 객체를 조회할 때 데이터를 모두 가져오는 방식: EAGER
		 * @OneToMany, @ManyToOne annotation 옵션으로 fetch=FetchType.LAZT & EAGER 설정 가능
		 */
	}

	@Test
	void createDummyData() {
		for(int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용무";
			this.questionService.create(subject, content, null);
		}
	}
}
