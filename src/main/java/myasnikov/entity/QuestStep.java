package myasnikov.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static myasnikov.entity.EndType.NONE;

@Entity
@Table(schema = "game",name = "quest_step")
public class QuestStep {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column
  private String description;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "id", nullable = false)
  private List<QuestChoice> choices;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "image_data", columnDefinition = "LONGBLOB")
  private byte[] imageData;

  @Column(columnDefinition = "enum('NONE', 'WIN', 'LOSE'")
  private EndType endType = NONE;

}
