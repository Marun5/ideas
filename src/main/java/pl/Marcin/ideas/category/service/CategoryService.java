package pl.marcin.ideas.category.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.marcin.ideas.category.domain.model.Category;
import pl.marcin.ideas.category.domain.repository.CategoryRepository;
import pl.marcin.ideas.category.dto.CategoryDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategories(String search) {
        return categoryRepository.findAllByNameContainingIgnoreCase(search)
                .stream()
                .map(categoryMapper::map)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public Page<Category> getCategories(Pageable pageable) {
        return getCategories(null, pageable);
    }
    @Transactional(readOnly = true)
    public Page<Category> getCategories(String search, Pageable pageable) {
        if(search==null){
            return categoryRepository.findAll(pageable);
        } else
            return categoryRepository.findByNameContainingIgnoreCase(search, pageable);
    }

    @Transactional(readOnly = true)
    public Category getCategory(UUID id) {
        return categoryRepository.getReferenceById(id);
    }

    @Transactional
    public Category createCategory(Category categoryRequest) {
        return categoryRepository.save(categoryRequest);
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

    @Transactional(readOnly = true)
    public List<CategoryDto> countEmptyCategories() {
        return categoryRepository.countEmptyCategories()
                .stream().map(categoryMapper::map)
                .collect(Collectors.toList());
    }
}
