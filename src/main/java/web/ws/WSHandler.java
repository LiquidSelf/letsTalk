package web.ws;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.database.dto.Message;
import app.database.dto.websocket.WSMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.*;
import app.services.JwtTokenUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.web.socket.CloseStatus.SESSION_NOT_RELIABLE;

@Component
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
        if(StringUtils.isEmpty(payload)) throw new NullPointerException("you cannot send empty message");

        WSMessage wsMessage = jackson.readValue(payload, WSMessage.class);

        validate(wsMessage, webSocketSession);

        switch (wsMessage.get_messageType()){
            case PING: break;
            case CHAT_MESSAGE:{
                handelChatMessage(wsMessage);
                break;
            }
            case FEED_MESSAGE:{

                break;
            }
            default: throw new RuntimeException("unknown message type");
        }
    }

    private void handelChatMessage(WSMessage<Message> wsMessage) throws JsonProcessingException, IOException {

        if(wsMessage == null) throw new RuntimeException("empty message");
        String token = wsMessage.get_token();

        wsMessage.set_token(null);

        String username = tokenUtil.getUsernameFromToken(token);

        Message message = wsMessage.get_data();
        message.setUsername(username);

        if(messages.size() >= 20) messages.remove(0);
        messages.add(message);

        String json = jackson.writeValueAsString(wsMessage);

        goSEND(json);
    }

    public void onNewFeedItem() throws JsonProcessingException {

        WSMessage<Message> ntf = new WSMessage<Message>();
        ntf.set_messageType(WSMessage.MessageType.FEED_MESSAGE);

        String  json = jackson.writeValueAsString(ntf);

        goSEND(json);
    }

    private void goSEND(String json){
        for(WebSocketSession ses : sessions){
            if(ses.isOpen())
                try { ses.sendMessage(new TextMessage(json));}
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
