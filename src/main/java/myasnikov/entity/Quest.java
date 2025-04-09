package myasnikov.entity;


import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "quest")
public class Quest{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(unique = true, nullable = false)
  private String title;

  @Column(unique = true, nullable = false)
  private String description;

  @OneToMany
  private List<QuestStep> steps;

  @Column
  private String imagePath;

  @Lob
  private String base64Image;

}
