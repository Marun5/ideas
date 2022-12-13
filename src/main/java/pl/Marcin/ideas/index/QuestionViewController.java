package pl.marcin.ideas.index;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.marcin.ideas.question.service.AnswerService;
import pl.marcin.ideas.question.service.QuestionService;

import java.util.UUID;

@Controller
@RequestMapping("/index/questions")
@RequiredArgsConstructor
public class QuestionViewController extends IndexAttributeController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping("{id}")
    public String singleQuestionView(@PathVariable UUID id, Model model) {
        addGlobalAttributes(model);
        model.addAttribute("question", questionService.getQuestion(id));
        model.addAttribute("answers", answerService.findAllByQuestionId(id));

        return "index/singleQuestion";
    }

    @GetMapping("hot")
    public String hotQuestionsView(Model model) {
        addGlobalAttributes(model);
        model.addAttribute("hotQuestions", questionService.findHotQuestions());

        return "index/hotQuestions";
    }

    @GetMapping("unanswered")
    public String unansweredQuestionsView(Model model) {
        addGlobalAttributes(model);
        model.addAttribute("unansweredQuestions", questionService.findUnansweredQuestions());

        return "index/unansweredQuestions";
    }
}
