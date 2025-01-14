package myasnikov.entity;

import lombok.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends Entity {
    private String username;
    private String email;
    private String password;
    @Setter
    private String base64Image;

    public User(Long id, String username, String email, String password) {
        super(id);
        this.username = username;
        this.email = email;
        this.password = password;
    }
}