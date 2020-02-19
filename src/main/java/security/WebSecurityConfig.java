package security;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.Message;
import filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import security.auth.DB_DetailService;
import security.auth.JwtAuthProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ApplicationContext context;

    @Bean
    public UserDetailsManager userDetailsService()  {
        return new DB_DetailService();
    }

    @Bean
    ObjectMapper jackson(){
        ObjectMapper mapper = new ObjectMapper();
        return mapper;
    }

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
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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


    @Bean
    public AuthenticationProvider jwtProvider(){
        return new JwtAuthProvider();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
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


        //ok
        http

                .authorizeRequests().anyRequest().permitAll()
                .and().authorizeRequests().antMatchers("/authenticate").permitAll()
                .and().authorizeRequests().antMatchers("/api/mee").permitAll()
                .and().authorizeRequests().antMatchers("/api/**").hasRole("USER")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();

        http.addFilterBefore(context.getBean(JwtRequestFilter.class), UsernamePasswordAuthenticationFilter.class);
    }


}