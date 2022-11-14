package pl.Marcin.ideas.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.Marcin.ideas.question.domain.model.Answer;
import pl.Marcin.ideas.question.domain.model.Question;
import pl.Marcin.ideas.question.service.AnswerService;
import pl.Marcin.ideas.question.service.QuestionService;

import java.util.UUID;

@Controller
@RequestMapping("/admin/questions")
@AllArgsConstructor
public class QuestionAdminController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping("{id}")
    public String singleQuestionView(@PathVariable UUID id, Model model) {
        model.addAttribute("category", questionService.getQuestion(id).getCategory());
        model.addAttribute("question", questionService.getQuestion(id));
        model.addAttribute("answers", questionService.findAllByQuestionId(id));
        return "admin/singleQuestion";
    }

    @GetMapping("{id}/add")
    public String addAnswerView(@PathVariable UUID id, Model model) {
        model.addAttribute("category", questionService.getQuestion(id).getCategory());
        model.addAttribute("question", questionService.getQuestion(id));
        Answer answer = new Answer();
        answer.setQuestion(questionService.getQuestion(id));
        model.addAttribute("answer", answer);
        return "admin/addAnswer";
    }

    @PostMapping("{id}/add")
    public String addAnswer(@PathVariable UUID id, @ModelAttribute("answer") Answer answer, Model model) {
        model.addAttribute("answer", answerService.createAnswer(id, answer));
        return "redirect:/admin/questions/{id}";
    }

    @GetMapping("{id}/edit")
    public String editQuestionView(@PathVariable UUID id, Model model) {
        model.addAttribute("question", questionService.getQuestion(id));
        model.addAttribute("category", questionService.getQuestion(id).getCategory());
        return "admin/editQuestion";
    }

    @PostMapping("{id}/edit")
    public String editQuestion(@PathVariable UUID id, @ModelAttribute("question")Question question) {
        questionService.updateQuestion(id, question);
        return "redirect:/admin/categories/"+questionService.getQuestion(id).getCategory().getId();
    }

    @GetMapping("{id}/delete")
    public String deleteQuestion(@PathVariable UUID id) {
        UUID categoryId = questionService.getQuestion(id).getCategory().getId();
        questionService.deleteQuestion(id);
        return "redirect:/admin/categories/"+categoryId;
    }
}
