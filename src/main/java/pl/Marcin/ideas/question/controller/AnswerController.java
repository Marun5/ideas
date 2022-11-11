package pl.Marcin.ideas.question.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.Marcin.ideas.question.domain.model.Answer;
import pl.Marcin.ideas.question.service.AnswerService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("questions/{question-id}/answers")
public class AnswerController {

    private final AnswerService answerService;

    @GetMapping
    List<Answer> findAllByQuestionId(@PathVariable("question-id") UUID questionId) {
        return answerService.findAllByQuestionId(questionId);
    }

    @GetMapping("{answer-id}")
    Answer getAnswer(@PathVariable("question-id") UUID questionId, @PathVariable("answer-id") UUID answerId) {
        return answerService.getAnswer(answerId);
    }

    @PostMapping
    Answer createAnswer(@PathVariable("question-id") UUID questionId, @RequestBody Answer answer) {
        return answerService.createAnswer(questionId, answer);
    }

    @PutMapping("{answer-id}")
    Answer updateAnswer(@PathVariable("question-id") UUID questionId,
                        @PathVariable("answer-id") UUID answerId,
                        @RequestBody Answer answer) {

        return answerService.updateAnswer(answerId, answer);
    }

    @DeleteMapping("{answer-id}")
    void deleteAnswer(@PathVariable("question-id") UUID questionId, @PathVariable("answer-id") UUID answerId) {
        answerService.deleteAnswer(answerId);
    }

}
