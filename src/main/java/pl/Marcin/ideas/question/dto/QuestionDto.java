package pl.marcin.ideas.question.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class QuestionDto {

    private UUID id;

    private String name;

    private UUID categoryId;

    private String categoryName;

    private long answers;
}
