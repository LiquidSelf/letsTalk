package app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.jboss.logging.annotations.Message;

import java.io.File;

@PropertySource("classpath:application.properties")
@ComponentScan(basePackageClasses = {AppConfig.class})
@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper jackson(){
        return new ObjectMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public File uplDir(@Value("${app.upload.dir.name}") String uploadDirName){
        File uploadDir = new File(System.getProperty( "catalina.base" ) +File.separator+ uploadDirName +File.separator);
        if(!uploadDir.exists()) uploadDir.mkdir();
        return uploadDir;
    }

}
