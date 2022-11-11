package pl.Marcin.ideas.question.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.Marcin.ideas.question.domain.model.Question;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

//    @Query(value = "from Question where category.id = :id")   ------> is it necessary?????
    List<Question> findAllByCategoryId(UUID id);
}
