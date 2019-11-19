package services.ws;

import dto.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;
import services.JwtTokenUtil;

import java.io.IOException;
import java.util.*;

import static org.springframework.web.socket.CloseStatus.SESSION_NOT_RELIABLE;

public class WSHandler implements WebSocketHandler {

    private static volatile List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
    public static List<Message> messages = new LinkedList<Message>();
    private ObjectMapper JACKSON = new ObjectMapper(); //smooth criminal

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Autowired
    private WsChatState chatState;

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        String tiket = parseToken(webSocketSession);

        if(!chatState.validateTiket(tiket)){
            webSocketSession.close(SESSION_NOT_RELIABLE);
            return;
        }

        try {
            sessions.add(webSocketSession);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        System.out.println("afterConnectionEstablished ss: " + sessions.size());
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        String payload = webSocketMessage.getPayload().toString();
        if(payload == null || payload.length() == 0) return;
        Message message = JACKSON.readValue(payload, Message.class);
        newMsg(message);
    }

    private void newMsg(Message msg) throws JsonProcessingException, IOException {

        if(messages.size() >= 20) messages.remove(0);
        messages.add(msg);

        String message = JACKSON.writeValueAsString(msg);

        for(WebSocketSession ses : sessions){
            if(ses.isOpen())
                try { ses.sendMessage(new TextMessage(message));}
                catch (IOException e) {
                    System.out.println(e);
                }

            else sessions.remove(ses);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        System.out.println("handleTransportError : " + throwable.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        System.out.println(closeStatus.toString());
        webSocketSession.close();
        sessions.remove(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private String parseToken(WebSocketSession webSocketSession){
        try{
            return webSocketSession.getUri().getQuery().replace("tiket=", "");
        }catch (Exception ex){
            System.out.println(ex);
            return null;
        }
    }
}
