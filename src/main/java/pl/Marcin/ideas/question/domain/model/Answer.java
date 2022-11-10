package pl.Marcin.ideas.question.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Answer {

    private UUID id;

    private String name;
}
