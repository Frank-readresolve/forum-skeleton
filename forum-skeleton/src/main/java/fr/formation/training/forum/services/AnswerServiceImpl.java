package fr.formation.training.forum.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import fr.formation.training.forum.dtos.AnswerAddDto;
import fr.formation.training.forum.dtos.AnswerUpdateDto;
import fr.formation.training.forum.dtos.IdentifierDto;
import fr.formation.training.forum.entities.Answer;
import fr.formation.training.forum.entities.Question;
import fr.formation.training.forum.repositories.AnswerJpaRepository;
import fr.formation.training.forum.repositories.QuestionJpaRepository;

@Service
public class AnswerServiceImpl extends AbstractService implements AnswerService {

	private final QuestionJpaRepository questions;

	private final AnswerJpaRepository answers;

	public AnswerServiceImpl(QuestionJpaRepository questions, AnswerJpaRepository answers) {
		this.questions = questions;
		this.answers = answers;
	}

	@Override
	public IdentifierDto add(AnswerAddDto dto) {
		Answer answer = getMapper().map(dto, Answer.class);
		answer.setAnswerDate(LocalDateTime.now());
		Question question = questions.getOne(dto.getQuestionId());
		answer.setQuestion(question);
		answers.save(answer);
		return new IdentifierDto(answer.getId());
	}

	@Override
	public void update(Long id, AnswerUpdateDto dto) {
		Answer answer = answers.findById(id).get();
		getMapper().map(dto, answer);
		answers.save(answer);
	}

}