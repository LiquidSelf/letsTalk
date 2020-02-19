package dto.users;

import dao.users.DB_USER;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Set;

public class UsersDTO implements UserDetails {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private Date                        exp;
    private Date                        iat;

    private String                      username;
    private String                      password;
    private Integer                     age;
    private Set<GrantedAuthority>       authorities;
    private boolean                     accountNonExpired;
    private boolean                     accountNonLocked;
    private boolean                     credentialsNonExpired;
    private boolean                     enabled;

    public UsersDTO() {}

    public UsersDTO(DB_USER db_user){
        if(db_user == null) return;
        this.setUsername(db_user.getUsername());
        this.setPassword(db_user.getPassword());
        this.setAge(db_user.getAge());
        this.setAuthorities(null);
        this.setAccountNonExpired(db_user.isAccountNonExpired());
        this.setAccountNonLocked(db_user.isAccountNonLocked());
        this.setCredentialsNonExpired(db_user.isCredentialsNonExpired());
        this.setEnabled(db_user.isEnabled());
//        this.setAuthorities(db_user.getAuthorities());
    }

    public Date getExp() {
        return exp;
    }

    public void setExp(Date exp) {
        this.exp = exp;
    }

    public Date getIat() {
        return iat;
    }

    public void setIat(Date iat) {
        this.iat = iat;
    }

    @Override
    public String getPassword() {return password;}

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "UsersDTO{" + "username='" + username + '\'' + '}';
    }
}
