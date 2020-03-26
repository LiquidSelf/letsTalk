package dto;

import dto.websocket.WSMessage;

public class Message {

    public static final String type = "CHAT_MESSAGE";
    private String text;
    private String username;

    public Message(){
    }

    public Message(String text, String userName) {
        this.text = text;
        this.username = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }
}
