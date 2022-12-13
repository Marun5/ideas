package pl.Marcin.ideas.question.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.Marcin.ideas.question.domain.model.Question;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

    List<Question> findAllByNameContainingIgnoreCase(String name);

    List<Question> findAllByCategoryId(UUID id);

    Page<Question> findAllByCategoryId(UUID id, Pageable pageable);

    Page<Question> findAllByCategoryIdAndNameContainingIgnoreCase(UUID id, String name, Pageable pageable);

    @Query(value = "select * from questions q order by random() limit :limit", nativeQuery = true)
    List<Question> findRandomQuestions(int limit);

    @Query("from Question q where size(q.answers) > 0 order by size(q.answers) desc")
    List<Question> findHotQuestions();

    @Query("from Question q where size(q.answers) = 0")
    List<Question> findUnansweredQuestions();

    @Query("from Question q where size(q.answers) > 1")
    List<Question> findQuestionsWithMinTwoAnswers();

    Page<Question> findAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
