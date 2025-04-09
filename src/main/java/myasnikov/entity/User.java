package myasnikov.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private Long games;

  @Column(nullable = false)
  private Long wins;

  @Column(nullable = false)
  private Long losses;

  @Column(nullable = false)
  private String base64Image;

}
