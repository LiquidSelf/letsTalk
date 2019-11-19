package controllers;

import dto.Message;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import services.JwtTokenUtil;
import services.ws.WSHandler;
import services.ws.WsChatState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatREST {

    @Autowired
    private WsChatState chatState;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @RequestMapping(value = "/getMessages", method = RequestMethod.GET)
    public List<Message> getMessages() {
        return WSHandler.messages;
    }

    @RequestMapping(value = "/ws_tiket", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String wsTiket(HttpServletRequest request, HttpServletResponse response) {
        String token = tokenUtil.getTokenFromRequest(request);

        try{
            tokenUtil.validateToken(token);
        }catch (JWTVerificationException ex){
            System.out.println(ex);
            return null;
        }
        return chatState.generateTiket(token);
    }

}