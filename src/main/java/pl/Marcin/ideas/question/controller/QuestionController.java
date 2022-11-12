package pl.Marcin.ideas.question.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.Marcin.ideas.question.domain.model.Answer;
import pl.Marcin.ideas.question.domain.model.Question;
import pl.Marcin.ideas.question.service.QuestionService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("questions")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    List<Question> findAllByCategoryId(UUID id) {
        return questionService.findAllByCategoryId(id);
    }

    @GetMapping("{id}")
    List<Answer> getQuestion(@PathVariable UUID id) {
        return questionService.getQuestion(id);
    }

    @PostMapping("{id}")
    Question createQuestion(@RequestParam UUID categoryId, @RequestBody Question question) {
        return questionService.createQuestion(categoryId, question);
    }

    @PutMapping("{id}")
    Question updateQuestion(@RequestParam UUID id, @RequestBody Question question) {
        return questionService.updateQuestion(id, question);
    }

    @DeleteMapping("{id}")
    void deleteQuestion(@PathVariable UUID id) {
        questionService.deleteQuestion(id);
    }

}
