package pl.Marcin.ideas.question.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.Marcin.ideas.category.domain.model.Category;
import pl.Marcin.ideas.category.domain.repository.CategoryRepository;
import pl.Marcin.ideas.question.domain.model.Answer;
import pl.Marcin.ideas.question.domain.model.Question;
import pl.Marcin.ideas.question.domain.repository.AnswerRepository;
import pl.Marcin.ideas.question.domain.repository.QuestionRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class QuestionService {

    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Transactional(readOnly = true)
    public List<Question> findAllByCategoryId(UUID id) {
        return questionRepository.findAllByCategoryId(id);
    }

    @Transactional(readOnly = true)
    public List<Answer> getQuestion(UUID id) {
        return answerRepository.findAllByQuestionId(id);
    }

    @Transactional
    public Question createQuestion(UUID categoryId, Question questionRequest) {
        Question question = new Question();
        question.setName(questionRequest.getName());
        Category category = categoryRepository.getReferenceById(categoryId);
        question.setCategory(category);

        questionRepository.save(question);
        categoryRepository.save(category);

        return question;
    }

    @Transactional
    public Question updateQuestion(UUID id, Question questionRequest) {
        Question question = questionRepository.getReferenceById(id);
        question.setName(questionRequest.getName());

        return questionRepository.save(question);
    }

    @Transactional
    public void deleteQuestion(UUID id) {
        questionRepository.deleteById(id);
    }



}
