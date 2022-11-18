package pl.Marcin.ideas.index;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.Marcin.ideas.category.domain.model.Category;
import pl.Marcin.ideas.question.domain.model.Answer;
import pl.Marcin.ideas.question.domain.model.Question;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/index")
@RequiredArgsConstructor
public class IndexViewController extends IndexAttributeController {


    @GetMapping
    public String indexView(Model model) {
        addGlobalAttributes(model);
        List<Question> questionsWithMinTwoAnswers = questionService.findQuestionsWithMinTwoAnswers();
        Collections.shuffle(questionsWithMinTwoAnswers);
        model.addAttribute("questionsWithMinTwoAnswers", questionsWithMinTwoAnswers);

        return "index/index";
    }

    @GetMapping("search")
    public String searchView(@RequestParam(name="s", required = false) String search, Model model) {
        addGlobalAttributes(model);
        model.addAttribute("search", search);
        List<Category> searchCategories = categoryService.getCategories(search);
        List<Question> searchQuestions = questionService.getQuestions(search);
        List<Answer> searchAnswers = answerService.getAnswers(search);

        model.addAttribute("searchCategories", searchCategories);
        model.addAttribute("searchQuestions", searchQuestions);
        model.addAttribute("searchAnswers", searchAnswers);

        return "index/searchIndex";
    }
}
