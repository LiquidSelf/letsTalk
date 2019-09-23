package controllers;

import beans.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import static org.springframework.web.socket.CloseStatus.SESSION_NOT_RELIABLE;

public class WSHandler implements WebSocketHandler {

    WebSocketSession session;
    private static Map<String, WebSocketSession> sessions = new ConcurrentHashMap<String, WebSocketSession>();
    public  static List<Message> messages = new LinkedList<Message>();
    private ObjectMapper JACKSON = new ObjectMapper(); //smooth criminal



    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        if(webSocketSession.getPrincipal() == null ||
           webSocketSession.getPrincipal().getName() == null ||
           webSocketSession.getPrincipal().getName().length() == 0) {
           webSocketSession.close(SESSION_NOT_RELIABLE);
           return;
        }

        session = webSocketSession;
        sessions.put(webSocketSession.getPrincipal().getName(), session);
        System.out.println("afterConnectionEstablished ss: " + sessions.size());
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        if(webSocketSession.getPrincipal() == null){
            System.out.println("principal is null, destroing the session");
            webSocketSession.close(SESSION_NOT_RELIABLE);
            return;
        }

        String payload = webSocketMessage.getPayload().toString();
        if(payload == null || payload.length() == 0) return;
        Message message = JACKSON.readValue(payload, Message.class);
        message.setUsername(webSocketSession.getPrincipal().getName());
        newMsg(message);
    }

    private void newMsg(Message msg) throws JsonProcessingException, IOException {

        if(messages.size() >= 20) messages.remove(0);
        messages.add(msg);

        String message = JACKSON.writeValueAsString(msg);

        sessions.forEach(new BiConsumer<String, WebSocketSession>() {
            @Override
            public void accept(String s, WebSocketSession ses) {
                if(ses.isOpen())

                    try { ses.sendMessage(new TextMessage(message));}
                    catch (IOException e) {e.printStackTrace();}

                else sessions.remove(ses);
            }
        });
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        System.out.println("handleTransportError");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        System.out.println(closeStatus.toString() + "\nbye who? : " + webSocketSession.getPrincipal().getName());
        session.close();
        sessions.remove(webSocketSession.getPrincipal().getName());
    }

    @Override
    public boolean supportsPartialMessages() {
        System.out.println("supportsPartialMessages?");
        return false;
    }
}
