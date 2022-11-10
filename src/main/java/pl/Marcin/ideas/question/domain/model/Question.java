package pl.Marcin.ideas.question.domain.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "questions")
public class Question {

    @Id
    private UUID id;

    private String name;

    public Question() {
        this.id = UUID.randomUUID();
    }
    public Question(String name) {
        this();
        this.name = name;
    }
}
