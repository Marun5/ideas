package pl.marcin.ideas.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.marcin.ideas.category.service.CategoryService;
import pl.marcin.ideas.question.service.AnswerService;
import pl.marcin.ideas.question.service.QuestionService;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final CategoryService categoryService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping
    public String indexView(Model model) {
        model.addAttribute("countCategories", categoryService.countCategories());
        model.addAttribute("countEmptyCategories", categoryService.countEmptyCategories().size());
        model.addAttribute("countQuestions", questionService.countQuestions());
        model.addAttribute("countAnswers", answerService.countAnswers());
        model.addAttribute("countUnansweredQuestions", questionService.findUnansweredQuestions().size());
        return "admin/index";
    }
}
