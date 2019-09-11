package controllers;

import beans.Message;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WSHandler implements WebSocketHandler {

    WebSocketSession session;
    private static Queue<WebSocketSession> sessions = new ConcurrentLinkedQueue<WebSocketSession>();
    public  static List<Message> messages = new LinkedList<Message>();
    private ObjectMapper JACKSON = new ObjectMapper(); //smooth criminal

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        session = webSocketSession;
        sessions.add(session);
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

        Iterator<WebSocketSession> it = sessions.iterator();
        while(it.hasNext()){
            WebSocketSession ses = it.next();
            if(ses.isOpen()) ses.sendMessage(new TextMessage(JACKSON.writeValueAsString(msg)));
            else             sessions.remove(ses);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        System.out.println("handleTransportError");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        System.out.println(closeStatus.toString() + " sess closed: " + webSocketSession.getId());
        session.close();
        sessions.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        System.out.println("supportsPartialMessages?");
        return false;
    }
}
