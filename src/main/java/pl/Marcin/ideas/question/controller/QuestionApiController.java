package pl.marcin.ideas.question.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.marcin.ideas.question.domain.model.Answer;
import pl.marcin.ideas.question.domain.model.Question;
import pl.marcin.ideas.question.service.AnswerService;
import pl.marcin.ideas.question.service.QuestionService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/questions")
public class QuestionApiController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping
    List<Question> getQuestions() {
        return questionService.getQuestions();
    }

    @GetMapping("{id}")
    Question getQuestion(@PathVariable UUID id) {
        return questionService.getQuestion(id);
    }

    @GetMapping("{id}/answers")
    List<Answer> findAllByQuestionId(@PathVariable UUID id) {
        return answerService.findAllByQuestionId(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Question updateQuestion(@PathVariable UUID id, @RequestBody Question question) {
        return questionService.updateQuestion(id, question);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteQuestion(@PathVariable UUID id) {
        questionService.deleteQuestion(id);
    }

    @PostMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    Answer createAnswer(@PathVariable UUID id, @RequestBody Answer answer) {
        return answerService.createAnswer(id, answer);
    }

}
