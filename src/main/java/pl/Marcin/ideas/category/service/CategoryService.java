package pl.Marcin.ideas.category.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.Marcin.ideas.category.domain.model.Category;
import pl.Marcin.ideas.category.domain.repository.CategoryRepository;
import pl.Marcin.ideas.question.domain.model.Question;
import pl.Marcin.ideas.question.domain.repository.QuestionRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Question> getCategory(UUID id) {
        return questionRepository.findAllByCategoryId(id);
    }

    @Transactional
    public Category createCategory(Category categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());

        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(UUID id, Category categoryRequest) {
        Category category = categoryRepository.getReferenceById(id);
        category.setName(categoryRequest.getName());

        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }

}
