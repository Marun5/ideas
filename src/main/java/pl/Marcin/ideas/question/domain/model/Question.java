package pl.marcin.ideas.question.domain.model;

import lombok.Data;
import pl.marcin.ideas.category.domain.model.Category;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
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
    private LocalDateTime created;
    private LocalDateTime modified;

    public Question() {
        this.id = UUID.randomUUID();
    }
    public Question(String name) {
        this();
        this.name = name;
    }

    @PrePersist
    void prePersist() {
        created = LocalDateTime.now();
        modified = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        modified = LocalDateTime.now();
    }
}
