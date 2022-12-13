package pl.marcin.ideas.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.marcin.ideas.common.Message;
import pl.marcin.ideas.question.domain.model.Answer;
import pl.marcin.ideas.question.service.AnswerService;

import java.util.UUID;

import static pl.marcin.ideas.admin.ControllerUtils.paging;
import static pl.marcin.ideas.admin.ControllerUtils.reverseSort;

@Controller
@RequestMapping("/admin/answers")
@AllArgsConstructor
@Slf4j
public class AnswerAdminController {

    private final AnswerService answerService;

    @GetMapping
    public String allAnswersView(Model model,
                                   @RequestParam(name="s", required = false) String search,
                                   @RequestParam(name="direction", required = false, defaultValue = "desc") String direction,
                                   @RequestParam(name="sortBy", required = false, defaultValue = "id") String sortBy,
                                   @RequestParam(name="page", required = false, defaultValue = "0") int page,
                                   @RequestParam(name="size", required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sortBy);
        Page<Answer> answersPage = answerService.getAnswers(search, pageable);
        model.addAttribute("answersPage", answersPage);
        model.addAttribute("search", search);
        model.addAttribute("direction", direction);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        model.addAttribute("reverseSort", reverseSort(direction));
        paging(model, answersPage);
        return "admin/answers";
    }

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
