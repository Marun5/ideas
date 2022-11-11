package pl.Marcin.ideas.question.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.Marcin.ideas.question.domain.model.Answer;

import java.util.List;
import java.util.UUID;

public interface AnswerRepository extends JpaRepository<Answer, UUID> {

    List<Answer> findAllByQuestionId(UUID id);
}
