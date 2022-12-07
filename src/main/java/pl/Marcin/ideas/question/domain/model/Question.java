package pl.Marcin.ideas.question.domain.model;

import lombok.Data;
import pl.Marcin.ideas.category.domain.model.Category;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;
    @NotBlank
    @Size(min = 3, max = 255)
    private String name;
    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    public Question() {
        this.id = UUID.randomUUID();
    }
    public Question(String name) {
        this();
        this.name = name;
    }
}
