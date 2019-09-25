package filters;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*")
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class Utf8Filter extends CharacterEncodingFilter {

    public Utf8Filter() {
        super("UTF-8", true, true);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("privet", "ANDREEEY BLINAAA!! PRIVET:)");
        super.doFilterInternal(request, response, filterChain);
    }
}
