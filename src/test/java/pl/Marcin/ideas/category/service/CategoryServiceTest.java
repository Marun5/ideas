package pl.Marcin.ideas.category.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.Marcin.ideas.category.domain.model.Category;
import pl.Marcin.ideas.category.domain.repository.CategoryRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Test
    void shouldGetAllCategories() {
        //given
        categoryRepository.deleteAll();

        categoryRepository.saveAll(List.of(
                new Category("Category 1"),
                new Category("Category 2"),
                new Category("Category 3")));

        //when
        List<Category> categories = categoryService.getCategories();

        //then
        assertThat(categories)
                .hasSize(3)
                .extracting(Category::getName)
                .containsExactlyInAnyOrder("Category 1", "Category 2", "Category 3");

    }

    @Test
    void shouldGetOneCategory() {
        //given
        categoryRepository.deleteAll();

        Category category = new Category("Category 1");
        categoryRepository.save(category);

        //when
        Category result = categoryService.getCategory(category.getId());

        //then
        assertThat(result.getId()).isEqualTo(category.getId());
    }

    @Test
    void shouldCreateCategory() {
        //given
        Category category = new Category("Category 1");

        //when
        Category result = categoryService.createCategory(category);

        //then
        assertThat(result.getName()).isEqualTo(category.getName());
        assertThat(result.getId()).isEqualTo(categoryRepository.getReferenceById(result.getId()).getId());
        assertThat(result.getName()).isEqualTo(categoryRepository.getReferenceById(result.getId()).getName());

    }

    @Test
    void shouldUpdateCategory() {
        //given
        Category category = new Category("Category 1");
        Category categoryRequest = categoryService.createCategory(category);

        categoryRequest.setName("Updated name");

        //when
        Category result = categoryService.updateCategory(categoryRequest.getId(), categoryRequest);

        //then
        assertThat(result.getName()).isEqualTo(categoryRequest.getName());
        assertThat(result.getName()).isEqualTo(categoryRepository.getReferenceById(result.getId()).getName());

    }

    @Test
    void shouldDeleteCategory() {
        //given
        Category category = new Category("Category 1");
        Category categoryRequest = categoryService.createCategory(category);

        //when
        categoryService.deleteCategory(categoryRequest.getId());

        //then
        assertThat(categoryRepository.findById(categoryRequest.getId())).isEmpty();

    }
}