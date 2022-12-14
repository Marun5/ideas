package pl.marcin.ideas.question.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.marcin.ideas.category.domain.model.Category;
import pl.marcin.ideas.category.domain.repository.CategoryRepository;
import pl.marcin.ideas.question.domain.model.Question;
import pl.marcin.ideas.question.domain.repository.QuestionRepository;
import pl.marcin.ideas.question.dto.QuestionDto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionService {

    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @Transactional(readOnly = true)
    public List<Question> getQuestions() {
        return questionRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Page<Question> getQuestions(String search, Pageable pageable) {
        if(search==null){
            return questionRepository.findAll(pageable);
        } else {
            return questionRepository.findAllByNameContainingIgnoreCase(search, pageable);
        }
    }
    @Transactional(readOnly = true)
    public List<QuestionDto> getQuestions(String search) {
        return questionRepository.findAllByNameContainingIgnoreCase(search)
                .stream()
                .map(questionMapper::map)
                .collect(Collectors.toList());
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
        Category category = categoryRepository.getReferenceById(categoryId);
        questionRequest.setCategory(category);

        questionRepository.save(questionRequest);
        categoryRepository.save(category);

        return questionRequest;
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
    public List<QuestionDto> findRandomQuestions(int limit) {
        return questionRepository.findRandomQuestions(limit)
                .stream()
                .map(questionMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<QuestionDto> findHotQuestions() {
        return questionRepository.findHotQuestions()
                .stream()
                .map(questionMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<QuestionDto> findUnansweredQuestions() {
        return questionRepository.findUnansweredQuestions()
                .stream()
                .map(questionMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<QuestionDto> findQuestionsWithMinTwoAnswers() {
        return questionRepository.findQuestionsWithMinTwoAnswers()
                .stream()
                .map(questionMapper::map)
                .collect(Collectors.toList());
    }
}
