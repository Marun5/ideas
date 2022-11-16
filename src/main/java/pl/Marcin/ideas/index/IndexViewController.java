package pl.Marcin.ideas.index;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
@RequiredArgsConstructor
public class IndexViewController extends IndexAttributeController {


    @GetMapping
    public String indexView(Model model) {
        addGlobalAttributes(model);

        return "index/index";
    }
}
