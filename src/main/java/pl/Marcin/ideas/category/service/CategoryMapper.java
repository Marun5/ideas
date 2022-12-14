package pl.marcin.ideas.category.service;

import org.springframework.stereotype.Component;
import pl.marcin.ideas.category.domain.model.Category;
import pl.marcin.ideas.category.dto.CategoryDto;
import pl.marcin.ideas.question.domain.model.Question;
import pl.marcin.ideas.question.dto.QuestionDto;

@Component
public class CategoryMapper {

    public CategoryDto map(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setQuestions(category.getQuestions() == null ? 0 : category.getQuestions().size());

        return categoryDto;
    }
}
