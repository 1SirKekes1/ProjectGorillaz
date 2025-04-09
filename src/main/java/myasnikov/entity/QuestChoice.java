package myasnikov.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quest_choice")
public class QuestChoice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Lob
  private String text;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "id", nullable = false)
  private QuestStep nextStepId;

}
