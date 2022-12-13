package pl.marcin.ideas.question.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import pl.marcin.ideas.category.domain.model.Category;
import pl.marcin.ideas.category.domain.repository.CategoryRepository;
import pl.marcin.ideas.question.domain.model.Question;
import pl.marcin.ideas.question.domain.repository.AnswerRepository;
import pl.marcin.ideas.question.domain.repository.QuestionRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
class QuestionServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        answerRepository.deleteAll();
        questionRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void shouldFindAllByCategoryId() {
        //given
        Category category = new Category("Category1");
        categoryRepository.save(category);

        Question question1 = new Question("Question1");
        question1.setCategory(category);
        Question question2 = new Question("Question2");
        question2.setCategory(category);
        Question question3 = new Question("Question3");
        question3.setCategory(category);

        questionRepository.saveAll(List.of(question1, question2, question3));

        //when
        List<Question> questions = questionService.findAllByCategoryId(category.getId());

        //then
        assertThat(questions)
                .hasSize(3)
                .extracting(Question::getName)
                .containsExactlyInAnyOrder("Question1", "Question2", "Question3");

    }

    @Test
    void shouldGetQuestion() {
        //given
        Category category = new Category("Category1");
        categoryRepository.save(category);
        Question question = new Question("Question1");
        question.setCategory(category);
        questionRepository.save(question);

        //when
        Question result = questionService.getQuestion(question.getId());

        //then
        assertThat(result).isEqualTo(question);
        assertThat(result).isEqualTo(questionRepository.getReferenceById(question.getId()));
        assertThat(result.getCategory()).isEqualTo(category);
    }

    @Test
    void shouldCreateQuestion() {
        //given
        Category category = new Category("Category 1");
        categoryRepository.save(category);
        Question question = new Question("Question1");
        question.setCategory(category);

        //when
        Question result = questionService.createQuestion(category.getId(), question);

        //then
        assertThat(result.getName()).isEqualTo(question.getName());
        assertThat(result.getId()).isEqualTo(questionRepository.getReferenceById(result.getId()).getId());
        assertThat(result.getCategory()).isEqualTo(category);
        assertThat(result.getCategory()).isEqualTo(questionRepository.getReferenceById(result.getId()).getCategory());
        assertThat(result.getName()).isEqualTo(questionRepository.getReferenceById(result.getId()).getName());
    }

    @Test
    void shouldUpdateQuestion() {
        //given
        Category category = new Category("Category 1");
        categoryRepository.save(category);
        Question question = new Question("Question1");
        question.setCategory(category);
        questionRepository.save(question);

        question.setName("Updated name");

        //when
        Question result = questionService.updateQuestion(question.getId(), question);

        //then
        assertThat(result.getName()).isEqualTo(question.getName());
        assertThat(result.getName()).isEqualTo(questionRepository.getReferenceById(result.getId()).getName());
        assertThat(result.getName()).isEqualTo("Updated name");
    }

    @Test
    void shouldDeleteQuestion() {
        //given
        Category category = new Category("Category 1");
        categoryRepository.save(category);
        Question question = new Question("Question1");
        question.setCategory(category);
        questionRepository.save(question);

        //when
        questionService.deleteQuestion(question.getId());

        //then
        assertThat(questionRepository.findById(question.getId())).isEmpty();
    }
}