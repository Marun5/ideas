package pl.Marcin.ideas.question.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.Marcin.ideas.question.domain.model.Answer;
import pl.Marcin.ideas.question.domain.model.Question;
import pl.Marcin.ideas.question.domain.repository.AnswerRepository;
import pl.Marcin.ideas.question.domain.repository.QuestionRepository;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AnswerService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Transactional(readOnly = true)
    public List<Answer> getAnswers() {
        return answerRepository.findAll();
    }
    @Transactional(readOnly = true)
    public List<Answer> getAnswers(String search) {
        return answerRepository.findAllByNameContainingIgnoreCase(search);
    }
    @Transactional(readOnly = true)
    public List<Answer> findAllByQuestionId(UUID id) {
        return answerRepository.findAllByQuestionId(id);
    }
    @Transactional(readOnly = true)
    public Page<Answer> findAllByQuestionId(UUID id, Pageable pageable) {
        return findAllByQuestionId(id, null, pageable);
    }
    @Transactional(readOnly = true)
    public Page<Answer> findAllByQuestionId(UUID id, String search, Pageable pageable) {
        if(search==null){
            return answerRepository.findAllByQuestionId(id, pageable);
        }
        return answerRepository.findAllByQuestionIdAndNameContainingIgnoreCase(id, search, pageable);
    }

    @Transactional(readOnly = true)
    public Answer getAnswer(UUID id) {
        return answerRepository.getReferenceById(id);
    }

    @Transactional
    public Answer createAnswer(UUID questionId, Answer answerRequest) {
        Answer answer = new Answer();
        answer.setName(answerRequest.getName());

        Question question = questionRepository.getReferenceById(questionId);
        answer.setQuestion(question);
        questionRepository.save(question);
        answerRepository.save(answer);

        return answer;
    }

    @Transactional
    public Answer updateAnswer(UUID answerId, Answer answerRequest) {
        Answer answer = answerRepository.getReferenceById(answerId);
        answer.setName(answerRequest.getName());

        return answerRepository.save(answer);
    }

    @Transactional
    public void deleteAnswer(UUID answerId) {
        answerRepository.deleteById(answerId);
    }

    @Transactional
    public Integer countAnswers() {
        return answerRepository.findAll().size();
    }
}
