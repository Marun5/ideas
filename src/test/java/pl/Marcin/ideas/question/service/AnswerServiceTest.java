package pl.marcin.ideas.question.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.marcin.ideas.category.domain.model.Category;
import pl.marcin.ideas.category.domain.repository.CategoryRepository;
import pl.marcin.ideas.question.domain.model.Answer;
import pl.marcin.ideas.question.domain.model.Question;
import pl.marcin.ideas.question.domain.repository.AnswerRepository;
import pl.marcin.ideas.question.domain.repository.QuestionRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class AnswerServiceTest {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private AnswerService answerService;

    @BeforeEach
    void setUp() {
        answerRepository.deleteAll();
        questionRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void shouldFindAllByQuestionId() {
        //given
        Category category = new Category("Category1");
        categoryRepository.save(category);

        Question question1 = new Question("Question1");
        question1.setCategory(category);
        questionRepository.save(question1);

        Answer answer1 = new Answer("Answer1");
        answer1.setQuestion(question1);
        Answer answer2 = new Answer("Answer2");
        answer2.setQuestion(question1);
        answerRepository.saveAll(List.of(answer1, answer2));

        //when
        List<Answer> result = answerService.findAllByQuestionId(question1.getId());

        //then
        assertThat(result)
                .hasSize(2)
                .extracting(Answer::getName)
                .containsExactlyInAnyOrder("Answer1", "Answer2");
    }

    @Test
    void shouldGetAnswer() {
        //given
        Category category = new Category("Category1");
        categoryRepository.save(category);

        Question question1 = new Question("Question1");
        question1.setCategory(category);
        questionRepository.save(question1);

        Answer answer1 = new Answer("Answer1");
        answer1.setQuestion(question1);
        Answer answer2 = new Answer("Answer2");
        answer2.setQuestion(question1);
        answerRepository.saveAll(List.of(answer1, answer2));

        //when
        Answer result = answerRepository.getReferenceById(answer1.getId());

        //then
        assertThat(result).isEqualTo(answerRepository.getReferenceById(answer1.getId()));
        assertThat(result.getId()).isEqualTo(answerRepository.getReferenceById(answer1.getId()).getId());
        assertThat(result.getName()).isEqualTo(answerRepository.getReferenceById(answer1.getId()).getName());
        assertThat(result.getQuestion()).isEqualTo(answerRepository.getReferenceById(answer1.getId()).getQuestion());
    }

    @Test
    void shouldCreateAnswer() {
        //given
        Category category = new Category("Category1");
        categoryRepository.save(category);

        Question question1 = new Question("Question1");
        question1.setCategory(category);
        questionRepository.save(question1);

        Answer answer1 = new Answer("Answer1");
        answer1.setQuestion(question1);
        answerRepository.save(answer1);
        Answer answer2 = new Answer("Answer2");
        answer2.setQuestion(question1);

        //when
        Answer result = answerService.createAnswer(question1.getId(), answer2);

        //then
        assertThat(result.getId()).isEqualTo(answerRepository.getReferenceById(result.getId()).getId());
        assertThat(result.getName()).isEqualTo(answerRepository.getReferenceById(result.getId()).getName());
        assertThat(answerRepository.findAllByQuestionId(question1.getId()))
                .hasSize(2)
                .extracting(Answer::getName)
                .containsExactlyInAnyOrder("Answer1", "Answer2");
    }

    @Test
    void shouldUpdateAnswer() {
        //given
        Category category = new Category("Category1");
        categoryRepository.save(category);

        Question question1 = new Question("Question1");
        question1.setCategory(category);
        questionRepository.save(question1);

        Answer answer1 = new Answer("Answer1");
        answer1.setQuestion(question1);
        answerRepository.save(answer1);

        Answer answer2 = new Answer("Answer updated");

        //when
        Answer result = answerService.updateAnswer(answer1.getId(), answer2);

        //then
        assertThat(result.getName()).isEqualTo("Answer updated");
        assertThat(result.getName()).isEqualTo(answer2.getName());
        assertThat(result.getName()).isEqualTo(answerRepository.getReferenceById(result.getId()).getName());
    }

    @Test
    void shouldDeleteAnswer() {
        //given
        Category category = new Category("Category1");
        categoryRepository.save(category);

        Question question1 = new Question("Question1");
        question1.setCategory(category);
        questionRepository.save(question1);

        Answer answer1 = new Answer("Answer1");
        answer1.setQuestion(question1);
        answerRepository.save(answer1);

        //when
        answerService.deleteAnswer(answer1.getId());

        //then
        assertThat(answerRepository.findById(answer1.getId())).isEmpty();
    }
}