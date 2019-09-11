package controllers;

import beans.Greeting;
import beans.Message;
import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private WebApplicationContext context;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping("/hello")
    public Greeting hello(@RequestParam(value="name", defaultValue="World") String name) {
        Greeting greeting = new Greeting(1l, "приветинг 1");
        return greeting;
    }

    @RequestMapping(value = "/getName")
    public String g(@RequestParam(value="id", required = true) Long id) {
        String description = "empty";
        Session s = sessionFactory.openSession();
        List result = s.createNativeQuery(
                "select current_description_ru from inv_book_museum where id = " + id).getResultList();
        if(result != null && result.size() > 0) description = String.valueOf(result.get(0));
        System.out.println(description);
        s.close();
        return "Description is " + description;
    }

    @RequestMapping(value = "/getMessages", method = RequestMethod.GET)
    public List<Message> getMessages() {
        return WSHandler.messages;
    }


    @RequestMapping(value = "/whoAmI", method = RequestMethod.GET)
    public Principal whoAmI(Principal principal) {
        return principal;
    }
}