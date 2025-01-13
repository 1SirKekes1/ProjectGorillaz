package myasnikov.entity;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Quest extends Entity {
    private String title;
    private String description;
    private List<QuestStep> steps;

    public Quest(Long id, String title, String description, List<QuestStep> steps) {
        super(id);
        this.title = title;
        this.description = description;
        this.steps = steps;
    }
}