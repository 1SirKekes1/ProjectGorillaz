package myasnikov.entity;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends Entity {
  private String username;
  private String email;
  private String password;
  private Long games;
  private Long wins;
  private Long losses;
  private String base64Image;

  public User(Long id, String username, String email, String password) {
    super(id);
    this.username = username;
    this.email = email;
    this.password = password;
    this.games = 0L;
    this.wins = 0L;
    this.losses = 0L;
  }
}
