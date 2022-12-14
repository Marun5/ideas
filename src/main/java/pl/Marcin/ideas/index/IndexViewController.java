package pl.marcin.ideas.index;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.marcin.ideas.category.domain.model.Category;
import pl.marcin.ideas.category.dto.CategoryDto;
import pl.marcin.ideas.question.domain.model.Answer;
import pl.marcin.ideas.question.domain.model.Question;
import pl.marcin.ideas.question.dto.AnswerDto;
import pl.marcin.ideas.question.dto.QuestionDto;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class IndexViewController extends IndexAttributeController {

    @GetMapping
    public String index() {
        return "redirect:/index";
    }

    @GetMapping("index")
    public String indexView(Model model) {
        addGlobalAttributes(model);
        List<QuestionDto> questionsWithMinTwoAnswers = questionService.findQuestionsWithMinTwoAnswers();
        Collections.shuffle(questionsWithMinTwoAnswers);
        model.addAttribute("questionsWithMinTwoAnswers", questionsWithMinTwoAnswers);
        model.addAttribute("categories", categoryService.topCategories());

        return "index/index";
    }

    @GetMapping("index/search")
    public String searchView(@RequestParam(name="s", required = false) String search, Model model) {
        addGlobalAttributes(model);
        model.addAttribute("search", search);
        List<CategoryDto> searchCategories = categoryService.getCategories(search);
        List<QuestionDto> searchQuestions = questionService.getQuestions(search);
        List<AnswerDto> searchAnswers = answerService.getAnswers(search);

        model.addAttribute("searchCategories", searchCategories);
        model.addAttribute("searchQuestions", searchQuestions);
        model.addAttribute("searchAnswers", searchAnswers);

        return "index/searchIndex";
    }
}
