package app.services.users;

import app.database.dao.users.UsersDao;
import app.services.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;

@PropertySource("classpath:application.properties")
@Configuration
public class DB_DetailServiceTest_ContextConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenUtil tokenUtil() {
        return new JwtTokenUtil();
    }

    @Bean
    public HttpServletRequest request() {
        return mock(HttpServletRequest.class);
    }

    @Bean
    public DB_DetailService dbDetailService() {
        return new DB_DetailService();
    }

    @Bean
    public UsersDao usersDao() {
        return new UsersDao();
    }
}
