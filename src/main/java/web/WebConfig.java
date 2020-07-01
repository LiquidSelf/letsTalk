package web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.WebSocketHandler;
import web.ws.WSHandler;

import java.io.File;
import java.util.concurrent.TimeUnit;


@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = {WebConfig.class})
public class WebConfig
implements WebMvcConfigurer {

    @Value("${app.upload.dir.name}")
    public String uploadDirName;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
        .addResourceHandler("*.js", "*.css", "*.html")
        .addResourceLocations("/");

        String uploadPATH =  System.getProperty("catalina.base" ) +File.separator+ uploadDirName +File.separator;

        registry
        .addResourceHandler("*.mp4")
        .addResourceLocations("file:"+uploadPATH);

        registry
        .addResourceHandler("*.png","*.jpg","*.gif")
        .setCacheControl(CacheControl.maxAge(5, TimeUnit.MINUTES))
        .addResourceLocations("file:"+uploadPATH, "classpath:/img/");
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(-1);
        return multipartResolver;
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new WSHandler();
    }
}
