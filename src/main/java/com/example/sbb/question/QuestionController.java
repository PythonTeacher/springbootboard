package com.example.sbb.question;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sbb.answer.AnswerForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
	
	private final QuestionService questionService;
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam(defaultValue="0") int page) {
		Page<Question> questionList = this.questionService.getList(page);
		model.addAttribute("paging", questionList);
		return "question_list";
	}
	
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable Integer id, AnswerForm answerForm) {
		Question q = this.questionService.getQuestion(id);
		model.addAttribute("question", q);
		return "question_detail";
	}
	
	@GetMapping("/create")
	public String createQuestionForm(QuestionForm questionForm) {
		return "question_form";
	}
	
	@PostMapping("/create")
//	public String createQuestion(@RequestParam String subject, @RequestParam String content) {
	public String createQuestion(@Valid QuestionForm questionForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "question_form";
		}
		this.questionService.createQuestion(questionForm.getSubject(), questionForm.getContent());
		return "redirect:/question/list";
	}
}
