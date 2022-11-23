package pl.Marcin.ideas.index;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.Marcin.ideas.category.service.CategoryService;
import pl.Marcin.ideas.question.service.QuestionService;

import java.util.UUID;

@Controller
@RequestMapping("/index/categories")
@RequiredArgsConstructor
public class CategoryViewController extends IndexAttributeController {

    private final CategoryService categoryService;
    private final QuestionService questionService;

    @GetMapping
    public String allCategoriesView(Model model) {
        addGlobalAttributes(model);
        model.addAttribute("categories", categoryService.getCategories());

        return "index/categories";
    }

    @GetMapping("{id}")
    public String singleCategoryView(@PathVariable UUID id, Model model) {
        addGlobalAttributes(model);
        model.addAttribute("category", categoryService.getCategory(id));
        model.addAttribute("questions", questionService.findAllByCategoryId(id));

        return "index/singleCategory";
    }
}
