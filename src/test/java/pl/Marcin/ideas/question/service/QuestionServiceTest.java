package pl.Marcin.ideas.question.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.Marcin.ideas.category.domain.model.Category;
import pl.Marcin.ideas.category.domain.repository.CategoryRepository;
import pl.Marcin.ideas.question.domain.model.Answer;
import pl.Marcin.ideas.question.domain.model.Question;
import pl.Marcin.ideas.question.domain.repository.AnswerRepository;
import pl.Marcin.ideas.question.domain.repository.QuestionRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class QuestionServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionService questionService;

    @Test
    void shouldFindAllQuestionsByCategoryId() {
        //given
        answerRepository.deleteAll();
        questionRepository.deleteAll();

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

//    @Test
//    void shouldGetQuestion() {
//        //given
//        answerRepository.deleteAll();
//        questionRepository.deleteAll();
//        categoryRepository.deleteAll();
//
//        Category category = new Category("Category1");
//        categoryRepository.save(category);
//        Question question = new Question("Question1");
//        question.setCategory(category);
//        questionRepository.save(question);
//
//        //when
////        List<Answer> result = questionService.getQuestion(question.getId());
//
//        //then
//        assertThat(question.getCategory()).isEqualTo(category);
//        assertThat(result).isEmpty();
//    }

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