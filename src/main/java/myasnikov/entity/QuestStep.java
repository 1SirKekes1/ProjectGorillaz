package myasnikov.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static myasnikov.entity.EndType.NONE;

@Entity
@Table(name = "quest_step")
public class QuestStep {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Lob
  private String description;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "id", nullable = false)
  private List<QuestChoice> choices;

  @Column(name = "image_path")
  private String imagePath;

  @Lob
  private String base64Image;

  @Column(columnDefinition = "enum('NONE', 'WIN', 'LOSE'")
  private EndType endType = NONE;

}
