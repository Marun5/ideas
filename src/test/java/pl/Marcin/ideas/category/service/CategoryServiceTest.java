package pl.marcin.ideas.category.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.marcin.ideas.category.domain.model.Category;
import pl.marcin.ideas.category.domain.repository.CategoryRepository;
import pl.marcin.ideas.question.domain.repository.AnswerRepository;
import pl.marcin.ideas.question.domain.repository.QuestionRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        answerRepository.deleteAll();
        questionRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void shouldGetAllCategories() {
        //given
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
    void shouldGetCategory() {
        //given
        Category category = new Category("Category 1");
        categoryRepository.save(category);

        //when
        Category result = categoryService.getCategory(category.getId());

        //then
        assertThat(result).isEqualTo(categoryRepository.getReferenceById(category.getId()));
        assertThat(result.getId()).isEqualTo(categoryRepository.getReferenceById(category.getId()).getId());
        assertThat(result.getName()).isEqualTo(categoryRepository.getReferenceById(category.getId()).getName());
    }

    @Test
    void shouldCreateCategory() {
        //given
        Category category = new Category("Category 1");

        //when
        Category result = categoryService.createCategory(category);

        //then
        assertThat(result).isEqualTo(categoryRepository.getReferenceById(category.getId()));
        assertThat(result.getId()).isEqualTo(categoryRepository.getReferenceById(category.getId()).getId());
        assertThat(result.getName()).isEqualTo(categoryRepository.getReferenceById(category.getId()).getName());

    }

    @Test
    void shouldUpdateCategory() {
        //given
        Category category = new Category("Category 1");
        categoryRepository.save(category);
        Category categoryRequest = categoryService.getCategory(category.getId());

        categoryRequest.setName("Updated name");

        //when
        Category result = categoryService.updateCategory(categoryRequest.getId(), categoryRequest);

        //then
        assertThat(result).isEqualTo(categoryRepository.getReferenceById(categoryRequest.getId()));
        assertThat(result.getId()).isEqualTo(categoryRepository.getReferenceById(categoryRequest.getId()).getId());
        assertThat(result.getName()).isEqualTo(categoryRepository.getReferenceById(categoryRequest.getId()).getName());

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