package pl.Marcin.ideas.question.service;

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
public class QuestionService {

    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;

    @Transactional(readOnly = true)
    public List<Question> getQuestions() {
        return questionRepository.findAll();
    }
    @Transactional(readOnly = true)
    public List<Question> getQuestions(String search) {
        return questionRepository.findAllByNameContainingIgnoreCase(search);
    }
    @Transactional(readOnly = true)
    public List<Question> findAllByCategoryId(UUID id) {
        return questionRepository.findAllByCategoryId(id);
    }
    @Transactional(readOnly = true)
    public Page<Question> findAllByCategoryId(UUID id, Pageable pageable) {
        return findAllByCategoryId(id, null, pageable);
    }
    @Transactional(readOnly = true)
    public Page<Question> findAllByCategoryId(UUID id, String search, Pageable pageable) {
        if(search==null){
            return questionRepository.findAllByCategoryId(id, pageable);
        } else {
            return questionRepository.findAllByCategoryIdAndNameContainingIgnoreCase(id, search, pageable);
        }
    }

    @Transactional(readOnly = true)
    public Question getQuestion(UUID id) {
        return questionRepository.getReferenceById(id);
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

    @Transactional(readOnly = true)
    public Integer countQuestions() {
        return questionRepository.findAll().size();
    }

    @Transactional(readOnly = true)
    public List<Question> findRandomQuestions(int limit) {
        return questionRepository.findRandomQuestions(limit);
    }

    @Transactional(readOnly = true)
    public List<Question> findHotQuestions() {
        return questionRepository.findHotQuestions();
    }

    @Transactional(readOnly = true)
    public List<Question> findUnansweredQuestions() {
        return questionRepository.findUnansweredQuestions();
    }

    @Transactional(readOnly = true)
    public List<Question> findQuestionsWithMinTwoAnswers() {
        return questionRepository.findQuestionsWithMinTwoAnswers();
    }
}
