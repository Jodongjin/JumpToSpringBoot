package com.example.sbb.question;

import com.example.sbb.answer.AnswerForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor // final 속성을 포함하는 생성자 자동 생성
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        /**
         * Model: 자바 클래스와 템플릿의 연결고리
         * page: 첫 페이지 번호 0
          */
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    @GetMapping(value = "/detail/{id}") // value 생각 가능
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        /**
         * QuestionForm: question_form.html에서 QuestionForm 객체를 요구하기 때문에 GET method에서도 전달해야 함
         * 매개변수로 바인딩 한 객체는 Model 객체로 전달하지 않아도 템플릿에서 사용 가능
         */
        return "question_form";
    }

    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        /**
         * subject, content 항목을 지닌 form이 전송되면 자동으로 바인딩 됨
         * BindingResult: @Valid annotation으로 인해 검증이 수행된 결과가 담긴 객체
         * BindingResult 매개변수는 항상 @Valid 매개변수 바로 뒤에 위치해야 함
         */
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        this.questionService.create(questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/list";
    }
}
