package beans;

public class Message {
    private String message;
    private String login;

    public Message(){
    }

    public Message(String message, String login) {
        this.message = message;
        this.login = login;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
