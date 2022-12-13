package pl.marcin.ideas.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticsDto {
    private long categories;
    private long questions;
    private long answers;
}
