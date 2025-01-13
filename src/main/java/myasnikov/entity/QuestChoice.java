package myasnikov.entity;

import lombok.*;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class QuestChoice extends Entity {
    private String text;
    private Long nextStepId;

    public QuestChoice(Long id, String text, Long nextStepId) {
        super(id);
        this.text = text;
        this.nextStepId = nextStepId;
    }
}