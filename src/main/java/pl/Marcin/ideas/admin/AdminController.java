package pl.Marcin.ideas.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.Marcin.ideas.category.service.CategoryService;
import pl.Marcin.ideas.question.service.AnswerService;
import pl.Marcin.ideas.question.service.QuestionService;

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
        model.addAttribute("countQuestions", questionService.countQuestions());
        model.addAttribute("countAnswers", answerService.countAnswers());
        model.addAttribute("countUnansweredQuestions", questionService.findUnansweredQuestions().size());
        return "admin/index";
    }
}
