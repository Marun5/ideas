package pl.Marcin.ideas.index;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import pl.Marcin.ideas.category.service.CategoryService;
import pl.Marcin.ideas.question.service.AnswerService;
import pl.Marcin.ideas.question.service.QuestionService;

public abstract class IndexAttributeController {

    @Autowired
    protected CategoryService categoryService;
    @Autowired
    protected QuestionService questionService;
    @Autowired
    protected AnswerService answerService;

    protected void addGlobalAttributes(Model model) {
        model.addAttribute("topCategories", categoryService.topCategories());
        model.addAttribute("randomQuestions", questionService.findRandomQuestions(6));
        model.addAttribute("hotQuestions", questionService.findHotQuestions());
        model.addAttribute("unansweredQuestions", questionService.findUnansweredQuestions());
    }
}
