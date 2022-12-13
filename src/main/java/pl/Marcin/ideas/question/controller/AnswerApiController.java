package pl.marcin.ideas.question.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.marcin.ideas.question.domain.model.Answer;
import pl.marcin.ideas.question.service.AnswerService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/answers")
public class AnswerApiController {

    private final AnswerService answerService;

    @GetMapping
    List<Answer> getAnswers() {
        return answerService.getAnswers();
    }

    @GetMapping("{id}")
    Answer getAnswer(@PathVariable("id") UUID id) {
        return answerService.getAnswer(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Answer updateAnswer(@PathVariable("id") UUID id, @RequestBody Answer answer) {
        return answerService.updateAnswer(id, answer);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAnswer(@PathVariable("id") UUID id) {
        answerService.deleteAnswer(id);
    }

}
