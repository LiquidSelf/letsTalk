package configuration;

import controllers.AngularREST;
import controllers.FileUploadREST;
import dao.Dao;
import dto.Greeting;
import filters.ExceptionsManager;
import filters.JwtRequestFilter;
import filters.Utf8Filter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import services.JwtTokenUtil;

import java.io.File;
import java.util.List;


@PropertySource("classpath:application.properties")
@EnableWebMvc
@ComponentScan(basePackageClasses = {
        AngularREST.class,
        FileUploadREST.class,
        Greeting.class,
        DatabaseConfig.class,
        JwtTokenUtil.class,
        JwtRequestFilter.class,
        Dao.class
})
@Configuration
public class WebConfig
implements WebMvcConfigurer {

    @Value("${app.upload.dir.name:${user.home}}")
    public String uploadDirName;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
        .addResourceHandler("*.js", "*.css", "*.html")
        .addResourceLocations("/");

        String path =  System.getProperty( "catalina.base" ) +File.separator+ uploadDirName +File.separator;

        registry
        .addResourceHandler("*.mp4")
        .addResourceLocations(
        "file://"+System.getProperty( "catalina.base" ) +File.separator+ uploadDirName +File.separator);

        registry
        .addResourceHandler("*.png","*.jpg","*.gif")
        .addResourceLocations(
        "file://"+System.getProperty( "catalina.base" ) +File.separator+ uploadDirName +File.separator);

        registry
        .addResourceHandler("*.png","*.jpg","*.gif")
        .addResourceLocations("classpath:/img/");
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        ExceptionsManager manager = new ExceptionsManager();
        resolvers.add(1, manager);
    }

    @Bean
    public OncePerRequestFilter characterEncodingFilter() {
        return new Utf8Filter();
    }
}
