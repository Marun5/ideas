package pl.marcin.ideas.question.service;

import org.springframework.stereotype.Component;
import pl.marcin.ideas.question.domain.model.Answer;
import pl.marcin.ideas.question.dto.AnswerDto;

@Component
public class AnswerMapper {

    public AnswerDto map(Answer answer) {
        AnswerDto answerDto = new AnswerDto();
        answerDto.setId(answer.getId());
        answerDto.setName(answer.getName());
        answerDto.setQuestionId(answer.getQuestion().getId());
        answerDto.setQuestionName(answer.getQuestion().getName());
        answerDto.setCategoryId(answer.getQuestion().getCategory().getId());
        answerDto.setCategoryName(answer.getQuestion().getCategory().getName());

        return answerDto;
    }
}
