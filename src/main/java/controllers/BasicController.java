package controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class BasicController {

    @GetMapping("/")
    public String ddd(Authentication authentication) {
        return "index.html";
    }
}