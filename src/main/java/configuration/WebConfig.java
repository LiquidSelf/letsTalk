package configuration;

import beans.Greeting;
import controllers.GreetingController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@EnableWebMvc
@ComponentScan(basePackageClasses = {
        GreetingController.class,
        Greeting.class,
        HibernateConfiguration.class
})
public class WebConfig
        implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
        .addResourceHandler("/**")
        .addResourceLocations("/chat/");
    }

}
