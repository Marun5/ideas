package pl.Marcin.ideas.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.Marcin.ideas.category.domain.model.Category;
import pl.Marcin.ideas.category.service.CategoryService;
import pl.Marcin.ideas.common.Message;
import pl.Marcin.ideas.question.domain.model.Question;
import pl.Marcin.ideas.question.service.QuestionService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static pl.Marcin.ideas.admin.ControllerUtils.paging;

@Controller
@RequestMapping("/admin/categories")
@AllArgsConstructor
@Slf4j
public class CategoryAdminController {

    private final CategoryService categoryService;
    private final QuestionService questionService;

    @GetMapping
    public String allCategoryView(Model model,
                                  @RequestParam(name="s", required = false) String search,
                                  @RequestParam(name="page", required = false, defaultValue = "0") int page,
                                  @RequestParam(name="size", required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoriesPage = categoryService.getCategories(search, pageable);
        model.addAttribute("categoriesPage", categoriesPage);
        model.addAttribute("search", search);
        paging(model, categoriesPage);
        return "admin/categories";
    }

    //addCategory
    @GetMapping("add")
    public String addCategoryView(Model model) {
        model.addAttribute("category", new Category());
        return "admin/addCategory";
    }

    @PostMapping("add")
    public String addCategory(@Valid @ModelAttribute("category") Category category,
                              BindingResult bindingResult,
                              RedirectAttributes ra,
                              Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("category", category);
        }
        try {
            model.addAttribute("category", categoryService.createCategory(category));
            ra.addFlashAttribute("message", Message.info("Category has been added"));
        }catch(Exception e) {
            log.error("Error on category add", e);
            model.addAttribute("category", category);
            ra.addFlashAttribute("message", Message.info("Unknown error occurred on category add."));
            return "admin/addCategory";
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("{id}")
    public String singleCategoryView(@PathVariable UUID id,
                                     @RequestParam(name="s", required = false) String search,
                                     @RequestParam(name="page", required = false, defaultValue = "0") int page,
                                     @RequestParam(name="size", required = false, defaultValue = "10") int size,
                                     Model model) {
        model.addAttribute("category", categoryService.getCategory(id));

        Pageable pageable = PageRequest.of(page, size);
        Page<Question> questionsPage = questionService.findAllByCategoryId(id, search, pageable);
        model.addAttribute("questionsPage", questionsPage);
        model.addAttribute("search", search);
        paging(model, questionsPage);

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
    public String addQuestion(@PathVariable UUID id,
                              @Valid @ModelAttribute("question") Question question,
                              BindingResult bindingResult,
                              RedirectAttributes ra,
                              Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("category", categoryService.getCategory(id));
            model.addAttribute("question", question);
        }
        try {
            model.addAttribute("question", questionService.createQuestion(id, question));
            ra.addFlashAttribute("message", Message.info("Question has been added"));
        }catch(Exception e) {
            log.error("Error on question add", e);
            model.addAttribute("question", question);
            ra.addFlashAttribute("message", Message.info("Unknown error occurred on question add."));
            return "admin/addQuestion";
        }
        return "redirect:/admin/categories/{id}";
    }

    @GetMapping("{id}/edit")
    public String editCategoryView(@PathVariable UUID id, Model model) {
        model.addAttribute("category", categoryService.getCategory(id));
        return "admin/editCategory";
    }

    @PostMapping("{id}/edit")
    public String editCategory(@PathVariable UUID id,
                               @Valid @ModelAttribute("category") Category category,
                               BindingResult bindingResult,
                               RedirectAttributes ra,
                               Model model) {

        if(bindingResult.hasErrors()){
            model.addAttribute("category", category);
            return "admin/editCategory";
        }
        try {
            categoryService.updateCategory(id, category);
            ra.addFlashAttribute("message", Message.info("Category has been changed"));
        }catch(Exception e) {
            log.error("Error on category update", e);
            model.addAttribute("category", category);
            ra.addFlashAttribute("message", Message.info("Unknown error occurred on category's name update."));
            return "admin/editCategory";
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("{id}/delete")
    public String deleteCategory(@PathVariable UUID id, RedirectAttributes ra) {
        try {
            categoryService.deleteCategory(id);
            ra.addFlashAttribute("message", Message.info("Category has been deleted"));
        }catch (Exception e) {
            if(questionService.findAllByCategoryId(id).isEmpty()){
                log.error("Error on category delete", e);
            }
            ra.addFlashAttribute("message",
                    Message.error("Could not delete category. Make sure that category is empty."));
            return "redirect:/admin/categories";
        }
        return "redirect:/admin/categories";
    }
}
