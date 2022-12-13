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
import pl.marcin.ideas.question.domain.model.Question;
import pl.marcin.ideas.question.service.AnswerService;
import pl.marcin.ideas.question.service.QuestionService;

import javax.validation.Valid;
import java.util.UUID;

import static pl.marcin.ideas.admin.ControllerUtils.paging;
import static pl.marcin.ideas.admin.ControllerUtils.reverseSort;

@Controller
@RequestMapping("/admin/questions")
@AllArgsConstructor
@Slf4j
public class QuestionAdminController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    @GetMapping
    public String allQuestionsView(Model model,
                                   @RequestParam(name="s", required = false) String search,
                                   @RequestParam(name="direction", required = false, defaultValue = "desc") String direction,
                                   @RequestParam(name="sortBy", required = false, defaultValue = "id") String sortBy,
                                   @RequestParam(name="page", required = false, defaultValue = "0") int page,
                                   @RequestParam(name="size", required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sortBy);
        Page<Question> questionsPage = questionService.getQuestions(search, pageable);
        model.addAttribute("questionsPage", questionsPage);
        model.addAttribute("search", search);
        model.addAttribute("direction", direction);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("page", page);
        model.addAttribute("size", size);


        model.addAttribute("reverseSort", reverseSort(direction));
        paging(model, questionsPage);
        return "admin/questions";
    }

    @GetMapping("{id}")
    public String singleQuestionView(@PathVariable UUID id,
                                     @RequestParam(name="s", required = false) String search,
                                     @RequestParam(name="page", required = false, defaultValue = "0") int page,
                                     @RequestParam(name="size", required = false, defaultValue = "10") int size,
                                     Model model) {
        model.addAttribute("category", questionService.getQuestion(id).getCategory());
        model.addAttribute("question", questionService.getQuestion(id));

        Pageable pageable = PageRequest.of(page, size);
        Page<Answer> answersPage = answerService.findAllByQuestionId(id, search, pageable);
        model.addAttribute("answersPage", answersPage);
        model.addAttribute("search", search);
        paging(model, answersPage);

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
            if(answerService.findAllByQuestionId(id).isEmpty()){
                log.error("Error on question delete", e);
            }
            ra.addFlashAttribute("message",
                    Message.error("Could not delete question. Make sure that question is empty."));
            return "redirect:/admin/categories/"+categoryId;
        }

        return "redirect:/admin/categories/"+categoryId;
    }
}
