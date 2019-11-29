package dao.users;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class Users {

    @Id @Column(name = "username", nullable = false)
    private String username;
    @Column(nullable = false) private String password;
    @Column(nullable = true) private Integer age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}