package pl.Marcin.ideas.question.domain.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Entity
@Table(name = "answers")
public class Answer {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;
    @NotBlank
    @Size(max = 255)
    private String name;
    @ManyToOne
    private Question question;

    public Answer() {
        this.id = UUID.randomUUID();
    }
    public Answer(String name) {
        this();
        this.name = name;
    }
}
