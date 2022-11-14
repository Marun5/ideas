package pl.Marcin.ideas.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.Marcin.ideas.category.domain.model.Category;
import pl.Marcin.ideas.category.service.CategoryService;
import pl.Marcin.ideas.question.domain.model.Question;
import pl.Marcin.ideas.question.service.QuestionService;

import java.util.UUID;

@Controller
@RequestMapping("/admin/categories")
@AllArgsConstructor
public class CategoryAdminController {

    private final CategoryService categoryService;
    private final QuestionService questionService;

    @GetMapping
    public String allCategoryView(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        return "admin/categories";
    }

    //addCategory
    @GetMapping("add")
    public String addCategoryView(Model model) {
        model.addAttribute("category", new Category());
        return "admin/addCategory";
    }

    @PostMapping("add")
    public String addCategory(@ModelAttribute("category") Category category, Model model) {
        model.addAttribute("category", categoryService.createCategory(category));
        return "redirect:/admin/categories";
    }

    @GetMapping("{id}")
    public String singleCategoryView(@PathVariable UUID id, Model model) {
        model.addAttribute("category", categoryService.getCategory(id));
        model.addAttribute("questions", categoryService.findAllByCategoryId(id));
        return "admin/singleCategory";
    }

    //addQuestion
    @GetMapping("{id}/add")
    public String addQuestionView(@PathVariable UUID id, Model model) {
        model.addAttribute("category", categoryService.getCategory(id));
        Question question = new Question();
        question.setCategory(categoryService.getCategory(id));
        model.addAttribute("question", question);
        return "admin/addQuestion";
    }

    @PostMapping("{id}/add")
    public String addQuestion(@PathVariable UUID id, @ModelAttribute("question") Question question, Model model) {
        model.addAttribute("question", questionService.createQuestion(id, question));

        return "redirect:/admin/categories/{id}";
    }

    @GetMapping("{id}/edit")
    public String editCategoryView(@PathVariable UUID id, Model model) {
        model.addAttribute("category", categoryService.getCategory(id));
        return "admin/editCategory";
    }

    @PostMapping("{id}/edit")
    public String editCategory(@PathVariable UUID id, @ModelAttribute("category") Category category) {
        categoryService.updateCategory(id, category);
        return "redirect:/admin/categories";
    }

    @GetMapping("{id}/delete")
    public String deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }
}
