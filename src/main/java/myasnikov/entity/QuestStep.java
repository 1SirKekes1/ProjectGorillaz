package myasnikov.entity;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class QuestStep extends Entity {
    private String description;
    private List<QuestChoice> choices;

    public QuestStep(Long id, String description, List<QuestChoice> choices) {
        super(id);
        this.description = description;
        this.choices = choices;
    }
}