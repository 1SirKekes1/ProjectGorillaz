package myasnikov.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(schema = "game", name = "quest")
@Getter
@Setter
public class Quest{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(unique = true, nullable = false)
  private String title;

  @Column(unique = true, nullable = false)
  private String description;

  @OneToMany
  @JoinColumn(name = "id")
  private List<QuestStep> steps;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "image_data", columnDefinition = "LONGBLOB")
  private byte[] imageData;

}
