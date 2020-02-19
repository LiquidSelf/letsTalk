package dao.users;

import dto.users.UsersDTO;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
public class DB_USER {

    @Id @Column(name = "username", nullable = false)    private String username;
    @Column(nullable = false)                           private String password;
    @Column(nullable = true)                            private Integer age;
    @Column(name = "account_non_expired")               private boolean accountNonExpired;
    @Column(name = "account_non_locked")                private boolean accountNonLocked;
    @Column(name = "credentials_non_expired")           private boolean credentialsNonExpired;
    @Column(name = "enabled")                           private boolean enabled;

    public DB_USER() {
    }

    public DB_USER(UsersDTO dto) {
        if(dto == null) return;
        this.setUsername(dto.getUsername());
        this.setAge(dto.getAge());
        this.setAccountNonExpired(dto.isAccountNonExpired());
        this.setAccountNonLocked(dto.isAccountNonLocked());
        this.setCredentialsNonExpired(dto.isCredentialsNonExpired());
        this.setEnabled(dto.isEnabled());
//        this.setAuthorities(dto.getAuthorities());
    }

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

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}