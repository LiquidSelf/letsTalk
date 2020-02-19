package dto.users.registration;

import java.io.Serializable;

public class RegRequest implements Serializable{
    private static final long serialVersionUID = 5926468583005150706L;
    private String username;
    private String password;
    private String passRepeat;

    public RegRequest(){}
    public RegRequest(String username, String password, String passRepeat) {
        this.setUsername(username);
        this.setPassword(password);
        this.setPassRepeat(passRepeat);
    }

    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassRepeat() {
        return passRepeat;
    }

    public void setPassRepeat(String passRepeat) {
        this.passRepeat = passRepeat;
    }
}