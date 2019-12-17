package lionel.meethere.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username",nullable = false,length = 20)
    private String username;

    @Column(name = "telephone",nullable = false,length = 11)
    private String telephone;

    @Column(name = "password",nullable = false,length = 20)
    private String password;

    @Column(name = "admin",nullable = false)
    private Integer admin;

}
