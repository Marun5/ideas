package pl.marcin.ideas.admin;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class ControllerUtils {

    public static void paging(Model model, Page page) {
        int totalPages = page.getTotalPages();
        if(totalPages>0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }

    public static String reverseSort(String direction) {
        String reverseSort;
        if("asc".equals(direction)){
            reverseSort = "desc";
        }else {
            reverseSort = "asc";
        }
        return reverseSort;
    }
}
