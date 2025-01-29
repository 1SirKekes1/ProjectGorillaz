package myasnikov.entity;

import lombok.*;

import java.util.List;

import static myasnikov.entity.EndType.NONE;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class QuestStep extends Entity {
  private String description;
  private List<QuestChoice> choices;
  private String imagePath;
  private String base64Image;
  private EndType endType = NONE;

  public QuestStep(Long id, String description, List<QuestChoice> choices, String imagePath) {
    super(id);
    this.description = description;
    this.choices = choices;
    this.imagePath = imagePath;
  }

  public QuestStep(Long id, String description, String imagePath, EndType endType) {
    super(id);
    this.description = description;
    this.imagePath = imagePath;
    this.endType = endType;
  }
}
