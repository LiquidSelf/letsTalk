package controllers;

import dto.Message;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import services.ws.WSHandler;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersCRUD {

//    @GetMapping(value = "/myself")
//    public Chel getMessages() {
//        return null;
//    }
}
