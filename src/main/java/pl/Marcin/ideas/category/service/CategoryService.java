package pl.Marcin.ideas.category.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Category> getCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Category getCategory(UUID id) {
        return categoryRepository.getReferenceById(id);
    }

    @Transactional(readOnly = true)
    public List<Question> findAllByCategoryId(UUID id) {
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

    @Transactional(readOnly = true)
    public Integer countCategories() {
        return categoryRepository.findAll().size();
    }

    @Transactional(readOnly = true)
    public List<Category> topCategories() {
        return categoryRepository.topCategories();
    }
}
