package configuration;

import beans.Greeting;
import controllers.AngularREST;
import controllers.BasicController;
import controllers.ExceptionsManager;
import controllers.GreetingController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@EnableWebMvc
@ComponentScan(basePackageClasses = {
        BasicController.class,
        GreetingController.class,
        AngularREST.class,
        Greeting.class,
        HibernateConfiguration.class
})
public class WebConfig
implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
        .addResourceHandler("*.js", "*.css", "*.html")
        .addResourceLocations("/chat/");
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        ExceptionsManager manager = new ExceptionsManager();
        resolvers.add(1, manager);
    }


    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter(){
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                super.doFilterInternal(request, response, filterChain);
                System.out.println("PAPSDPASPDPASDP ");
            }
        };
        System.out.println("CharacterEncodingFilter eeye");
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setForceRequestEncoding(true);
        characterEncodingFilter.setForceResponseEncoding(true);
        return characterEncodingFilter;
    }
}
