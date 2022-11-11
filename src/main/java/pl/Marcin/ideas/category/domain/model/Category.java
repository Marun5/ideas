package pl.Marcin.ideas.category.domain.model;

import lombok.Data;
import pl.Marcin.ideas.question.domain.model.Question;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    private UUID id;
    private String name;
//    @OneToMany(mappedBy = "category")
//    private List<Question> questions;

    public Category() {
        this.id = UUID.randomUUID();
    }
    public Category(String name) {
        this();
        this.name = name;
    }
}
