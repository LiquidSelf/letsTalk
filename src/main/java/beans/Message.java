package beans;

public class Message {
    private String message;
    private String username;

    public Message(){
    }

    public Message(String message, String userName) {
        this.message = message;
        this.username = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }
}
