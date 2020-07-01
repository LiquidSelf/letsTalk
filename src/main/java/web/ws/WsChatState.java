package web.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import app.services.JwtTokenUtil;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class WsChatState {
    private ConcurrentHashMap<String, String> tikets = new ConcurrentHashMap<String, String>();

    @Autowired
    private JwtTokenUtil tokenUtil;

    public String generateTiket(String token){
        String tiket = tokenUtil.getUsernameFromToken(token) + "322SWAG322";
        tikets.put(tiket, token);
        return tiket;
    }
    public boolean validateTiket(String tiket){
        if(tikets.containsKey(tiket)){
            tikets.remove(tiket);
            return true;
        }
        return false;
    }
}
