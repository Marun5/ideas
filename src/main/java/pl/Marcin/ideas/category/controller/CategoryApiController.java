package pl.marcin.ideas.category.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.marcin.ideas.category.domain.model.Category;
import pl.marcin.ideas.category.service.CategoryService;
import pl.marcin.ideas.question.domain.model.Question;
import pl.marcin.ideas.question.service.QuestionService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/categories")
public class CategoryApiController {

    private final CategoryService categoryService;
    private final QuestionService questionService;

    @GetMapping
    Page<Category> getCategories(Pageable pageable) {
        return categoryService.getCategories(pageable);
    }

    @GetMapping("single/{id}")
    Category getCategory(@PathVariable UUID id) {
        return categoryService.getCategory(id);
    }

    @GetMapping("{id}")
    List<Question> findAllByCategoryId(@PathVariable UUID id) {
        return questionService.findAllByCategoryId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Category updateCategory(@PathVariable UUID id, @RequestBody Category category) {
        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }

    @PostMapping("{categoryId}")
    @ResponseStatus(HttpStatus.CREATED)
    Question createQuestion(@PathVariable UUID categoryId, @RequestBody Question question) {
        return questionService.createQuestion(categoryId, question);
    }

}
