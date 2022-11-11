package pl.Marcin.ideas.question.domain.model;

import lombok.Data;
import pl.Marcin.ideas.category.domain.model.Category;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "questions")
public class Question {

    @Id
    private UUID id;
    private String name;
    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "question")
    private Set<Answer> answers;

    public Question() {
        this.id = UUID.randomUUID();
    }
    public Question(String name) {
        this();
        this.name = name;
    }

    public Question addAnswer(Answer answer) {
        if(answers==null){
            answers = new LinkedHashSet<>();
        }
        answer.setQuestion(this);
        answers.add(answer);
        return this;
    }
}
