package pl.Marcin.ideas.question.domain.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "answers")
public class Answer {

    @Id
    private UUID id;

    private String name;

    public Answer() {
        this.id = UUID.randomUUID();
    }
    public Answer(String name) {
        this();
        this.name = name;
    }
}
