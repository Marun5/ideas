package pl.Marcin.ideas.category.domain.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    private UUID id;
    private String name;

    public Category() {
        this.id = UUID.randomUUID();
    }
    public Category(String name) {
        this();
        this.name = name;
    }
}
