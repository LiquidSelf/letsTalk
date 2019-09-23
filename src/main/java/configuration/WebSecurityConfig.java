package configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService()  {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder().username("admin").password("111").roles("USER").build());
        manager.createUser(User.withDefaultPasswordEncoder().username("user").password("111").roles("USER").build());

        return manager;
    }

//    @Autowired
//    public void inMemoryUsers(AuthenticationManagerBuilder builder) throws Exception {
//        builder.inMemoryAuthentication().withUser("user").password("user").roles("USER");
//    }
//
//    @Bean
//    public PasswordEncoder passEncoder(){
//        return NoOpPasswordEncoder.getInstance();
//    }


//    @Bean
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return new AuthenticationManager() {
//            @Override
//            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//                System.out.println("getPrincipal: " + authentication.getPrincipal());
//                System.out.println("getCredentials: " + authentication.getCredentials());
//                return null;
//            }
//        };
//    }

    @Bean
    public SimpleUrlAuthenticationSuccessHandler authSuccHandler() {

        return new SimpleUrlAuthenticationSuccessHandler(){
            @Override
            public void onAuthenticationSuccess(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    Authentication authentication
            ) throws IOException, ServletException {
                System.out.println("wellcome " + authentication.getName() + "!");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html; charset=UTF-8");
                response.getWriter().write(new ObjectMapper().writeValueAsString(authentication.getPrincipal()));
                response.setStatus(200);
            }
        };
    }
    @Bean
    public SimpleUrlAuthenticationFailureHandler authFailHandler() {
        return new SimpleUrlAuthenticationFailureHandler(){
            @Override
            public void onAuthenticationFailure(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    AuthenticationException exception
            ) throws IOException, ServletException {
                System.out.println("fail? " + exception.getMessage());
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html; charset=UTF-8");
                response.getWriter().write(new ObjectMapper().writeValueAsString(exception));
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        };
    }

    @Bean
    public SimpleUrlLogoutSuccessHandler logoutSuccHandler() {
        return new SimpleUrlLogoutSuccessHandler(){
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                System.out.println("bye-bye " + authentication.getName() + ".");
                response.setContentType("text/html; charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(new ObjectMapper().writeValueAsString(authentication.getPrincipal()));
                response.setStatus(200);
            }
        };
    }

    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("WebSecurityConfig#configure()");
//        http
//                .formLogin()
//                .loginPage("/")
//                .permitAll()
//                .successForwardUrl("/index.html#wellcome")
//                .failureForwardUrl("/index.html#login?error")
//                .usernameParameter("username")
//                .passwordParameter("password");
//        http
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout")
//                .invalidateHttpSession(true)
//                .clearAuthentication(true);
//        http
//                .authorizeRequests()
//                .antMatchers("/","/*.js", "/*.css")
//                .permitAll()
//                .and()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and().csrf()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessHandler(new LogoutSuccessHandler() {
//                    @Override
//                    public void onLogoutSuccess(
//                            HttpServletRequest request,
//                            HttpServletResponse response,
//                            Authentication authentication
//                    ) throws IOException, ServletException {
//                        System.out.println("onLogoutSuccess");
//                        response.setStatus(200);
//
//                    }
//                })
//                .clearAuthentication(true)
//                .permitAll()
//                .and()
//                .formLogin()
//                .loginPage("/")
//                .loginProcessingUrl("/login")
//                .failureForwardUrl("/")
//                .successForwardUrl("/")
//                .successHandler(authSuccHandler())
//                .failureHandler(new AuthenticationFailureHandler() {
//                    @Override
//                    public void onAuthenticationFailure(
//                            HttpServletRequest request,
//                            HttpServletResponse response,
//                            AuthenticationException exception
//                    ) throws IOException, ServletException {
//                        response.getWriter().write(new ObjectMapper().writeValueAsString(exception));
//                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                    }
//                })
//                .permitAll();


        http

                .authorizeRequests().anyRequest().permitAll()
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and().formLogin().loginPage("/").loginProcessingUrl("/login")
                .successHandler(authSuccHandler()).failureHandler(authFailHandler()).permitAll()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/").logoutSuccessHandler(logoutSuccHandler()).permitAll()
                .and().authorizeRequests().antMatchers("/api/mee").permitAll()
                .and().authorizeRequests().antMatchers("/api/**").hasRole("USER");
    }


}