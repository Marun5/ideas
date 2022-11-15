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
import pl.Marcin.ideas.question.domain.model.Question;
import pl.Marcin.ideas.question.service.AnswerService;
import pl.Marcin.ideas.question.service.QuestionService;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/admin/questions")
@AllArgsConstructor
@Slf4j
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
    public String addAnswer(@PathVariable UUID id,
                            @Valid @ModelAttribute("answer") Answer answer,
                            BindingResult bindingResult,
                            RedirectAttributes ra,
                            Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("category", questionService.getQuestion(id).getCategory());
            model.addAttribute("question", questionService.getQuestion(id));
            model.addAttribute("answer", answer);
        }
        try {
            model.addAttribute("answer", answerService.createAnswer(id, answer));
            ra.addFlashAttribute("message", Message.info("Answer has been added"));
        }catch(Exception e) {
            log.error("Error on answer add", e);
            model.addAttribute("answer", answer);
            ra.addFlashAttribute("message", Message.info("Unknown error occurred on answer add."));
            return "admin/addAnswer";
        }
        return "redirect:/admin/questions/{id}";
    }

    @GetMapping("{id}/edit")
    public String editQuestionView(@PathVariable UUID id, Model model) {
        model.addAttribute("question", questionService.getQuestion(id));
        model.addAttribute("category", questionService.getQuestion(id).getCategory());
        return "admin/editQuestion";
    }

    @PostMapping("{id}/edit")
    public String editQuestion(@PathVariable UUID id,
                               @Valid @ModelAttribute("question")Question question,
                               BindingResult bindingResult,
                               RedirectAttributes ra,
                               Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("category", questionService.getQuestion(id).getCategory());
            model.addAttribute("question", question);
            return "admin/editQuestion";
        }
        try{
            questionService.updateQuestion(id, question);
            ra.addFlashAttribute("message", Message.info("Question name has been changed"));
        } catch (Exception e) {
            log.error("Error on question update", e);
            model.addAttribute("question", question);
            ra.addFlashAttribute("message", Message.error("Unknown error occurred on question's name update."));
            return "admin/editQuestion";
        }
        return "redirect:/admin/categories/"+questionService.getQuestion(id).getCategory().getId();
    }

    @GetMapping("{id}/delete")
    public String deleteQuestion(@PathVariable UUID id,
                                 RedirectAttributes ra) {
        UUID categoryId = questionService.getQuestion(id).getCategory().getId();
        try {
            questionService.deleteQuestion(id);
            ra.addFlashAttribute("message", Message.info("Question has been deleted"));
        }catch (Exception e) {
            if(questionService.findAllByQuestionId(id).isEmpty()){
                log.error("Error on question delete", e);
            }
            ra.addFlashAttribute("message",
                    Message.error("Could not delete question. Make sure that question is empty."));
            return "redirect:/admin/categories/"+categoryId;
        }

        return "redirect:/admin/categories/"+categoryId;
    }
}
