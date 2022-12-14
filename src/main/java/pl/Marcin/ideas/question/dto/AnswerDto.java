package pl.marcin.ideas.question.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class AnswerDto {

    private UUID id;

    private String name;

    private UUID questionId;

    private String questionName;

    private UUID categoryId;

    private String categoryName;
}
