package web.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class RedirectToIndex {

    @ExceptionHandler(value = NoHandlerFoundException.class)
    protected ModelAndView doResolveException(NoHandlerFoundException ex) {
        return new ModelAndView("index.html");
    }
}
