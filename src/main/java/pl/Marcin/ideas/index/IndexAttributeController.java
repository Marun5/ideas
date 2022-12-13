package pl.marcin.ideas.index;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import pl.marcin.ideas.category.service.CategoryService;
import pl.marcin.ideas.question.service.AnswerService;
import pl.marcin.ideas.question.service.QuestionService;

public abstract class IndexAttributeController {

    private final int RANDOM_QUESTIONS_LIMIT = 6;
    @Autowired
    protected CategoryService categoryService;
    @Autowired
    protected QuestionService questionService;
    @Autowired
    protected AnswerService answerService;

    protected void addGlobalAttributes(Model model) {
        model.addAttribute("topCategories", categoryService.topCategories());
        model.addAttribute("randomQuestions", questionService.findRandomQuestions(RANDOM_QUESTIONS_LIMIT));
    }
}
