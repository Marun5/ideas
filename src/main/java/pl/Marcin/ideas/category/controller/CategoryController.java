package pl.Marcin.ideas.category.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.Marcin.ideas.category.domain.model.Category;
import pl.Marcin.ideas.category.service.CategoryService;
import pl.Marcin.ideas.question.domain.model.Question;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    List<Category> getCategories() {
        return categoryService.getCategories();
    }

    //not used here
    Category getCategory(@PathVariable UUID id) {
        return categoryService.getCategory(id);
    }

    @GetMapping("{id}")
    List<Question> findAllByCategoryId(@PathVariable UUID id) {
        return categoryService.findAllByCategoryId(id);
    }

    @PostMapping("{id}")
    Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @PutMapping("{id}")
    Category updateCategory(@RequestParam UUID id, @RequestBody Category category) {
        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("{id}")
    void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }

}
