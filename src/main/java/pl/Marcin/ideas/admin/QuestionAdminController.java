package pl.Marcin.ideas.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.Marcin.ideas.question.domain.model.Question;
import pl.Marcin.ideas.question.service.QuestionService;

import java.util.UUID;

@Controller
@RequestMapping("/admin/questions")
@AllArgsConstructor
public class QuestionAdminController {

    private final QuestionService questionService;

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

}
