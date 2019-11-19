package configuration;

import dto.Greeting;
import controllers.AngularREST;
import controllers.BasicController;
import controllers.GreetingController;
import filters.ExceptionsManager;
import filters.JwtRequestFilter;
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

import java.util.List;


@PropertySource("classpath:application.properties")
@EnableWebMvc
@ComponentScan(basePackageClasses = {
        BasicController.class,
        GreetingController.class,
        AngularREST.class,
        Greeting.class,
        DatabaseConfig.class,
        JwtTokenUtil.class,
        JwtRequestFilter.class
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
