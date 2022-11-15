package pl.Marcin.ideas.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.Marcin.ideas.common.Message;
import pl.Marcin.ideas.question.domain.model.Answer;
import pl.Marcin.ideas.question.service.AnswerService;

import java.util.UUID;

@Controller
@RequestMapping("/admin/answers")
@AllArgsConstructor
@Slf4j
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
    public String editAnswer(@PathVariable UUID id,
                             @ModelAttribute("answer")Answer answer,
                             BindingResult bindingResult,
                             RedirectAttributes ra,
                             Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("category", answerService.getAnswer(id).getQuestion().getCategory());
            model.addAttribute("question", answerService.getAnswer(id).getQuestion());
            model.addAttribute("answer", answerService.getAnswer(id));
        }
        try{
            answerService.updateAnswer(id, answer);
            ra.addFlashAttribute("message", Message.info("Answer name has been changed."));
        }catch (Exception e) {
            log.error("Error on answer update", e);
            model.addAttribute("answer", answer);
            ra.addFlashAttribute("message", Message.error("Unknown error occurred on answer's name change."));
        }
        return "redirect:/admin/questions/"+answerService.getAnswer(id).getQuestion().getId();
    }

    @GetMapping("{id}/delete")
    public String deleteAnswer(@PathVariable UUID id, RedirectAttributes ra) {
        UUID questionId = answerService.getAnswer(id).getQuestion().getId();
        try {
            answerService.deleteAnswer(id);
            ra.addFlashAttribute("message", Message.info("Answer has been deleted."));
        }catch (Exception e){
            log.error("Error on question delete", e);
            ra.addFlashAttribute("message", Message.error("Could not delete answer."));
            return "redirect:/admin/questions/"+questionId;
        }
        return "redirect:/admin/questions/"+questionId;
    }
}
