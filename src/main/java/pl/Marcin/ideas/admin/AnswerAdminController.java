package pl.Marcin.ideas.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.Marcin.ideas.question.domain.model.Answer;
import pl.Marcin.ideas.question.service.AnswerService;

import java.util.UUID;

@Controller
@RequestMapping("/admin/answers")
@AllArgsConstructor
public class AnswerAdminController {

    private final AnswerService answerService;

    @GetMapping("{id}/edit")
    public String editAnswerView(@PathVariable UUID id, Model model) {
        model.addAttribute("category", answerService.getAnswer(id).getQuestion().getCategory());
        model.addAttribute("question", answerService.getAnswer(id).getQuestion());
        model.addAttribute("answer", answerService.getAnswer(id));
        return "admin/editAnswer";
    }

    @PostMapping("{id}/edit")
    public String editAnswer(@PathVariable UUID id, @ModelAttribute("answer")Answer answer) {
        answerService.updateAnswer(id, answer);
        return "redirect:/admin/questions/"+answerService.getAnswer(id).getQuestion().getId();
    }

    @GetMapping("{id}/delete")
    public String deleteAnswer(@PathVariable UUID id) {
        UUID questionId = answerService.getAnswer(id).getQuestion().getId();
        answerService.deleteAnswer(id);
        return "redirect:/admin/questions/"+questionId;
    }
}
