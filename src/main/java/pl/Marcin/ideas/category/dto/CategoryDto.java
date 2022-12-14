package pl.marcin.ideas.category.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryDto {

    private UUID id;

    private String name;

    private long questions;
}
