package services.ws;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.Message;
import dto.websocket.WSMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.*;
import services.JwtTokenUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.web.socket.CloseStatus.POLICY_VIOLATION;
import static org.springframework.web.socket.CloseStatus.SESSION_NOT_RELIABLE;
import static org.springframework.web.socket.CloseStatus.TLS_HANDSHAKE_FAILURE;

public class WSHandler implements WebSocketHandler {

    private static volatile List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
    public static List<Message> messages = new LinkedList<Message>();

    @Autowired private ObjectMapper jackson;

    @Autowired private JwtTokenUtil tokenUtil;

    @Autowired private WsChatState chatState;

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        sessions.add(webSocketSession);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        String payload = webSocketMessage.getPayload().toString();
        if(payload == null || payload.length() == 0) throw new NullPointerException("you cannot send empty message");

        JavaType typeMessage = jackson.getTypeFactory().constructParametricType(WSMessage.class, Message.class);

        WSMessage<Message> wsMessage = jackson.readValue(payload, typeMessage);

        validate(wsMessage, webSocketSession);
        if(wsMessage.get_isPing()) return;


        String username = tokenUtil.getUsernameFromToken(wsMessage.get_token());

        Message userMessage = wsMessage.get_data();
        userMessage.setUsername(username);

        handleUserMessage(userMessage);
    }

    private void handleUserMessage(Message userMessage) throws JsonProcessingException, IOException {

        if(userMessage == null || StringUtils.isEmpty(userMessage.getText())) throw new RuntimeException("empty message");
        if(StringUtils.isEmpty(userMessage.getUsername())) throw new RuntimeException("empty username");


        if(messages.size() >= 20) messages.remove(0);
        messages.add(userMessage);

        String message = jackson.writeValueAsString(userMessage);

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
        if(webSocketSession.isOpen()) webSocketSession.close();
        sessions.remove(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void validate(WSMessage message, WebSocketSession session) throws Exception {
        if(message == null) throw new RuntimeException("wsmsg is null");
        final String token = message.get_token();

        try{
            tokenUtil.validateToken(token);
        }catch (JWTVerificationException ex){
            session.close(SESSION_NOT_RELIABLE);
        }

    }
}
