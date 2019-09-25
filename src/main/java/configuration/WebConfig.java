package configuration;

import beans.Greeting;
import controllers.AngularREST;
import controllers.BasicController;
import filters.ExceptionsManager;
import controllers.GreetingController;
import filters.Utf8Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import services.JwtTokenUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@PropertySource("classpath:application.properties")
@EnableWebMvc
@ComponentScan(basePackageClasses = {
        BasicController.class,
        GreetingController.class,
        AngularREST.class,
        Greeting.class,
        HibernateConfiguration.class,
        JwtTokenUtil.class
})
@Configuration
public class WebConfig
implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
        .addResourceHandler("*.js", "*.css", "*.html")
        .addResourceLocations("/ng/");
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        ExceptionsManager manager = new ExceptionsManager();
        resolvers.add(1, manager);
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        return new Utf8Filter();
    }
}
