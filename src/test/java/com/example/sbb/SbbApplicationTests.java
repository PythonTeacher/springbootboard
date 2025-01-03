package com.example.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.sbb.answer.Answer;
import com.example.sbb.answer.AnswerRepository;
import com.example.sbb.question.Question;
import com.example.sbb.question.QuestionRepository;
import com.example.sbb.question.QuestionService;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionService questionService;
	
//	@Test
	void createQuestion() {
		// 질문 생성
		Question q1 = new Question();
		q1.setSubject("질문제목1");
		q1.setContent("질문내용1");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);
		
		Question q2 = new Question();
		q2.setSubject("질문제목2");
		q2.setContent("질문내용2");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}
	
//	@Test
	void findAll() {
		List<Question> results = this.questionRepository.findAll();
		assertEquals(2, results.size());
		
		Question q = results.get(0);
		assertEquals("질문제목1", q.getSubject());
	}
	
//	@Test
	void findById() {
		Optional<Question> result = this.questionRepository.findById(2);
		if(result.isPresent()) {
			Question q = result.get();
			assertEquals("질문제목2", q.getSubject());
		}
	}
	
//	@Test
	void findBySubject() {
		Question result = this.questionRepository.findBySubject("질문제목1");
		assertEquals(1, result.getId());
	}
	
//	@Test
	void findBySubjectAndContent() {
		Question result = this.questionRepository.findBySubjectAndContent("질문제목1", "질문내용1");
		assertEquals(1, result.getId());
	}
	
//	@Test
	void findBySubjectLike() {
		List<Question> results = this.questionRepository.findBySubjectLike("%제목%");
		Question q = results.get(0);
		assertEquals(1, q.getId());
	}
	
//	@Test
	void updateQuestion() {
		Optional<Question> result = this.questionRepository.findById(1);
		assertTrue(result.isPresent());
		Question q = result.get();
		q.setSubject("질문제목1_수정");
		this.questionRepository.save(q);
	}
	
//	@Test
	void deleteQuestion() {
		assertEquals(2, this.questionRepository.count());
		Optional<Question> result = this.questionRepository.findById(1);
		assertTrue(result.isPresent());
		Question q = result.get();
		this.questionRepository.delete(q);
		assertEquals(1,  this.questionRepository.count());
	}
	
//	@Test
	void createAnswer() {
		Optional<Question> result = this.questionRepository.findById(2);
		assertTrue(result.isPresent());
		Question q = result.get();
		
		Answer a = new Answer();
		a.setQuestion(q);
		a.setContent("답변내용1");
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);
	}
	
//	@Test
	void findByIdAnswer() {
		Optional<Answer> result = this.answerRepository.findById(1);
		assertTrue(result.isPresent());
		Answer a = result.get();
		assertEquals(2, a.getQuestion().getId());
	}
	
//	@Transactional
//	@Test
	void getAnswerList() {
		Optional<Question> question = this.questionRepository.findById(2);
		assertTrue(question.isPresent());
		Question q = question.get();
		
		List<Answer> answerList = q.getAnswerList();
		assertEquals(1, answerList.size());
		assertEquals("답변내용1", answerList.get(0).getContent());
	}
	
	@Test
	void createManyQuestions() {
		for (int i = 1; i <= 100; i++) {
			String subject = String.format("테스트 데이터 %03d", i);
			String content = "내용없음";
			this.questionService.createQuestion(subject, content);
		}
	}
}
