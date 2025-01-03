package com.example.sbb.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.sbb.question.Question;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {
	private final AnswerRepository answerRepository;
	
	public void create(Question question, String content) {
		Answer answer = new Answer();
		answer.setQuestion(question);
		answer.setContent(content);
		answer.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(answer);
	}
}
