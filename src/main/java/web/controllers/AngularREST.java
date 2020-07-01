package web.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AngularREST {

    /**Возвращает @Principal если авторизован и @null если нет*/
    @RequestMapping(value = "/mee", method = RequestMethod.GET)
    public Object isAuthenticated(Authentication isAuth) throws Exception {
        return isAuth == null ? null : isAuth.getPrincipal();
    }
}
