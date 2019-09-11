package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
public class AngularController {

    @GetMapping("/")
    public String index() {
        return "/index.html";
    }

//    @GetMapping
//    public String all(){
//        return "redirect:/index.html";
//    }

}
