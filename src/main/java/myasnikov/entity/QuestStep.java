package myasnikov.entity;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class QuestStep extends Entity {
  private String description;
  private List<QuestChoice> choices;
  private String imagePath;
  private String base64Image;
  private boolean isWin;

  public QuestStep(Long id, String description, List<QuestChoice> choices, String imagePath) {
    super(id);
    this.description = description;
    this.choices = choices;
    this.imagePath = imagePath;
  }

  public QuestStep(Long id, String description, String imagePath, Boolean isWin) {
    super(id);
    this.description = description;
    this.imagePath = imagePath;
    this.isWin = isWin;
  }
}
